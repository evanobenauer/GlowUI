package com.ejo.glowui.screen.elements.interactable;

import com.ejo.glowui.screen.Screen;
import com.ejo.glowui.screen.elements.shape.RectangleUI;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

public class ButtonUI extends InteractableUI {

    private Vector size;
    private ColorE color;

    public RectangleUI baseRect;

    public ButtonUI(Screen screen, Vector pos, Vector size, ColorE color, Runnable action) {
        super(screen,pos,true,action);
        this.size = size;
        this.color = color;
        baseRect = new RectangleUI(getScreen(),getPos(),getSize(),getColor());
    }


    @Override
    public void draw() {
        if (shouldRender()) {
            baseRect.draw();
        }
    }

    @Override
    public void tick() {
        baseRect = new RectangleUI(getScreen(),getPos(),getSize(),getColor());
        super.tick();
    }

    @Override
    public void onKeyPress(int key, int scancode, int action, int mods) {
    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        if (isMouseOver() && action == 1) {
            getAction().run();
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
