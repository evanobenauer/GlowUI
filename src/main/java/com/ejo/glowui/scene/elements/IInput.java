package com.ejo.glowui.scene.elements;

import org.util.glowlib.math.Vector;

public interface IInput {
    void onKeyPress(int key, int scancode, int action, int mods);
    void onMouseClick(int button, int action, int mods, Vector mousePos);

}
