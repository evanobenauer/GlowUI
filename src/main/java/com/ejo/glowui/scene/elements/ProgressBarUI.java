package com.ejo.glowui.scene.elements;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.DrawUtil;
import com.ejo.glowui.util.QuickDraw;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.misc.Container;
import com.ejo.glowlib.util.NumberUtil;

import java.awt.*;

public class ProgressBarUI<T extends Number> extends ElementUI {

    private final Container<T> container;

    private String title;

    private final double min;
    private final double max;

    private Vector size;

    private ColorE color;

    public ProgressBarUI(Scene scene, String title, Vector pos, Vector size, Container<T> container, double min, double max, ColorE color) {
        super(scene, pos, true, true);
        this.title = title;
        this.size = size;
        this.container = container;
        this.min = min;
        this.max = max;
        this.color = color;
    }

    public ProgressBarUI(Scene scene, Vector pos, Vector size, Container<T> container, double min, double max, ColorE color) {
        this(scene, "", pos, size, container, min, max,color);
    }

    @Override
    public void draw() {
        super.draw();

        //Draw Background
        new RectangleUI(getScene(), getPos(), getSize(), DrawUtil.WIDGET_BACKGROUND).draw();

        int border = (int)getSize().getY()/5;

        //Draw Bar Fill
        double barPercent = NumberUtil.getBoundValue(getContainer().get().doubleValue(), getMin(), getMax()).doubleValue() / getMax();
        QuickDraw.drawRect(getScene(),getPos().getAdded(border,border),new Vector((getSize().getX() - 2 * border) * barPercent,getSize().getY() - 2*border)
                .getAdded(new Vector(getContainer().get().doubleValue() == getMin() ? 2 : 0, 0)),getColor());

        //Draw Title
        QuickDraw.drawText(getScene(), getTitle(), new Font("Arial", Font.PLAIN, 20), getPos().getAdded(2, 2), ColorE.WHITE);
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