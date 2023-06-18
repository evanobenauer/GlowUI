package com.ejo.glowui.scene.elements.construct;

import com.ejo.glowlib.math.Vector;

public interface IInput {

    void onKeyPress(int key, int scancode, int action, int mods);

    void onMouseClick(int button, int action, int mods, Vector mousePos);

    boolean updateMouseOver(Vector mousePos);

    boolean isMouseOver();

}
