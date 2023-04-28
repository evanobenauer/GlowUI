package com.ejo.glowui.screen.elements.interactable;

import com.ejo.glowui.screen.Screen;
import com.ejo.glowui.screen.elements.IElementUI;
import org.util.glowlib.math.Vector;

public abstract class InteractableUI implements IElementUI {

    private final Screen screen;
    private Vector pos;

    private boolean shouldRender;
    protected boolean mouseOver;

    private Runnable action;

    public InteractableUI(Screen screen, Vector pos, boolean shouldRender, Runnable action) {
        this.screen = screen;
        this.pos = pos;
        this.shouldRender = shouldRender;
        this.action = action;
    }

    @Override
    public void tick() {
        updateMouseOver(getScreen().getWindow().getMousePos());
    }


    public void setAction(Runnable action) {
        this.action = action;
    }

    @Override
    public void setRendered(boolean shouldRender) {
        this.shouldRender = shouldRender;
    }

    @Override
    public Vector setPos(Vector pos) {
        return this.pos = pos;
    }


    public Runnable getAction() {
        return action;
    }

    @Override
    public boolean shouldRender() {
        return this.shouldRender;
    }

    @Override
    public boolean isMouseOver() {
        return this.mouseOver;
    }

    @Override
    public Vector getPos() {
        return this.pos;
    }

    @Override
    public Screen getScreen() {
        return this.screen;
    }

}
