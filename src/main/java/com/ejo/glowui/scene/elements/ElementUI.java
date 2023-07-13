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

    @Override
    public void draw(Scene scene, Vector mousePos) {
        if (!shouldRender()) return;
        drawElement(scene,mousePos);
    }

    public void quickDraw(Scene scene) {
        draw(scene,scene.getWindow().getScaledMousePos());
    }

    public void quickDraw() {
        draw(null,new Vector(-1,-1));
    }

    /**
     * This is the method that the element will have its contents drawn in. This method is called whenever draw() is called
     * @param scene
     * @param mousePos
     */
    protected abstract void drawElement(Scene scene, Vector mousePos);

    /**
     * Make sure that for every implementation of the tick method in different elements, you supersede the code with the super
     */
    @Override
    public void tick(Scene scene, Vector mousePos) {
        if (!shouldTick()) return;
        mouseOver = updateMouseOver(mousePos);
        tickElement(scene,mousePos);
    }

    public void quickTick(Scene scene) {
        tick(scene,scene.getWindow().getScaledMousePos());
    }

    public void quickTick() {
        tick(null,new Vector(-1,-1));
    }

    /**
     * This is the method that the element will have its contents ticked in. This method is called whenever tick() is called
     * @param scene
     * @param mousePos
     */
    protected abstract void tickElement(Scene scene, Vector mousePos);

    /**
     * Make sure that for every implementation of the tick method in different elements, you supersede the code with the super
     */
    @Override
    public abstract void onKeyPress(Scene scene, int key, int scancode, int action, int mods);

    /**
     * Make sure that for every implementation of the tick method in different elements, you supersede the code with the super
     */
    @Override
    public abstract void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos);


    /**
     * Stops the element from ticking and un-renders the element. This is useful for "removing" elements with an interaction from another element to avoid
     * a concurrent modification exception
     * @param disabled
     */
    public void disable(boolean disabled) {
        setRendered(!disabled);
        setTicking(!disabled);
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
