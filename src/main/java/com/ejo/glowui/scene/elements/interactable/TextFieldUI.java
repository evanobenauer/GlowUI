package com.ejo.glowui.scene.elements.interactable;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;


//TODO: INCOMPLETE PLACEHOLDER
public class TextFieldUI extends InteractableUI {

    private Vector size;
    private ColorE color;

    public RectangleUI baseRect;

    public TextFieldUI(Scene scene, Vector pos, Vector size, ColorE color, Runnable action) {
        super(scene,pos,true,action);
        this.size = size;
        this.color = color;
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
        //TODO MAKE THIS TEXT FIELD
    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        if (isMouseOver()) {
            if (action == 1) {
                //activate text field
            }
        }  else {
            //If the mouse is not over, unselect
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
}
