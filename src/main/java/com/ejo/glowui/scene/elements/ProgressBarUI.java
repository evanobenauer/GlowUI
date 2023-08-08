package com.ejo.glowui.scene.elements;

import com.ejo.glowlib.setting.Container;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.DrawUtil;
import com.ejo.glowui.util.Fonts;
import com.ejo.glowui.util.QuickDraw;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.util.NumberUtil;

public class ProgressBarUI<T extends Number> extends ElementUI {

    private Container<T> container;

    private String title;

    private final double min;
    private final double max;

    private Vector size;

    private ColorE color;

    public ProgressBarUI(String title, Vector pos, Vector size, ColorE color, Container<T> container, double min, double max) {
        super(pos, true, true);
        this.title = title;
        this.size = size;
        this.container = container;
        this.min = min;
        this.max = max;
        this.color = color;
    }

    public ProgressBarUI(Vector pos, Vector size, ColorE color, Container<T> container, double min, double max) {
        this("", pos, size, color, container, min, max);
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        //Draw Background
        QuickDraw.drawRect(getPos(),getSize(),DrawUtil.WIDGET_BACKGROUND);

        int border = 4;//(int)getSize().getY()/5;

        //Draw Bar Fill
        double barPercent = NumberUtil.getBoundValue(getContainer().get().doubleValue(), getMin(), getMax()).doubleValue() / getMax();
        QuickDraw.drawRect(getPos().getAdded(border,border),new Vector((getSize().getX() - 2 * border) * barPercent,getSize().getY() - 2*border)
                .getAdded(new Vector(getContainer().get().doubleValue() == getMin() ? 2 : 0, 0)),getColor());

        //Draw Title
        QuickDraw.drawText(getTitle(), Fonts.getDefaultFont(20), getPos().getAdded(2, 2), ColorE.WHITE);
    }

    @Override
    protected void tickElement(Scene scene, Vector mousePos) {
    }


    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return false;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSize(Vector size) {
        this.size = size;
    }

    public void setColor(ColorE color) {
        this.color = color;
    }

    public void setContainer(Container<T> container) {
        this.container = container;
    }


    public String getTitle() {
        return title;
    }

    public Vector getSize() {
        return size;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public ColorE getColor() {
        return color;
    }

    public Container<T> getContainer() {
        return container;
    }

}