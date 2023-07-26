package com.ejo.glowui.scene;

import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.scene.elements.ElementUI;
import com.ejo.glowui.Window;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowui.util.QuickDraw;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * The screen is the whiteboard of GlowUI. It is the canvas to which elements are drawn on and interacted with. The window can contain one screen at a time. Screens can be swapped
 * and set containing a different arrangement of components
 */
public abstract class Scene {

    private final ArrayList<ElementUI> elementList = new ArrayList<>();

    private final ArrayList<ElementUI> addElementList = new ArrayList<>();
    private final ArrayList<ElementUI> deleteElementList = new ArrayList<>();

    private Window window;
    private String title;

    public Scene(String title) {
        this.title = title;
        setWindow(null);
    }


    /**
     * This is the main draw method for the scene. It will draw all elements that are added to the scene. To draw more than the elements provided, you
     * must call the super of this method inside your override to continue to support the added elements. This method is called in the draw thread of the window
     */
    public void draw() {
        try {
            for (ElementUI element : getElements()) {
                element.draw(this, getWindow().getScaledMousePos());
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the main tick method for the scene. It will tick all elements that are added to the scene. To tick more than the elements provided, you
     * must call the super of this method inside your override to continue to support the added elements. This method is called in the tick thread of the window
     */
    public void tick() {
        try {
            for (ElementUI element : getElements()) {
                element.tick(this, getWindow().getScaledMousePos());
            }
            addElements(getAddElementList().toArray(new ElementUI[0])); //Adds all queued elements to the list after all ticks
            removeElements(getRemoveElementList().toArray(new ElementUI[0])); //Removes all queued elements to the list after all ticks
            getAddElementList().clear();
            getRemoveElementList().clear();
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is a user-input detection method. It will detect all key presses for added elements, returning the key, scancode, action, and modifiers for the key.
     * To scan for more input that the elements provided, you must call the super of this method inside your override to continue to support the added elements.
     * This method is called in the tick thread of the window.
     * @param key
     * @param scancode
     * @param action
     * @param mods
     */
    public void onKeyPress(int key, int scancode, int action, int mods) {
        try {
            for (ElementUI element : getElements()) {
                if (!element.shouldTick()) continue;
                element.onKeyPress(this, key, scancode, action, mods);
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is a user-input detection method. It will detect all mouse clicks for added elements, returning the button, action, modifiers, and mousePos for the click.
     * To scan for more input that the elements provided, you must call the super of this method inside your override to continue to support the added elements.
     * This method is called in the tick thread of the window.
     * @param button
     * @param action
     * @param mods
     * @param mousePos
     */
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        try {
            for (ElementUI element : getElements()) {
                if (!element.shouldTick()) continue;
                element.onMouseClick(this, button, action, mods, mousePos);
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    public void onMouseScroll(double scrollX, double scrollY, Vector mousePos) {
        try {
            for (ElementUI element : getElements()) {
                if (!element.shouldTick()) continue;
                element.onMouseScroll(this, scrollX, scrollY, mousePos);
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }


    public void drawBackground(ColorE color) {
        QuickDraw.drawRect(Vector.NULL,getSize(),color);
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

    public void queueAddElements(ElementUI... elements) {
        for (ElementUI element : elements) {
            getAddElementList().add(element);
        }
    }

    private void removeElements(ElementUI... elements) {
        for (ElementUI element : elements) {
            getElements().remove(element);
        }
    }

    private void queueRemoveElements(ElementUI... elements) {
        for (ElementUI element : elements) {
            getRemoveElementList().add(element);
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

    private ArrayList<ElementUI> getAddElementList() {
        return addElementList;
    }

    private ArrayList<ElementUI> getRemoveElementList() {
        return deleteElementList;
    }

}