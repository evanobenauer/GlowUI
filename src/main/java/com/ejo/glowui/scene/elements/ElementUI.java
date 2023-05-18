package com.ejo.glowui.scene.elements;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.construct.IDrawable;
import com.ejo.glowui.scene.elements.construct.IInput;
import com.ejo.glowui.scene.elements.construct.IComponent;
import com.ejo.glowui.scene.elements.construct.ITick;
import org.util.glowlib.math.Vector;

public abstract class ElementUI implements IComponent, IDrawable, ITick, IInput {

    private final Scene scene;
    private Vector pos;

    private boolean rendered;
    private boolean ticking;
    protected boolean mouseOver;

    public ElementUI(Scene scene, Vector pos, boolean shouldRender, boolean shouldTick) {
        this.scene = scene;
        this.pos = pos;
        this.rendered = shouldRender;
        this.ticking = shouldTick;
        this.mouseOver = false;
    }

    /**
     * The draw method will draw the attributes of the UI element. In every extension of the element, make sure the super comes first
     */
    @Override
    public void draw() {
        if (!shouldRender()) return;
    }

    @Override
    public void tick() {
        if (!shouldTick()) return;
        mouseOver = updateMouseOver(getScene().getWindow().getMousePos());
    }

    @Override
    public void onKeyPress(int key, int scancode, int action, int mods) {
        if (!shouldTick()) return;
    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        if (!shouldTick()) return;
    }


    /**
     * Stops the element from ticking and un-renders the element. This is useful for "removing" elements with an interaction from another element to avoid
     * a concurrent modification exception
     * @param disabled
     */
    public void disable(boolean disabled) {
        setRendered(false);
        setTicking(false);
    }


    public void setRendered(boolean shouldRender) {
        this.rendered = shouldRender;
    }

    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }

    public abstract boolean updateMouseOver(Vector mousePos);


    public Vector setPos(Vector pos) {
        return this.pos = pos;
    }


    public boolean shouldRender() {
        return this.rendered;
    }

    public boolean shouldTick() {
        return this.ticking;
    }

    public boolean isMouseOver() {
        return this.mouseOver;
    }

    public Vector getPos() {
        return this.pos;
    }

    public Scene getScene() {
        return this.scene;
    }

}
