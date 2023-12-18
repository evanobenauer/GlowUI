package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowlib.setting.Container;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.util.UIUtil;
import com.ejo.glowui.util.Mouse;
import com.ejo.glowui.util.render.QuickDraw;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.util.NumberUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class ModeCycleUI<T> extends SettingWidget<T> {

    private final ArrayList<T> modes;

    private ColorE baseColor;
    private ColorE colorR;
    private ColorE colorL;

    private static int arrayNumber;

    @SafeVarargs
    public ModeCycleUI(String title, Vector pos, Vector size, ColorE color, Container<T> container, T... modes) {
        super(title,pos, size, true, true,container);
        updateValueFromContainer();
        this.modes = new ArrayList<>(Arrays.asList(modes));

        this.baseColor = color;
        this.colorR = color;
        this.colorL = color;
    }

    @SafeVarargs
    public ModeCycleUI(Vector pos, Vector size, ColorE color, Container<T> container, T... modes) {
        this("",pos,size,color,container,modes);
    }

    @Override
    protected void drawWidget(Scene scene, Vector mousePos) {
        //Draw Background
        QuickDraw.drawRect(getPos(),getSize(), UIUtil.WIDGET_BACKGROUND);

        double border = 4;//getSize().getY()/5;

        //Draw Mode Arrows
        double borderArrow = border/2;
        QuickDraw.drawArrow(getPos().getAdded(borderArrow,borderArrow),getColorL(),getSize().getY()-2*borderArrow,true);
        QuickDraw.drawArrow(getPos().getAdded(borderArrow,borderArrow).getAdded(getSize().getX() - getSize().getY() - borderArrow,0),getColorR(),getSize().getY()-2*borderArrow,false);

        //Draw Text
        String text = (hasTitle() ? getTitle() + ": " : "") + value;
        int fontSize = (int)getSize().getY();
        setUpDisplayText(text,border,fontSize);
        getDisplayText().drawCentered(scene, mousePos, getSize().getAdded(-border*2,-border*2));
    }

    @Override
    public void onKeyPress(Scene scene, int key, int scancode, int action, int mods) {
    }

    @Override
    public void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos) {
        if (isMouseOver()) {
            if (action == Mouse.ACTION_RELEASE && (button == Mouse.BUTTON_RIGHT.getId() || button == Mouse.BUTTON_LEFT.getId())) {

                boolean isRightButton = (button == Mouse.BUTTON_RIGHT.getId());
                int addNum = isRightButton ? 1 : -1;
                int endIndex = isRightButton ? getModes().size() - 1 : 0;
                int otherEndIndex = !isRightButton ? getModes().size() - 1 : 0;

                arrayNumber = getModes().indexOf(value);
                arrayNumber += addNum;
                if (arrayNumber - addNum != endIndex) {
                    try {
                        value = getModes().get(arrayNumber);
                    } catch (IndexOutOfBoundsException e) {
                        value = getModes().get(0);
                    }
                    getAction().run();
                } else { //If the value IS equal to the end index
                    value = getModes().get(otherEndIndex);
                    arrayNumber = otherEndIndex;
                    getAction().run();
                }
            }
            if (action == Mouse.ACTION_CLICK) {
                int[] colVal = {getColor().getRed(),getColor().getGreen(),getColor().getBlue()};
                for (int i = 0; i < colVal.length; i++) {
                    int col = colVal[i] - 50;
                    col = NumberUtil.getBoundValue(col,0,255).intValue();
                    colVal[i] = col;
                }
                if (button == Mouse.BUTTON_RIGHT.getId()) setColorR(new ColorE(colVal[0],colVal[1],colVal[2],getColor().getAlpha()));
                if (button == Mouse.BUTTON_LEFT.getId()) setColorL(new ColorE(colVal[0],colVal[1],colVal[2],getColor().getAlpha()));
            }
        }
        if (action == Mouse.ACTION_RELEASE) {
            if (button == Mouse.BUTTON_RIGHT.getId()) setColorR(baseColor);
            if (button == Mouse.BUTTON_LEFT.getId()) setColorL(baseColor);
        }
    }


    public ModeCycleUI<T> setColor(ColorE baseColor) {
        this.baseColor = baseColor;
        return this;
    }

    private ModeCycleUI<T> setColorR(ColorE colorR) {
        this.colorR = colorR;
        return this;
    }

    private ModeCycleUI<T> setColorL(ColorE colorL) {
        this.colorL = colorL;
        return this;
    }


    public ColorE getColor() {
        return baseColor;
    }

    private ColorE getColorR() {
        return colorR;
    }

    private ColorE getColorL() {
        return colorL;
    }


    public ArrayList<T> getModes() {
        return modes;
    }

}
