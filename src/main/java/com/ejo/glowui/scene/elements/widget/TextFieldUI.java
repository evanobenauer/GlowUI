package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.TextUI;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.DrawUtil;
import com.ejo.glowui.util.QuickDraw;
import org.lwjgl.glfw.GLFW;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.misc.Container;
import org.util.glowlib.setting.Setting;
import org.util.glowlib.time.StopWatch;

import java.awt.*;

public class TextFieldUI extends WidgetUI {

    private final Container<String> container;

    private final StopWatch cursorTimer = new StopWatch();
    private boolean typing;
    private String text;

    private ColorE color;

    public TextFieldUI(Scene scene, String title, Container<String> container, Vector pos, Vector size, ColorE color) {
        super(scene,title,pos,size,true,true,null);
        this.container = container;
        this.text = container.get();
        this.color = color;

        setAction(() -> container.set(text));
    }

    public TextFieldUI(Scene scene, Container<String> container, Vector pos, Vector size, ColorE color) {
        this(scene,"",container,pos,size,color);
    }

    @Override
    protected void drawWidget() {
        //Draw Background
        new RectangleUI(getScene(),getPos(),getSize(), DrawUtil.WIDGET_BACKGROUND).draw();

        //TODO: Figure out text scaling; Experiment a little
        // MAYBE use width scaling, when the text gets bigger than the width, make it smaller
        int bufferX = 10;

        //Define Text with title
        String msg = (hasTitle() ? getTitle() + ": " : "") + getContainer().get();
        getDisplayText().setText(msg);
        getDisplayText().setSize((int)(getSize().getY() / 1.33));
        getDisplayText().setPos(getPos().getAdded(0,getSize().getY()/2).getAdded(bufferX,-getDisplayText().getHeight()/2 - 2));
        getDisplayText().setColor(ColorE.WHITE);

        //Draw Blinking Cursor
        if (isTyping()) {
            getDisplayText().setColor(ColorE.GREEN);
            cursorTimer.start();
            if (cursorTimer.hasTimePassedS(1)) cursorTimer.restart();
            int alpha = cursorTimer.hasTimePassedMS(500) ? 255 : 0;
            double x = getPos().getX() + getDisplayText().getWidth() + bufferX;
            double y = getPos().getY() + 10;
            QuickDraw.drawRect(getScene(),new Vector(x,y),new Vector(6,getSize().getY() - 20), new ColorE(255,255,255,alpha));
        }

        //Draw Text Object
        getDisplayText().draw();
    }


    @Override
    public void onKeyPress(int key, int scancode, int action, int mods) {
        if (action == 0 || !isTyping()) return;
        String buttonText = getContainer().get();
        try {
            if (isTyping()) {
                if (key == GLFW.GLFW_KEY_ESCAPE || key == GLFW.GLFW_KEY_ENTER) {
                    setTyping(false);
                } else if (key == GLFW.GLFW_KEY_BACKSPACE) {
                    if (buttonText.length() > 0) buttonText = buttonText.substring(0, buttonText.length() - 1);
                } else if (key == GLFW.GLFW_KEY_SPACE) {
                    if (isKeyNumber(false,key)) buttonText = buttonText + " ";
                } else if (!GLFW.glfwGetKeyName(key, -1).equals("null")) {
                    if (GLFW.glfwGetKey(getScene().getWindow().getWindowId(), GLFW.GLFW_KEY_LEFT_SHIFT) == 1 || GLFW.glfwGetKey(getScene().getWindow().getWindowId(), GLFW.GLFW_KEY_RIGHT_SHIFT) == 1) {
                        buttonText = buttonText + GLFW.glfwGetKeyName(key, -1).toUpperCase();
                    } else {
                        if (isKeyNumber(false,key)) buttonText = buttonText + GLFW.glfwGetKeyName(key, -1);
                    }
                }
            }
        } catch (Exception e) {
        }

        this.text = buttonText;

        getAction().run();
    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        if (button == 0 && action == 1) {
            if (isTyping()) setTyping(false);
            if (isMouseOver()) setTyping(true);
        }
    }

    public static boolean isKeyNumber(boolean numbersOnly, int key) {
        //TODO: Add an option for numbers only
        return true;
    }

    public void setTyping(boolean typing) {
        this.typing = typing;
    }

    public void setColor(ColorE color) {
        this.color = color;
    }

    public boolean isTyping() {
        return typing;
    }

    public ColorE getColor() {
        return color;
    }

    public Container<String> getContainer() {
        return container;
    }
}
