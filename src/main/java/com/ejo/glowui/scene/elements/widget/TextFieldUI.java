package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowlib.setting.Container;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.util.DrawUtil;
import com.ejo.glowui.util.Fonts;
import com.ejo.glowui.util.Key;
import com.ejo.glowui.util.QuickDraw;
import org.lwjgl.glfw.GLFW;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.time.StopWatch;


public class TextFieldUI extends WidgetUI {

    private String text;
    private Container<String> container;

    private String hint;
    private boolean numbersOnly;
    private int charLimit;

    private ColorE color;

    private final StopWatch cursorTimer = new StopWatch();
    private boolean typing;

    public TextFieldUI(String title, Vector pos, Vector size, ColorE color, Container<String> container, String hint, boolean numbersOnly, int charLimit) {
        super(title, pos, size, true, true, null);
        this.container = container;
        this.text = container.get();
        this.hint = hint;
        this.numbersOnly = numbersOnly;
        this.charLimit = charLimit;
        this.color = color;

        setAction(() -> getContainer().set(text));
    }

    public TextFieldUI(Vector pos, Vector size, ColorE color, Container<String> container, String hint, boolean numbersOnly, int charLimit) {
        this("", pos, size, color, container, hint, numbersOnly,charLimit);
    }

    public TextFieldUI(String title, Vector pos, Vector size, ColorE color, Container<String> container, String hint, boolean numbersOnly) {
        this(title, pos, size, color, container, hint, numbersOnly,-1);
    }

    public TextFieldUI(Vector pos, Vector size, ColorE color, Container<String> container, String hint, boolean numbersOnly) {
        this("", pos, size, color, container, hint, numbersOnly,-1);
    }

    @Override
    protected void drawWidget(Scene scene, Vector mousePos) {
        //Draw Background
        QuickDraw.drawRect(getPos(),getSize(),DrawUtil.WIDGET_BACKGROUND);

        double border = 4;//getSize().getY()/5;

        //Prepare Text
        String msg = (hasTitle() ? getTitle() + ": " : "") + text;
        int size = (int) (getSize().getY() / 1.5);
        setUpDisplayText(msg,border,size);
        getDisplayText().setPos(getPos().getAdded(border, getSize().getY() / 2 - getDisplayText().getHeight() / 2));
        getDisplayText().setColor(ColorE.WHITE);

        //Draw Hint
        if (text.equals(""))
            QuickDraw.drawText(getHint(), Fonts.getDefaultFont(size), getPos()
                    .getAdded(border + getDisplayText().getWidth(), -2 + getSize().getY() / 2 - getDisplayText().getHeight() / 2)
                    , ColorE.GRAY);

        //Draw Blinking Cursor
        if (isTyping()) {
            getDisplayText().setColor(ColorE.GREEN);
            cursorTimer.start();
            if (cursorTimer.hasTimePassedS(1)) cursorTimer.restart();
            int alpha = cursorTimer.hasTimePassedMS(500) ? 255 : 0;

            String[] text = getDisplayText().getText().split("\\\\n");
            String lastRow = text[text.length - 1];
            double lastRowWidth = getDisplayText().getFontMetrics().stringWidth(lastRow) * getDisplayText().getScale();

            double x = getPos().getX() + lastRowWidth + border;
            double height = (getSize().getY() - 2 * border) / text.length;
            double y = getPos().getY() + getSize().getY() - height - border/text.length;
            QuickDraw.drawRect(new Vector(x, y), new Vector(2, height), new ColorE(255, 255, 255, alpha));
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
        this.text = getContainer().get();

        if (isTyping()) {
            if ((Key.KEY_LSHIFT.isKeyDown() || Key.KEY_RSHIFT.isKeyDown()) && key == Key.KEY_ENTER.getId()) {
                text += "\\n";
            } else if (key == GLFW.GLFW_KEY_ESCAPE || key == GLFW.GLFW_KEY_ENTER) {
                setTyping(false);
            } else if (key == GLFW.GLFW_KEY_DELETE) {
                text = "";
            } else if (key == GLFW.GLFW_KEY_SPACE) {
                if (shouldEnterKey(key, text)) text += " ";
            } else if (key == GLFW.GLFW_KEY_BACKSPACE) {
                if (text.length() > 1) {
                    if (text.substring(text.length() - 2).equals("\\n")) {
                        text = text.substring(0, text.length() - 3);
                    } else {
                        text = text.substring(0, text.length() - 1);
                    }
                } else if (text.length() > 0) text = "";
            } else if ((Key.KEY_LCONTROL.isKeyDown() || Key.KEY_RCONTROL.isKeyDown()) && key == Key.KEY_C.getId()) {
                GLFW.glfwSetClipboardString(scene.getWindow().getWindowId(), text);
            } else if ((Key.KEY_LCONTROL.isKeyDown() || Key.KEY_RCONTROL.isKeyDown()) && key == Key.KEY_X.getId()) {
                GLFW.glfwSetClipboardString(scene.getWindow().getWindowId(), text);
                text = "";
            } else if ((Key.KEY_LCONTROL.isKeyDown() || Key.KEY_RCONTROL.isKeyDown()) && key == Key.KEY_Z.getId()) {
                //TODO: Add Undo
            } else if (GLFW.glfwGetKeyName(key, scancode) != null && !GLFW.glfwGetKeyName(key, scancode).equals("null")) {
                if (Key.KEY_LSHIFT.isKeyDown() || Key.KEY_RSHIFT.isKeyDown()) {
                    text += getShiftValue(key);
                } else if ((Key.KEY_LCONTROL.isKeyDown() || Key.KEY_RCONTROL.isKeyDown()) && key == Key.KEY_V.getId()) {
                    text += GLFW.glfwGetClipboardString(scene.getWindow().getWindowId());
                } else {
                    if (shouldEnterKey(key, text)) text += GLFW.glfwGetKeyName(key, scancode);
                }
            }
        }

        getAction().run();
    }

    @Override
    public void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos) {
        if (button == 0 && action == 1) {
            if (isTyping()) setTyping(false);
            if (isMouseOver()) setTyping(true);
        }
    }

    private boolean shouldEnterKey(int key, String text) {
        if (getCharLimit() != -1 && text.length() >= getCharLimit()) return false;
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

    private String getShiftValue(int key) {
        if (key == Key.KEY_GRAVE.getId()) return "~";
        else if (key == Key.KEY_1.getId()) return "!";
        else if (key == Key.KEY_2.getId()) return "@";
        else if (key == Key.KEY_3.getId()) return "#";
        else if (key == Key.KEY_4.getId()) return "$";
        else if (key == Key.KEY_5.getId()) return "%";
        else if (key == Key.KEY_6.getId()) return "^";
        else if (key == Key.KEY_7.getId()) return "&";
        else if (key == Key.KEY_8.getId()) return "*";
        else if (key == Key.KEY_9.getId()) return "(";
        else if (key == Key.KEY_0.getId()) return ")";
        else if (key == Key.KEY_MINUS.getId()) return "_";
        else if (key == Key.KEY_EQUALS.getId()) return "+";
        else if (key == Key.KEY_LBRACKET.getId()) return "{";
        else if (key == Key.KEY_RBRACKET.getId()) return "}";
        else if (key == Key.KEY_BACK_SLASH.getId()) return "|";
        else if (key == Key.KEY_SEMICOLON.getId()) return ":";
        else if (key == Key.KEY_APOSTROPHE.getId()) return "\"";
        else if (key == Key.KEY_COMMA.getId()) return "<";
        else if (key == Key.KEY_PERIOD.getId()) return ">";
        else if (key == Key.KEY_FORWARD_SLASH.getId()) return "?";
        else return GLFW.glfwGetKeyName(key, -1).toUpperCase();
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

    public void setCharLimit(int charLimit) {
        this.charLimit = charLimit;
    }

    public void setContainer(Container<String> container) {
        this.container = container;
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

    public int getCharLimit() {
        return charLimit;
    }

    public Container<String> getContainer() {
        return container;
    }
}