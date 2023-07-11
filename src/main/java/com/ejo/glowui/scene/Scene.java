package com.ejo.glowui.scene;

import com.ejo.glowui.scene.elements.ElementUI;
import com.ejo.glowui.Window;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowui.scene.elements.construct.IDrawable;
import com.ejo.glowui.scene.elements.construct.IInput;
import com.ejo.glowui.scene.elements.construct.ITick;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * The screen is the whiteboard of GlowUI. It is the canvas to which elements are drawn on and interacted with. The window can contain one screen at a time. Screens can be swapped
 * and set containing a different arrangement of components
 */
public abstract class Scene implements IDrawable, IInput, ITick {

    //TODO: Create special abstract screen types
    // Create bordered screen type that is its own screen that can in case a screen in a border. The border will have action buttons and the inner screen can be swapped
    // Create a dual screen side-by-side type

    private final ArrayList<ElementUI> elementList = new ArrayList<>();

    private Window window;
    private String title;

    public Scene(String title) {
        this.title = title;
        setWindow(null);
    }


    @Override
    public void draw(Scene scene, Vector mousePos) {
        try {
            for (ElementUI element : getElements()) {
                element.draw(this, mousePos);
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick(Scene scene, Vector mousePos) {
        try {
            for (ElementUI element : getElements()) {
                element.tick(this, mousePos);
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onKeyPress(Scene scene, int key, int scancode, int action, int mods) {
        try {
            for (ElementUI element : getElements()) {
                element.onKeyPress(this, key, scancode, action, mods);
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos) {
        try {
            for (ElementUI element : getElements()) {
                element.onMouseClick(this, button, action, mods, mousePos);
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return false;
    }

    @Override
    public boolean isMouseOver() {
        return true;
    }

    @Override
    public void setTicking(boolean shouldTick) {
    }

    @Override
    public boolean shouldTick() {
        return false;
    }

    @Override
    public void setRendered(boolean shouldRender) {

    }

    @Override
    public boolean shouldRender() {
        return true;
    }


    public void setWindow(Window window) {
        this.window = window;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addElements(ElementUI... elements) {
        for (ElementUI element : elements) {
            if (!getElements().contains(element)) getElements().add(element);
        }
    }

    public Vector getSize() {
        return getWindow().getScaledSize();
    }

    public Window getWindow() {
        return window;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<ElementUI> getElements() {
        return elementList;
    }

}