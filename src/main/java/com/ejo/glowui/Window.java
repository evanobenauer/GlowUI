package com.ejo.glowui;

import com.ejo.glowlib.misc.DoOnce;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.event.EventRegistry;
import com.ejo.glowui.util.render.GLManager;
import com.ejo.glowui.util.input.Key;
import com.ejo.glowui.util.input.Mouse;
import com.ejo.glowui.util.render.TextureUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.time.StopWatch;

import java.net.URL;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;

//https://www.glfw.org/docs/latest/window.html#window_refresh
/**
 * The Window class is a container class for the LWJGL3 WindowID. It incorporates GLFW methods in an easy-to-use object-oriented
 * fashion for the window.
 */
public class Window {

    private long windowId;

    private String title;
    private Vector pos;
    private Vector size;

    private int antiAliasing;

    private boolean vSync;

    private int maxTPS;
    private int maxFPS;

    private int ticks;
    private int frames;

    private int tps;
    private int fps;

    private Vector mousePos;

    private double uiScale;

    private boolean open;

    private boolean drawn;
    private boolean ticking;

    private boolean economic;

    private boolean debug;

    public DoOnce doOnResize;

    private Scene scene;


    public Window(String title, Vector pos, Vector size, Scene startingScene, boolean vSync, int antiAliasing, int maxTPS, int maxFPS) {
        this.title = title;
        this.pos = pos;
        this.size = size;
        this.vSync = vSync;
        this.antiAliasing = antiAliasing;
        this.maxTPS = maxTPS;
        this.maxFPS = maxFPS;
        this.uiScale = 1;

        this.open = true;
        this.drawn = true;
        this.ticking = true;
        this.economic = false;
        this.debug = false;

        this.doOnResize = new DoOnce();

        this.scene = startingScene;
    }

    /**
     * This method initiates the window using LWJGL3. All functions are called to set up the position, id, V-Sync, and more.
     * The final action of this method is to set the default screen of the window, which is defined in the constructor
     */
    public void init() {
        final long NULL = 0L;
        if (!glfwInit()) throw new IllegalStateException("Failed to init GLFW");

        setAntiAliasing(getAntiAliasing());

        //Creating the window
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        windowId = glfwCreateWindow((int) getSize().getX(), (int) getSize().getY(), getTitle(), NULL, NULL);
        if (getWindowId() == NULL) throw new IllegalStateException("Window could not be created");

        //Creating the monitor
        glfwGetVideoMode(glfwGetPrimaryMonitor());

        //Set up keys
        Key.onKey.subscribe();
        Mouse.onMouse.subscribe();

        // Load the window icon
        URL imageURL = getClass().getResource("/icon.png");
        if (imageURL != null) {
            int width = 512;
            int height = 512;
            GLFWImage glfwImage = GLFWImage.malloc();
            GLFWImage.Buffer glfwImageBuffer = GLFWImage.malloc(1);
            glfwImage.set(width, height, TextureUtil.getImageBuffer(imageURL, width, height));
            glfwImageBuffer.close();
            glfwSetWindowIcon(getWindowId(), glfwImageBuffer.put(0, glfwImage));
        }

        //Show the window
        setPos(getPos());
        glfwShowWindow(getWindowId());

        //Sets the window context to display graphics
        glfwMakeContextCurrent(getWindowId());
        GL.createCapabilities();
        GL11.glClearColor(0f, 0f, 0f, 0f);

        setVSync(getVSync());

        setScene(getScene());
    }

    /**
     * The MaintenanceLoop is a loop that runs side by side with the Animation Loop, TickLoop and RenderLoop.
     * Its intended job is to have features that don't fit into tick and render to be called in order to modify
     * or "Do Maintenance" on the application
     */
    public void startMaintenanceLoop() {
        Thread thread = new Thread(() -> {
            StopWatch fpsWatch = new StopWatch();
            while (true) {
                sleepThread(1); //This is a limitation that slows down the maintenance loop. I may plan to change this in the future
                calculateFPSTPS(fpsWatch);
                //TODO: This may cause problems when changing scenes. Events don't unsubscribe. Also, Ive tried a similar method to Ticking/Rendering as before, but CPU usage skyrockets
                EventRegistry.EVENT_RUN_MAINTENANCE.post();
            }
        });
        thread.setName("Maintenance Thread");
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * The TickLoop is a loop that runs side by side with the RenderLoop in its own thread.
     * This loop will update as per the defined max TPS of the UI, as defined in the constructor.
     * Its purpose is to have all calculations and functions carried out in it, separate from rendering, which is variable.
     * Current actions include inputs and screen ticks
     */
    public void startTickLoop() {
        Thread thread = new Thread(() -> {
            while (isOpen()) {
                long startTimeNS = System.nanoTime();
                updateMousePos();
                tick();
                ticks++;
                long endTimeNS = System.nanoTime();

                long tickTimeNS = endTimeNS - startTimeNS;
                long sleepTimeNS = 1000000000 / getMaxTPS() - tickTimeNS;
                if (sleepTimeNS > 0) sleepThread(sleepTimeNS / 1000000);
            }
        });
        thread.setName("Tick Thread");
        thread.start();
    }

    /**
     * The RenderLoop is the final instantiated loop. It is a part of the main thread alongside the window instantiation.
     * Its goal is to strictly have render items placed in it. The loop will update at the refresh rate of the monitor.
     * In order to have consistently paced actions, use the tick loop. The render loop must be the final loop as it is
     * a part of the main thread
     */
    public void runRenderLoop() {
        while (isOpen()) {
            setOpen(!glfwWindowShouldClose(getWindowId()));
            long startTimeNS = System.nanoTime();
            updateWindow();
            draw();
            frames++;
            long endTimeNS = System.nanoTime();

            long tickTimeNS = endTimeNS - startTimeNS;
            long sleepTimeNS = (1000000000 / getMaxFPS() - tickTimeNS);
            if (!getVSync()) if (sleepTimeNS > 0) sleepThread(sleepTimeNS / 1000000);
        }
        setOpen(false);
    }

    private void sleepThread(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void draw() {
        if (!shouldDraw()) {
            GLFW.glfwWaitEvents();
            return;
        }
        GL.createCapabilities();
        GL11.glViewport(0, 0, (int) getSize().getX(), (int) getSize().getY());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, getSize().getX(), getSize().getY(), 0, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GLManager.scale(getUIScale()); //Shape Scale
        GLManager.textureScale(getUIScale()); //Texture Scale

        getScene().draw(); //Draw the screen
        EventRegistry.EVENT_RENDER.post(this); //Render event after drawing the screen

        glfwSwapBuffers(getWindowId()); //Finish Drawing here

        if (isEconomic()) {
            try {
                GLFW.glfwWaitEvents();
            } catch (NullPointerException e) {
                GLFW.glfwPollEvents();
            }
        } else {
            try {
                GLFW.glfwPollEvents();
            } catch (NullPointerException e) {
                GLFW.glfwWaitEvents();
            }
        }
    }

    private void tick() {
        if (!shouldTick()) return;
        onKeyPress();
        onMouseClick();
        onMouseScroll();
        getScene().tick();
        EventRegistry.EVENT_TICK.post(this);
    }

    private void onKeyPress() {
        GLFWKeyCallback callback = glfwSetKeyCallback(getWindowId(), (window, key, scancode, action, mods) -> {
            EventRegistry.EVENT_KEY_PRESS.post(window, key, scancode, action, mods); //Key Event to be used outside of class
            if (action == Key.ACTION_PRESS || action == Key.ACTION_HOLD) {
                toggleDebugMode();
                runDebugKeybindings();
            }
            getScene().onKeyPress(key, scancode, action, mods);
        });
        closeAutoClosable(callback);
    }

    private void onMouseClick() {
        GLFWMouseButtonCallback callback = glfwSetMouseButtonCallback(getWindowId(), (window, button, action, mods) -> {
            EventRegistry.EVENT_MOUSE_CLICK.post(window, button, action, mods, getScaledMousePos()); //Mouse click event to be used outside of class
            getScene().onMouseClick(button, action, mods, getScaledMousePos());
        });
        closeAutoClosable(callback);
    }

    private void onMouseScroll() {
        GLFWScrollCallback callback = glfwSetScrollCallback(getWindowId(), (window, scrollX, scrollY) -> {
            EventRegistry.EVENT_MOUSE_SCROLL.post(window,(int)scrollY,getScaledMousePos());
            getScene().onMouseScroll((int)scrollY, getScaledMousePos());
        });
        closeAutoClosable(callback);
    }

    private void closeAutoClosable(AutoCloseable closable) {
        try {
            closable.close();
        } catch (Exception ignored) {
        }
    }


    private void updateWindow() {
        IntBuffer buffer = BufferUtils.createIntBuffer(1);
        glfwGetWindowPos(getWindowId(), buffer, null);
        double x = buffer.get(0);
        glfwGetWindowPos(getWindowId(), null, buffer);
        double y = buffer.get(0);
        Vector pos = new Vector(x, y);
        setPos(pos);

        glfwGetWindowSize(getWindowId(), buffer, null);
        double w = buffer.get(0);
        glfwGetWindowSize(getWindowId(), null, buffer);
        double h = buffer.get(0);
        Vector size = new Vector(w, h);
        if (size.getMagnitude() != 0) setSize(size);
    }

    private Vector updateMousePos() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(getWindowId(), buffer, null);
        double mouseX = buffer.get(0);
        glfwGetCursorPos(getWindowId(), null, buffer);
        double mouseY = buffer.get(0);
        return mousePos = new Vector(mouseX, mouseY);
    }

    private void calculateFPSTPS(StopWatch stopWatch) {
        stopWatch.start();
        if (stopWatch.hasTimePassedS(1)) { //TPS-FPS Updater
            fps = frames;
            frames = 0;
            tps = ticks;
            ticks = 0;
            stopWatch.restart();
        }
    }

    private void toggleDebugMode() {
        if (Key.isControlDown() && Key.isShiftDown() && Key.KEY_G.isKeyDown()) setDebug(!isDebug());
    }

    private void runDebugKeybindings() {
        if (!isDebug()) return;
        if (Key.isShiftDown()) {
            if (Key.KEY_EQUALS.isKeyDown()) setMaxTPS(getMaxTPS() + 1);
            if (Key.KEY_MINUS.isKeyDown()) setMaxTPS(Math.max(getMaxTPS() - 1,0));
        } else if (Key.isControlDown()) {
            if (Key.KEY_EQUALS.isKeyDown()) setMaxFPS(getMaxFPS() + 1);
            if (Key.KEY_MINUS.isKeyDown()) setMaxFPS(Math.max(getMaxFPS() - 1,0));
        } else if (Key.isAltDown()) {
            if (Key.KEY_E.isKeyDown()) setEconomic(!isEconomic());
            if (Key.KEY_V.isKeyDown()) setVSync(!getVSync());
        } else {
            if (Key.KEY_EQUALS.isKeyDown()) setUIScale(getUIScale() + .1);
            if (Key.KEY_MINUS.isKeyDown()) setUIScale(Math.max(getUIScale() - .1,.1));
        }
    }


    public Window run() {
        init();
        startMaintenanceLoop();
        startTickLoop();
        runRenderLoop();
        return this;
    }

    public Window close() {
        GLFW.glfwDestroyWindow(getWindowId());
        GLFW.glfwTerminate();
        return this;
    }


    public Window setScene(Scene scene) {
        scene.setWindow(this);
        this.scene = scene;
        return this;
    }

    public Window setTitle(String title) {
        glfwSetWindowTitle(getWindowId(), title);
        this.title = title;
        return this;
    }

    public Window setPos(Vector pos) {
        glfwSetWindowPos(getWindowId(), (int) pos.getX(), (int) pos.getY());
        this.pos = pos.getMod();
        return this;
    }

    public Window setSize(Vector size) {
        if (!size.equals(getSize())) doOnResize.reset();
        glfwSetWindowSize(getWindowId(), (int) size.getX(), (int) size.getY());
        this.size = size.getMod();
        return this;
    }

    public Window setVSync(boolean vSync) {
        glfwSwapInterval(vSync ? 1 : 0);
        this.vSync = vSync;
        return this;
    }

    public Window setAntiAliasing(int level) {
        if (getAntiAliasing() > 0) GLFW.glfwWindowHint(GLFW_SAMPLES, level);
        this.antiAliasing = level;
        return this;
    }

    public Window setUIScale(double uiScale) {
        this.uiScale = uiScale;
        return this;
    }

    public Window setOpen(boolean open) {
        this.open = open;
        return this;
    }

    public Window setDrawn(boolean drawn) {
        this.drawn = drawn;
        return this;
    }

    public Window setTicking(boolean ticking) {
        this.ticking = ticking;
        return this;
    }

    public Window setEconomic(boolean economic) {
        this.economic = economic;
        return this;
    }

    public Window setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public Window setMaxTPS(int maxTPS) {
        this.maxTPS = maxTPS;
        return this;
    }

    public Window setMaxFPS(int maxFPS) {
        this.maxFPS = maxFPS;
        return this;
    }


    public long getWindowId() {
        return windowId;
    }

    public Scene getScene() {
        return scene;
    }

    public String getTitle() {
        return title;
    }

    public Vector getPos() {
        return pos;
    }

    public Vector getSize() {
        return size;
    }

    public Vector getScaledSize() {
        return getSize().getMultiplied(1/getUIScale());
    }

    public Vector getMousePos() {
        return mousePos;
    }

    public Vector getScaledMousePos() {
        return getMousePos().getMultiplied(1/getUIScale());
    }

    public boolean getVSync() {
        return vSync;
    }

    public int getAntiAliasing() {
        return antiAliasing;
    }

    public double getUIScale() {
        return uiScale;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean shouldDraw() {
        return drawn;
    }

    public boolean shouldTick() {
        return ticking;
    }

    public boolean isEconomic() {
        return economic;
    }

    public boolean isDebug() {
        return debug;
    }

    public int getMaxTPS() {
        return maxTPS;
    }

    public int getMaxFPS() {
        return maxFPS;
    }

    public int getTPS() {
        return tps;
    }

    public int getFPS() {
        return fps;
    }

}