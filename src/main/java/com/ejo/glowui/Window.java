package com.ejo.glowui;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.event.EventRegistry;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.util.glowlib.math.Vector;
import org.util.glowlib.time.StopWatch;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;

/**
 * The Window class is a container class for the LWJGL3 WindowID. It incorporates GLFW methods in an easy to use object oriented
 * fashion for the window.
 */
public class Window {

    private long windowId;

    private String title;
    private Vector pos;
    private Vector size;
    private int maxTPS;

    public int ticks;
    public int frames;

    private Scene scene;

    public Window(String title, Vector pos, Vector size, Scene startingScene, int maxTPS) {
        this.title = title;
        this.pos = pos;
        this.size = size;
        this.maxTPS = maxTPS;
        this.scene = startingScene;
    }

    /**
     * This method initiates the window using LWJGL3. All functions are called to set up the position, id, V-Sync, and more.
     * The final action of this method is to set the default screen of the window, which is defined in the constructor
     */
    public void init() {
        final long NULL = 0L;
        if (!glfwInit()) throw new IllegalStateException("Failed to init GLFW");

        //Creating the window
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        windowId = glfwCreateWindow((int)getSize().getX(), (int)getSize().getY(), getTitle(), NULL, NULL);
        if (getWindowId() == NULL) throw new IllegalStateException("Window could not be created");

        //Creating the monitor
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        //Show the window
        setPos(getPos());
        glfwShowWindow(getWindowId());

        //Sets the window context to display graphics
        glfwMakeContextCurrent(getWindowId());
        GL.createCapabilities();
        GL11.glClearColor(0f,0f,0f,0f);

        glfwSwapInterval(1);// Enable v-sync

        setScene(getScene());
    }

    /**
     * The MaintenanceLoop is a loop that runs side by side with the TickLoop and RenderLoop.
     * Its intended job is to have features that don't fit into tick and render to be called in order to modify
     * or "Do Maintenance" on the application
     */
    public void startMaintenanceLoop() {
        Thread thread = new Thread(() -> {
            while (true) {
                sleepThread(1);
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
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            while (!glfwWindowShouldClose(getWindowId())) { //While the window is open
                sleepThread(1);
                if (stopWatch.hasTimePassedMS(1000f/(getMaxTPS()))) {
                    stopWatch.restart();
                    tick();
                    ticks++;
                }
            }
        });
        thread.setName("Tick Thread");
        thread.start();
    }

    /**
     * The RenderLoop is the final instantiated loop. It is a part of the main thread alongside the window instantiation.
     * Its goal is to strictly have render items placed in it. The loop will update at the refresh rate of the monitor.
     * In order to have consistently paced actions, use the tick loop
     */
    public void runRenderLoop() {
        while (!glfwWindowShouldClose(getWindowId())) { //While the window is open
            updateWindow();
            draw();
            frames++;
        }
    }

    private void sleepThread(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void tick() {
        onKeyPress();
        onMouseClick();
        getScene().tick();
        EventRegistry.EVENT_TICK.post(this, getScene());
    }

    private void updateWindow() {
        IntBuffer buffer = BufferUtils.createIntBuffer(1);
        glfwGetWindowPos(getWindowId(), buffer, null);
        double x = buffer.get(0);
        glfwGetWindowPos(getWindowId(), null, buffer);
        double y = buffer.get(0);
        Vector pos = new Vector(x,y);
        setPos(pos);

        glfwGetWindowSize(getWindowId(), buffer, null);
        double w = buffer.get(0);
        glfwGetWindowSize(getWindowId(), null, buffer);
        double h = buffer.get(0);
        Vector size = new Vector(w,h);
        if (size.getMagnitude() != 0) setSize(size);
    }

    public void draw() {
        GL.createCapabilities();
        GL11.glViewport(0, 0, (int)getSize().getX(), (int)getSize().getY());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, getSize().getX(), getSize().getY(), 0,-1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        this.getScene().draw(); //Draw the screen
        EventRegistry.EVENT_RENDER.post(this, getScene()); //Render event after drawing the screen

        glfwSwapBuffers(getWindowId()); //Finish Drawing here
        GLFW.glfwPollEvents();
    }


    private void onKeyPress() {
        glfwSetKeyCallback(getWindowId(), (window, key, scancode, action, mods) -> {
            this.getScene().onKeyPress(key, scancode, action, mods);
            EventRegistry.EVENT_KEY_PRESS.post(window, key, scancode, action, mods); //Key Event to be used outside of class
        });
    }

    private void onMouseClick() {
        glfwSetMouseButtonCallback(getWindowId(), (window, button, action, mods) -> {
            this.getScene().onMouseClick(button, action, mods, getMousePos());
            EventRegistry.EVENT_MOUSE_CLICK.post(window, button, action, mods, getMousePos()); //Mouse click event to be used outside of class
        });
    }


    public void setScene(Scene scene) {
        scene.setWindow(this);
        this.scene = scene;
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(getWindowId(), title);
        this.title = title;
    }

    public void setPos(Vector pos) {
        glfwSetWindowPos(getWindowId(), (int) pos.getX(), (int) pos.getY());
        this.pos = pos;
    }

    public void setSize(Vector size) {
        glfwSetWindowSize(getWindowId(), (int)size.getX(), (int)size.getY());
        this.size = size;
    }

    public void setMaxTPS(int maxTPS) {
        this.maxTPS = maxTPS;
    }

    public Vector getMousePos() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(getWindowId(), buffer, null);
        double mouseX = buffer.get(0);
        glfwGetCursorPos(getWindowId(), null, buffer);
        double mouseY = buffer.get(0);
        return new Vector(mouseX, mouseY);
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

    public int getMaxTPS() {
        return maxTPS;
    }

}