package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import com.ejo.glowui.scene.elements.shape.LineUI;
import com.ejo.glowui.util.QuickDraw;

import java.util.ArrayList;
import java.util.Arrays;

public class SideBarUI extends WidgetUI {

    private final ArrayList<ElementUI> elementList = new ArrayList<>();

    //TODO: Maybe have the button centered and move with the animation
    private final ButtonUI buttonUI;

    private Type type;
    private double width;
    private ColorE color;

    private boolean open;

    public SideBarUI(Scene scene, String title, Vector buttonPos, Vector buttonSize, ColorE color, Type type, double width, ElementUI... elements) {
        super(scene, title, Vector.NULL, Vector.NULL, true, true,null);
        this.type = type;
        this.width = width;
        this.color = color;

        this.open = false; //Maybe set default open

        this.buttonUI = new ButtonUI(getScene(),buttonPos,buttonSize,getColor(),() -> setOpen(!isOpen()));

        addElements(elements);
    }

    //TODO: Add maintenance loop animation after clicking button to move sidebar from off the window to the window
    // Also add a type setting to choose between top bottom left right
    // also make sure to have a positionable button that activates the sidebar
    // Also make sure to add a list of Elements to belong to the sidebar

    @Override
    protected void drawWidget() {
        //Draw Button
        buttonUI.draw();
        double border = 2;
        double size = 2;
        QuickDraw.drawRect(getScene(),getButtonPos().getAdded(border,border*2),new Vector(getButtonSize().getX() - 2*border,size),ColorE.WHITE);
        QuickDraw.drawRect(getScene(),getButtonPos().getAdded(border,getButtonSize().getY()/2 - size / 2),new Vector(getButtonSize().getX() - 2*border,size),ColorE.WHITE);
        QuickDraw.drawRect(getScene(),getButtonPos().getAdded(border,getButtonSize().getY() - 2*border - size),new Vector(getButtonSize().getX() - 2*border,size),ColorE.WHITE);

        //The pos is always at the top left of the bar
        //TODO: Change this vector when using the animation when you eventually add it future evan
        Vector barPos = switch(getType()) {
            case TOP, LEFT -> Vector.NULL;
            case BOTTOM -> new Vector(0, getScene().getSize().getY() - getWidth());
            case RIGHT -> new Vector(getScene().getSize().getX() - getWidth(),0);
        };

        //Draw Background
        switch(getType()) {
            case TOP, BOTTOM -> QuickDraw.drawRect(getScene(),barPos,new Vector(getScene().getSize().getX(),getWidth()),getColor());
            case LEFT, RIGHT -> QuickDraw.drawRect(getScene(),barPos,new Vector(getWidth(),getScene().getSize().getY()),getColor());
        }

        //Draw Elements
        for (ElementUI elementUI : getElementList()) {
            elementUI.setPos(elementUI.getPos().getAdded(barPos));
            elementUI.draw();
            elementUI.setPos(elementUI.getPos().getAdded(barPos.getMultiplied(-1)));
        }
    }

    @Override
    public void tick() {
        this.buttonUI.tick();
        super.tick();

        //Tick Elements
        for (ElementUI elementUI : getElementList()) {
            elementUI.tick();
        }
    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        super.onMouseClick(button, action, mods, mousePos);
        this.buttonUI.onMouseClick(button,action,mods,mousePos);
    }

    public Vector setButtonPos(Vector pos) {
        return this.buttonUI.setPos(pos);
    }

    public void setButtonSize(Vector size) {
        this.buttonUI.setSize(size);
    }

    public double getWidth() {
        return width;
    }

    public void setColor(ColorE color) {
        this.color = color;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }


    public Vector getButtonPos() {
        return this.buttonUI.getPos();
    }

    public Vector getButtonSize() {
        return this.buttonUI.getSize();
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public ColorE getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public boolean isOpen() {
        return open;
    }


    private void addElements(ElementUI... elementUI) {
        elementList.addAll(Arrays.asList(elementUI));
    }

    public ArrayList<ElementUI> getElementList() {
        return elementList;
    }


    public enum Type {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

}
