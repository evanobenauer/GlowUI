package com.ejo.glowui.screen.elements.shape;

import com.ejo.glowui.screen.Screen;
import com.ejo.glowui.screen.elements.IElementUI;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

/**
 * The shape class is basically complete, don't touch
 */
public abstract class ShapeUI implements IElementUI {

    private final Screen screen;
    private Vector pos;
    private ColorE color;

    private boolean shouldRender;
    protected boolean mouseOver;

    public ShapeUI(Screen screen, Vector pos, ColorE color, boolean shouldRender) {
        this.screen = screen;
        this.pos = pos;
        this.color = color;
        this.shouldRender = shouldRender;
        this.mouseOver = false;
    }

    @Override
    public void tick() {
        updateMouseOver(getScreen().getWindow().getMousePos());
    }

    @Override
    public void onKeyPress(int key, int scancode, int action, int mods) {
        //NULL
    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        //NULL
    }

    @Override
    public void setRendered(boolean shouldRender) {
        this.shouldRender = shouldRender;
    }

    @Override
    public Vector setPos(Vector pos) {
        return this.pos = pos;
    }

    public abstract Vector setCenter(Vector pos);

    public void setColor(ColorE color) {
        this.color = color;
    }


    @Override
    public boolean shouldRender() {
        return this.shouldRender;
    }

    @Override
    public Vector getPos() {
        return this.pos;
    }

    public abstract Vector getCenter();

    public ColorE getColor() {
        return color;
    }

    @Override
    public boolean isMouseOver() {
        return this.mouseOver;
    }

    @Override
    public Screen getScreen() {
        return this.screen;
    }

}
