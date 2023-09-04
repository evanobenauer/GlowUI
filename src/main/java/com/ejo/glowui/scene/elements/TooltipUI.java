package com.ejo.glowui.scene.elements;

import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.util.GLManager;
import com.ejo.glowui.util.QuickDraw;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * TooltipUI is a drawable tooltip that will render if the mouse is hovering the element to which the tooltip belongs
 * The tooltip contains its own list of elements which it will draw within its bounds
 */
public class TooltipUI extends ElementUI {

    private ElementUI element;

    private ColorE color;
    private Vector size;

    private final ArrayList<ElementUI> elementList;

    public TooltipUI(ElementUI element, Vector size, ColorE color, ElementUI... elements) {
        super(Vector.NULL, true, true);
        this.element = element;
        this.size = size;
        this.color = color;
        this.elementList = new ArrayList<>(Arrays.asList(elements));
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        if (!getElement().isMouseOver()) return;
        Vector startPos = mousePos.getAdded(0, -getSize().getY());
        GLManager.translate(startPos);
        QuickDraw.drawRect(Vector.NULL, getSize(), getColor());
        for (ElementUI elementUI : getElementList()) {
            elementUI.draw(scene, mousePos.getAdded(startPos.getMultiplied(-1)));
        }
        GLManager.translate(startPos.getMultiplied(-1));
    }

    @Override
    protected void tickElement(Scene scene, Vector mousePos) {
        if (!getElement().isMouseOver()) return;
        Vector startPos = mousePos.getAdded(0, -getSize().getY());
        for (ElementUI elementUI : getElementList()) {
            elementUI.tick(scene, mousePos.getAdded(startPos.getMultiplied(-1)));
        }
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return false;
    }

    public void setColor(ColorE color) {
        this.color = color;
    }

    public void setSize(Vector size) {
        this.size = size;
    }

    public void setElement(ElementUI element) {
        this.element = element;
    }

    public ColorE getColor() {
        return color;
    }

    public Vector getSize() {
        return size;
    }

    public ElementUI getElement() {
        return element;
    }

    public ArrayList<ElementUI> getElementList() {
        return elementList;
    }

}
