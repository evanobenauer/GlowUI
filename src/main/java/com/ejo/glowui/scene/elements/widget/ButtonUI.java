package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.Mouse;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.util.NumberUtil;

public class ButtonUI extends WidgetUI {

    private ColorE baseColor;
    private ColorE color;

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
        //Draw Background Color
        new RectangleUI(getScene(),getPos(),getSize(),getColor()).draw();

        double border = getSize().getY() / 5;

        //Draw Text
        int fontSize = (int)(getSize().getY());
        setUpDisplayText(getTitle(),border,fontSize);
        getDisplayText().drawCentered(getSize().getAdded(-border*2,-border*2));
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
