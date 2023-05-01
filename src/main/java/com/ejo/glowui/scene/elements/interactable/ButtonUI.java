package com.ejo.glowui.scene.elements.interactable;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

public class ButtonUI extends InteractableUI {

    private Vector size;
    private ColorE color;

    public ButtonUI(Scene scene, Vector pos, Vector size, ColorE color, Runnable action) {
        super(scene,pos,true,action);
        this.color = color;
        this.size = size;
    }

    @Override
    public void draw() {
        super.draw();
        baseRect.draw();
    }

    @Override
    public void tick() {
        baseRect = new RectangleUI(getScene(),getPos(),getSize(),getColor());
        super.tick();
    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        if (isMouseOver() && action == 1) getAction().run();
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


    public Vector getSize() {
        return size;
    }

    public ColorE getColor() {
        return color;
    }

}
