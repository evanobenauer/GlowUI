package com.ejo.glowui.screen.elements.interactable;

import com.ejo.glowui.screen.Screen;
import org.util.glowlib.math.Vector;

public class ModeCycleUI extends InteractableUI {

    public ModeCycleUI(Screen screen, Vector pos, boolean shouldRender, Runnable action) {
        super(screen, pos, shouldRender, action);
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
