package com.ejo.glowui.scene.elements.construct;

import com.ejo.glowlib.math.Vector;
import com.ejo.glowui.scene.Scene;

public interface IInput {

    void onKeyPress(Scene scene, int key, int scancode, int action, int mods);

    void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos);

    boolean updateMouseOver(Vector mousePos);

    boolean isMouseOver();

}
