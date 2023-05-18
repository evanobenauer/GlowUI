package com.ejo.glowui.scene;

import com.ejo.glowui.scene.elements.ElementUI;
import com.ejo.glowui.Window;
import org.util.glowlib.math.Vector;

import java.util.ArrayList;

/**
 * The screen class is basically complete. Don't touch it
 */
public abstract class Scene {

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


    public void draw() {
        for (ElementUI element : getElements()) {
            element.draw();
        }
    }

    public void tick() {
        for (ElementUI element : getElements()) {
            element.tick();
        }
    }

    public void onKeyPress(int key, int scancode, int action, int mods) {
        for (ElementUI element : getElements()) {
            element.onKeyPress(key,scancode,action,mods);
        }
    }

    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        for (ElementUI element : getElements()) {
            element.onMouseClick(button,action,mods,mousePos);
        }
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
