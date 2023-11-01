package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.util.Mouse;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.util.NumberUtil;
import com.ejo.glowui.util.render.QuickDraw;

public class ButtonUI extends WidgetUI {

    private ColorE baseColor;
    private ColorE color;

    private MouseButton mouseButton;

    public ButtonUI(String title, Vector pos, Vector size, ColorE color, MouseButton mouseButton, Runnable action) {
        super(title,pos,size, true, true, action);
        this.mouseButton = mouseButton;
        this.baseColor = color;
        this.color = color;
    }

    public ButtonUI(Vector pos, Vector size, ColorE color, MouseButton mouseButton, Runnable action) {
        this("",pos,size,color,mouseButton, action);
    }

    @Override
    protected void drawWidget(Scene scene, Vector mousePos) {
        //Draw Background Color
        QuickDraw.drawRect(getPos(),getSize(),getColor());

        double border = 4;//getSize().getY() / 5;

        //Draw Text
        int fontSize = (int)(getSize().getY());
        setUpDisplayText(getTitle(),border,fontSize);
        getDisplayText().drawCentered(scene, mousePos, getSize().getAdded(-border*2,-border*2));
    }

    @Override
    protected void tickWidget(Scene scene, Vector mousePos) {
    }

    @Override
    public void onKeyPress(Scene scene, int key, int scancode, int action, int mods) {
    }

    @Override
    public void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos) {
        if (button == getMouseButton().getId() || getMouseButton().equals(MouseButton.ALL)) {
            if (action == Mouse.ACTION_CLICK) this.baseColor = getColor();

            if (isMouseOver()) {
                if (action == Mouse.ACTION_CLICK) {
                    int[] colVal = {getColor().getRed(),getColor().getGreen(),getColor().getBlue()};
                    for (int i = 0; i < colVal.length; i++) {
                        int col = colVal[i] - 50;
                        col = NumberUtil.getBoundValue(col,0,255).intValue();
                        colVal[i] = col;
                    }
                    setColor(new ColorE(colVal[0],colVal[1],colVal[2],getColor().getAlpha()));
                }
                if (action == Mouse.ACTION_RELEASE) getAction().run();
            }

            if (action == Mouse.ACTION_RELEASE) setColor(baseColor);
        }
    }


    public ButtonUI setColor(ColorE color) {
        this.color = color;
        return this;
    }

    public ButtonUI setMouseButton(MouseButton mouseButton) {
        this.mouseButton = mouseButton;
        return this;
    }


    public ColorE getColor() {
        return color;
    }

    public MouseButton getMouseButton() {
        return mouseButton;
    }

    public enum MouseButton {
        LEFT(Mouse.BUTTON_LEFT.getId()),
        RIGHT(Mouse.BUTTON_RIGHT.getId()),
        MIDDLE(Mouse.BUTTON_MIDDLE.getId()),
        ALL(-1);

        final int id;
        MouseButton(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

}
