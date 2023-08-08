package com.ejo.glowui.scene.elements;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.construct.IDrawable;
import com.ejo.glowui.scene.elements.construct.IInput;
import com.ejo.glowui.scene.elements.construct.IComponent;
import com.ejo.glowui.scene.elements.construct.ITick;
import com.ejo.glowlib.math.Vector;

//TODO: Make all elements stop rendering when off the scene
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
     * The draw method should be called in the draw method of the scene to which it belongs. This method operates in the render thread
     * @param scene
     * @param mousePos
     */
    @Override
    public void draw(Scene scene, Vector mousePos) {
        if (!shouldRender()) return;
        drawElement(scene,mousePos);
    }

    public void draw(Scene scene) {
        draw(scene,scene.getWindow().getScaledMousePos());
    }

    public void draw() {
        draw(null,new Vector(-1,-1));
    }

    /**
     * This is the method that the element will have its contents drawn in. This method is called whenever draw() is called
     * Make sure when drawing elements, do NOT override draw(), and use this method instead
     * @param scene
     * @param mousePos
     */
    protected abstract void drawElement(Scene scene, Vector mousePos);


    /**
     * The tick method should be called in the tick method of the scene to which it belongs. This method operates in the tick thread
     * @param scene
     * @param mousePos
     */
    @Override
    public void tick(Scene scene, Vector mousePos) {
        if (!shouldTick()) return;
        mouseOver = updateMouseOver(mousePos);
        tickElement(scene,mousePos);
    }

    public void tick(Scene scene) {
        tick(scene,scene.getWindow().getScaledMousePos());
    }

    public void tick() {
        tick(null,new Vector(-1,-1));
    }

    /**
     * This is the method that the element will have its contents ticked in. This method is called whenever tick() is called
     * Make sure when ticking elements, do NOT override tick(), and use this method instead
     * @param scene
     * @param mousePos
     */
    protected abstract void tickElement(Scene scene, Vector mousePos);

    @Override
    public void onKeyPress(Scene scene, int key, int scancode, int action, int mods) {
    }

    @Override
    public void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos) {
    }

    @Override
    public void onMouseScroll(Scene scene, int scroll, Vector mousePos) {
    }


    /**
     * Stops the element from ticking and un-renders the element. This is useful for "removing" elements with an interaction from another element to avoid
     * a concurrent modification exception
     * @param disabled
     */
    @Deprecated
    public void disable(boolean disabled) {
        setRendered(!disabled);
        setTicking(!disabled);
    }

    /**
     * This method can set the element to be enabled or disabled. Disabling an element is useful for "removing" elements with an interaction
     * from another elements while avoiding a concurrent modification exception
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        setRendered(enabled);
        setTicking(enabled);
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
