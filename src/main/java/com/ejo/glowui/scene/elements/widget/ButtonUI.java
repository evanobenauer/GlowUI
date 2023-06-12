package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.event.events.MouseClickEvent;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.Mouse;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.util.NumberUtil;

public class ButtonUI extends WidgetUI {

    private ColorE color;

    private ColorE baseColor;

    public ButtonUI(Scene scene, String title, Vector pos, Vector size, ColorE color, Runnable action) {
        super(scene,title,pos,size, true, true, action);
        this.color = color;

        this.baseColor = color;
    }

    public ButtonUI(Scene scene, Vector pos, Vector size, ColorE color, Runnable action) {
        this(scene,"",pos,size,color,action);
    }

    @Override
    protected void drawWidget() {
        new RectangleUI(getScene(),getPos(),getSize(),getColor()).draw();
        //Draw Name Here
    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        super.onMouseClick(button, action, mods, mousePos);
        if (button == Mouse.BUTTON_LEFT.getId()) {
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
                if (action == Mouse.ACTION_RELEASE) {
                    getAction().run();
                }
            }
            if (action == Mouse.ACTION_RELEASE) {
                setColor(baseColor);
            }
        }
    }


    public void setColor(ColorE color) {
        this.color = color;
    }


    public ColorE getColor() {
        return color;
    }

}
