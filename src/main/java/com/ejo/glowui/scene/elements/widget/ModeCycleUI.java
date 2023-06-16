package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.TextUI;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.DrawUtil;
import com.ejo.glowui.util.Mouse;
import com.ejo.glowui.util.QuickDraw;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.misc.Container;
import org.util.glowlib.util.NumberUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ModeCycleUI<T> extends WidgetUI {

    private final Container<T> container;
    private final ArrayList<T> modes;
    private ColorE baseColor;
    private ColorE colorR;
    private ColorE colorL;

    private T mode;

    private static int arrayNumber;

    @SafeVarargs
    public ModeCycleUI(Scene scene, String title, Container<T> container, Vector pos, Vector size, ColorE color, T... modes) {
        super(scene, title,pos, size, true, true,null);
        this.container = container;
        this.modes = new ArrayList<>(Arrays.asList(modes));

        this.baseColor = color;
        this.colorR = color;
        this.colorL = color;

        this.mode = container.get();

        setAction(() -> container.set(mode));
    }

    @SafeVarargs
    public ModeCycleUI(Scene scene, Container<T> container, Vector pos, Vector size, ColorE color, T... modes) {
        this(scene,"",container,pos,size,color,modes);
    }


    @Override
    protected void drawWidget() {
        //TODO: Add arrows to each side that darken when clicked and then on release, proceed
        //Draw Background
        new RectangleUI(getScene(),getPos(),getSize(), DrawUtil.WIDGET_BACKGROUND).draw();

        //Draw Mode Arrows
        QuickDraw.drawArrow(getScene(),getPos().getAdded(5 + 55,5),getColorL(),true);
        QuickDraw.drawArrow(getScene(),getPos().getAdded(5,5).getAdded(getSize().getX() - 55 - 10,0),getColorR(),false);

        //Draw the slider text
        String title = (hasTitle() ? getTitle() + ": " : "") + getContainer().get();
        TextUI text = new TextUI(getScene(),title,new Font("Arial", Font.PLAIN, (int)getSize().getY()),getPos(),ColorE.WHITE);
        text.drawCentered(getSize());
    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        super.onMouseClick(button, action, mods, mousePos);
        if (isMouseOver()) {
            if (action == 0) {
                if (button == 0) {
                    arrayNumber = getModes().indexOf(getContainer().get());
                    arrayNumber += 1;
                    if (arrayNumber != getModes().size()) {
                        mode = getModes().get(arrayNumber);
                        getAction().run();
                    } else {
                        mode = getModes().get(0);
                        arrayNumber = 0;
                        getAction().run();
                    }
                }
                if (button == 1) {
                    arrayNumber = getModes().indexOf(getContainer().get());
                    arrayNumber -= 1;
                    if (arrayNumber != -1) {
                        mode = getModes().get(arrayNumber);
                        getAction().run();
                    } else {
                        mode = (getModes().get(getModes().size() - 1));
                        arrayNumber = getModes().size() - 1;
                        getAction().run();
                    }
                }
            }
            if (action == Mouse.ACTION_CLICK) {
                int[] colVal = {getBaseColor().getRed(),getBaseColor().getGreen(),getBaseColor().getBlue()};
                for (int i = 0; i < colVal.length; i++) {
                    int col = colVal[i] - 50;
                    col = NumberUtil.getBoundValue(col,0,255).intValue();
                    colVal[i] = col;
                }
                if (button == 0) setColorR(new ColorE(colVal[0],colVal[1],colVal[2],getBaseColor().getAlpha()));
                if (button == 1) setColorL(new ColorE(colVal[0],colVal[1],colVal[2],getBaseColor().getAlpha()));
            }
        }
        if (action == Mouse.ACTION_RELEASE) {
            setColorR(baseColor);
            setColorL(baseColor);
        }
    }

    public void setBaseColor(ColorE baseColor) {
        this.baseColor = baseColor;
    }

    public void setColorR(ColorE colorR) {
        this.colorR = colorR;
    }

    public void setColorL(ColorE colorL) {
        this.colorL = colorL;
    }

    public ColorE getBaseColor() {
        return baseColor;
    }

    public ColorE getColorR() {
        return colorR;
    }

    public ColorE getColorL() {
        return colorL;
    }

    public ArrayList<T> getModes() {
        return modes;
    }

    public Container<T> getContainer() {
        return container;
    }
}
