package com.ejo.glowui.scene;

import com.ejo.glowlib.math.MathE;
import com.ejo.glowlib.math.VectorMod;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.scene.elements.ElementUI;
import com.ejo.glowui.Window;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowui.scene.elements.TextUI;
import com.ejo.glowui.util.render.Fonts;
import com.ejo.glowui.util.render.QuickDraw;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * The screen is the whiteboard of GlowUI. It is the canvas to which elements are drawn on and interacted with. The window can contain one screen at a time. Screens can be swapped
 * and set containing a different arrangement of components
 */
public abstract class Scene {

    private final ArrayList<ElementUI> elementList = new ArrayList<>();
    private ArrayList<ElementUI> drawElementsList = new ArrayList<>();

    private final ArrayList<ElementUI> addElementQueue = new ArrayList<>();
    private final ArrayList<ElementUI> deleteElementQueue = new ArrayList<>();

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
            drawElementsList = new ArrayList<>(getElements()); //Stops queued elements from causing a concurrent modification exception
        } catch (ConcurrentModificationException ignored) {
        }
        try {
            for (ElementUI element : drawElementsList) {
                element.draw(this, getWindow().getScaledMousePos());
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
        drawDebugText();
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
            if (getAddElementQueue().isEmpty() && getRemoveElementQueue().isEmpty()) return;
            removeElements(getRemoveElementQueue().toArray(new ElementUI[0])); //Removes all queued elements to the list after all ticks
            addElements(getAddElementQueue().toArray(new ElementUI[0])); //Adds all queued elements to the list after all ticks
            getRemoveElementQueue().clear();
            getAddElementQueue().clear();
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

    public void onMouseScroll(int scroll, Vector mousePos) {
        try {
            for (ElementUI element : getElements()) {
                if (!element.shouldTick()) continue;
                element.onMouseScroll(this, scroll, mousePos);
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }


    public void drawBackground(ColorE color) {
        QuickDraw.drawRect(Vector.NULL,getSize(),color);
    }

    private void drawDebugText() {
        if (!getWindow().isDebug()) return;
        QuickDraw.drawFPSTPS(this,new Vector(2,2),15,true,true);
        VectorMod pos = new Vector(2,37).getMod();

        //INFO
        int sigFigs = 2;
        QuickDraw.drawText("UI Scale: " + (int)MathE.roundDouble(getWindow().getUIScale() * 100,0) + "%", Fonts.getDefaultFont(15),pos,ColorE.WHITE);
        pos.add(new Vector(0,17));
        QuickDraw.drawText("Size: " + MathE.roundDouble(getWindow().getScaledSize().getX(),sigFigs) + "(" + MathE.roundDouble(getWindow().getSize().getX(),sigFigs) + ")" + ", " + MathE.roundDouble(getWindow().getScaledSize().getY(),sigFigs) + "(" + MathE.roundDouble(getWindow().getSize().getY(),sigFigs) + ") ",Fonts.getDefaultFont(15),pos,ColorE.WHITE);
        pos.add(new Vector(0,17));
        QuickDraw.drawText("MousePos: " + MathE.roundDouble(getWindow().getScaledMousePos().getX(),sigFigs) + "(" + MathE.roundDouble(getWindow().getMousePos().getX(),sigFigs) + ")" + ", " + MathE.roundDouble(getWindow().getScaledMousePos().getY(),sigFigs) + "(" + MathE.roundDouble(getWindow().getMousePos().getY(),sigFigs) + ") ",Fonts.getDefaultFont(15),pos,ColorE.WHITE);

        //KEYBINDINGS
        int y = 2;
        TextUI text0 = new TextUI("+/-: Scale++/Scale--",Fonts.getDefaultFont(15),Vector.NULL,ColorE.WHITE);
        text0.setPos(new Vector(getWindow().getScaledSize().getX() - text0.getWidth() - 6,y));
        text0.draw();
        y += 17;
        TextUI text1 = new TextUI("Shift +/-: TPS++/TPS--",Fonts.getDefaultFont(15),Vector.NULL,ColorE.WHITE);
        text1.setPos(new Vector(getWindow().getScaledSize().getX() - text1.getWidth() - 6,y));
        text1.draw();
        y += 17;
        TextUI text2 = new TextUI("CTRL +/-: FPS++/FPS--",Fonts.getDefaultFont(15),Vector.NULL,ColorE.WHITE);
        text2.setPos(new Vector(getWindow().getScaledSize().getX() - text2.getWidth() - 6,y));
        text2.draw();
        y += 17;
        TextUI text3 = new TextUI("ALT E: Economy",Fonts.getDefaultFont(15),Vector.NULL,ColorE.WHITE);
        text3.setPos(new Vector(getWindow().getScaledSize().getX() - text3.getWidth() - 8,y));
        text3.draw();
        y += 17;
        TextUI text4 = new TextUI("ALT V: V-Sync",Fonts.getDefaultFont(15),Vector.NULL,ColorE.WHITE);
        text4.setPos(new Vector(getWindow().getScaledSize().getX() - text4.getWidth() - 10,y));
        text4.draw();
        y += 17;
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
            getAddElementQueue().add(element);
        }
    }

    public void removeElements(ElementUI... elements) {
        for (ElementUI element : elements) {
            getElements().remove(element);
        }
    }

    public void queueRemoveElements(ElementUI... elements) {
        for (ElementUI element : elements) {
            getRemoveElementQueue().add(element);
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

    private ArrayList<ElementUI> getAddElementQueue() {
        return addElementQueue;
    }

    private ArrayList<ElementUI> getRemoveElementQueue() {
        return deleteElementQueue;
    }

    public ArrayList<ElementUI> getElements() {
        return elementList;
    }

}