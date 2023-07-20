package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.DrawUtil;
import com.ejo.glowui.util.QuickDraw;
import org.lwjgl.glfw.GLFW;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.misc.Container;
import com.ejo.glowlib.time.StopWatch;

import java.awt.*;

public class TextFieldUI extends WidgetUI {

    private String text;

    private final Container<String> container;
    private String hint;
    private boolean numbersOnly;

    private ColorE color;

    private final StopWatch cursorTimer = new StopWatch();
    private boolean typing;

    public TextFieldUI(String title, Vector pos, Vector size, ColorE color, Container<String> container, String hint, boolean numbersOnly) {
        super(title, pos, size, true, true, null);
        this.container = container;
        this.text = container.get();
        this.hint = hint;
        this.numbersOnly = numbersOnly;
        this.color = color;

        setAction(() -> container.set(text));
    }

    public TextFieldUI(Vector pos, Vector size, ColorE color, Container<String> container, String hint, boolean numbersOnly) {
        this("", pos, size, color, container, hint, numbersOnly);
    }

    @Override
    protected void drawWidget(Scene scene, Vector mousePos) {
        //Draw Background
        QuickDraw.drawRect(getPos(),getSize(),DrawUtil.WIDGET_BACKGROUND);

        double border = getSize().getY()/5;

        //Prepare Text
        String msg = (hasTitle() ? getTitle() + ": " : "") + text;
        int size = (int) (getSize().getY() / 1.5);
        setUpDisplayText(msg,border,size);
        getDisplayText().setPos(getPos().getAdded(border, -2 + getSize().getY() / 2 - getDisplayText().getHeight() / 2));
        getDisplayText().setColor(ColorE.WHITE);

        //Draw Hint
        if (text.equals(""))
            QuickDraw.drawText(getHint(), new Font("Arial", Font.PLAIN, size), getPos()
                    .getAdded(border + getDisplayText().getWidth(), -2 + getSize().getY() / 2 - getDisplayText().getHeight() / 2)
                    , ColorE.GRAY);

        //Draw Blinking Cursor
        if (isTyping()) {
            getDisplayText().setColor(ColorE.GREEN);
            cursorTimer.start();
            if (cursorTimer.hasTimePassedS(1)) cursorTimer.restart();
            int alpha = cursorTimer.hasTimePassedMS(500) ? 255 : 0;
            double x = getPos().getX() + getDisplayText().getWidth() + border;
            double y = getPos().getY() + border;
            QuickDraw.drawRect(new Vector(x, y), new Vector(2, getSize().getY() - 2 * border), new ColorE(255, 255, 255, alpha));
        }

        //Draw Text Object
        getDisplayText().draw(scene, mousePos);
    }

    @Override
    protected void tickWidget(Scene scene, Vector mousePos) {
        this.text = getContainer().get(); //Consistently sync the value of the container and the value of the widget
    }

    @Override
    public void onKeyPress(Scene scene, int key, int scancode, int action, int mods) {
        if (action == 0 || !isTyping()) return;
        String buttonText = getContainer().get();
        try {
            if (isTyping()) {
                if (key == GLFW.GLFW_KEY_ESCAPE || key == GLFW.GLFW_KEY_ENTER) {
                    setTyping(false);
                } else if (key == GLFW.GLFW_KEY_BACKSPACE) {
                    if (buttonText.length() > 0) buttonText = buttonText.substring(0, buttonText.length() - 1);
                } else if (key == GLFW.GLFW_KEY_SPACE) {
                    if (shouldEnterKey(key)) buttonText = buttonText + " ";
                } else if (!GLFW.glfwGetKeyName(key, -1).equals("null")) {
                    if (GLFW.glfwGetKey(scene.getWindow().getWindowId(), GLFW.GLFW_KEY_LEFT_SHIFT) == 1 || GLFW.glfwGetKey(scene.getWindow().getWindowId(), GLFW.GLFW_KEY_RIGHT_SHIFT) == 1) {
                        buttonText = buttonText + GLFW.glfwGetKeyName(key, -1).toUpperCase();
                    } else {
                        if (shouldEnterKey(key)) buttonText = buttonText + GLFW.glfwGetKeyName(key, -1);
                    }
                }
            }
        } catch (Exception e) {
        }

        this.text = buttonText;

        getAction().run();
    }

    @Override
    public void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos) {
        if (button == 0 && action == 1) {
            if (isTyping()) setTyping(false);
            if (isMouseOver()) setTyping(true);
        }
    }

    public boolean shouldEnterKey(int key) {
        if (isNumbersOnly()) {
            return key == GLFW.GLFW_KEY_PERIOD ||
                    key == GLFW.GLFW_KEY_KP_SUBTRACT ||
                    key == GLFW.GLFW_KEY_MINUS ||
                    key == GLFW.GLFW_KEY_0 ||
                    key == GLFW.GLFW_KEY_1 ||
                    key == GLFW.GLFW_KEY_2 ||
                    key == GLFW.GLFW_KEY_3 ||
                    key == GLFW.GLFW_KEY_4 ||
                    key == GLFW.GLFW_KEY_5 ||
                    key == GLFW.GLFW_KEY_6 ||
                    key == GLFW.GLFW_KEY_7 ||
                    key == GLFW.GLFW_KEY_8 ||
                    key == GLFW.GLFW_KEY_9 ||
                    key == GLFW.GLFW_KEY_KP_0 ||
                    key == GLFW.GLFW_KEY_KP_1 ||
                    key == GLFW.GLFW_KEY_KP_2 ||
                    key == GLFW.GLFW_KEY_KP_3 ||
                    key == GLFW.GLFW_KEY_KP_4 ||
                    key == GLFW.GLFW_KEY_KP_5 ||
                    key == GLFW.GLFW_KEY_KP_6 ||
                    key == GLFW.GLFW_KEY_KP_7 ||
                    key == GLFW.GLFW_KEY_KP_8 ||
                    key == GLFW.GLFW_KEY_KP_9;
        } else {
            return true;
        }
    }

    public void setColor(ColorE color) {
        this.color = color;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setTyping(boolean typing) {
        this.typing = typing;
    }

    public void setNumbersOnly(boolean numbersOnly) {
        this.numbersOnly = numbersOnly;
    }


    public ColorE getColor() {
        return color;
    }

    public String getHint() {
        return hint;
    }

    public boolean isTyping() {
        return typing;
    }

    public boolean isNumbersOnly() {
        return numbersOnly;
    }

    public Container<String> getContainer() {
        return container;
    }
}