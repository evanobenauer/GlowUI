package com.ejo.glowui.scene.elements.interactable;

import com.ejo.glowui.scene.Scene;
import org.util.glowlib.math.Vector;

//TODO: INCOMPLETE PLACEHOLDER
public class ModeCycleUI extends InteractableUI {

    public ModeCycleUI(Scene scene, Vector pos, boolean shouldRender, Runnable action) {
        super(scene, pos, shouldRender, action);
    }

    @Override
    public void draw() {

    }

    @Override
    public void onKeyPress(int key, int scancode, int action, int mods) {

    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {

    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return false;
    }
}
