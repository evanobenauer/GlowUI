package com.ejo.glowui.scene.elements;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.QuickDraw;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.misc.Container;
import org.util.glowlib.util.NumberUtil;

import java.awt.*;

public class ProgressBarUI<T extends Number> extends ElementUI {

    private final Container<T> container;

    private String title;

    private final double min;
    private final double max;

    private Vector size;

    public ProgressBarUI(Scene scene, String title, Vector pos, Vector size, Container<T> container, double min, double max) {
        super(scene, pos, true, true);
        this.title = title;
        this.size = size;
        this.container = container;
        this.min = min;
        this.max = max;
    }

    public ProgressBarUI(Scene scene, Vector pos, Vector size, Container<T> container, double min, double max) {
        this(scene, "", pos, size, container, min, max);
    }

    @Override
    public void draw() {
        super.draw();
        double barPercent = NumberUtil.getBoundValue(getContainer().get().doubleValue(), getMin(), getMax()).doubleValue() / getMax();

        new RectangleUI(getScene(), getPos(), getSize(), new ColorE(50, 50, 50, 200)).draw();
        int border = 10;
        new RectangleUI(
                getScene(),
                getPos().getAdded(new Vector(border, border)), new Vector(getSize().getX() * barPercent,
                getSize().getY()).getAdded(new Vector(getContainer().get().doubleValue() == getMin() ? 2 : -2 * border, -2 * border)),
                new ColorE(0, 125, 200, 150)).draw();

        QuickDraw.drawText(getScene(), getTitle(), new Font("Arial", Font.PLAIN, 20), getPos().getAdded(2,2),ColorE.WHITE);
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

    public Container<T> getContainer() {
        return container;
    }

}