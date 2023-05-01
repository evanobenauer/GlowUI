package com.ejo.glowui.scene.elements.interactable;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.misc.Container;

//TODO: INCOMPLETE PLACEHOLDER
public class SliderUI<T extends Number> extends InteractableUI {

    private Vector size;
    private ColorE color;
    private final Container<T> container;

    public RectangleUI baseRect;

    public T value;

    public SliderUI(Scene scene, Vector pos, Vector size, ColorE color, Container<T> container) {
        super(scene,pos,true,null);
        this.size = size;
        this.color = color;
        this.container = container;

        this.value = null;
        setAction(() -> container.set(value));

        baseRect = new RectangleUI(getScene(),getPos(),getSize(),getColor());
    }


    @Override
    public void draw() {
        if (shouldRender()) {
            baseRect.draw();
        }
    }

    @Override
    public void tick() {
        baseRect = new RectangleUI(getScene(),getPos(),getSize(),getColor());
        super.tick();
    }

    @Override
    public void onKeyPress(int key, int scancode, int action, int mods) {
    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        //TODO: MAKE SLIDER CODE HERE
        // have the action be setting a setting
        if (isMouseOver()) {
            //stuff
        }
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return baseRect.updateMouseOver(mousePos);
    }


    public void setSize(Vector size) {
        this.size = size;
    }

    public void setColor(ColorE color) {
        this.color = color;
    }


    @Override
    public boolean isMouseOver() {
        return baseRect.isMouseOver();
    }

    public Vector getSize() {
        return size;
    }

    public ColorE getColor() {
        return color;
    }

    public Container<T> getContainer() {
        return container;
    }
}
