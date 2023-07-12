package com.ejo.glowui.scene.elements;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.construct.IDrawable;
import com.ejo.glowui.scene.elements.construct.IInput;
import com.ejo.glowui.scene.elements.construct.IComponent;
import com.ejo.glowui.scene.elements.construct.ITick;
import com.ejo.glowlib.math.Vector;

public abstract class ElementUI implements IComponent, IDrawable, ITick, IInput {

    private Vector pos;

    private boolean rendered;
    private boolean ticking;
    protected boolean mouseOver;

    public ElementUI(Vector pos, boolean shouldRender, boolean shouldTick) {
        this.pos = pos;
        this.rendered = shouldRender;
        this.ticking = shouldTick;
        this.mouseOver = false;
    }

    /**
     * The draw method will draw the attributes of the UI element. In every extension of the element, make sure the super comes first
     */
    @Override
    public void draw(Scene scene, Vector mousePos) {
        if (!shouldRender()) return;
    }

    public void quickDraw(Scene scene) {
        draw(scene,scene.getWindow().getScaledMousePos());
    }

    public void quickDraw() {
        draw(null,new Vector(-1,-1));
    }

    /**
     * Make sure that for every implementation of the tick method in different elements, you supersede the code with the super
     */
    @Override
    public void tick(Scene scene, Vector mousePos) {
        if (!shouldTick()) return;
        mouseOver = updateMouseOver(mousePos);
    }

    public void quickTick(Scene scene) {
        tick(scene,scene.getWindow().getScaledMousePos());
    }

    public void quickTick() {
        draw(null,new Vector(-1,-1));
    }

    /**
     * Make sure that for every implementation of the tick method in different elements, you supersede the code with the super
     */
    @Override
    public void onKeyPress(Scene scene, int key, int scancode, int action, int mods) {
        if (!shouldTick()) return;
    }

    /**
     * Make sure that for every implementation of the tick method in different elements, you supersede the code with the super
     */
    @Override
    public void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos) {
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

    public abstract boolean updateMouseOver(Vector mousePos);

    public void setRendered(boolean shouldRender) {
        this.rendered = shouldRender;
    }

    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }

    public Vector setPos(Vector pos) {
        return this.pos = pos;
    }


    public boolean isMouseOver() {
        return this.mouseOver;
    }

    public boolean shouldRender() {
        return this.rendered;
    }

    public boolean shouldTick() {
        return this.ticking;
    }

    public Vector getPos() {
        return this.pos;
    }

}
