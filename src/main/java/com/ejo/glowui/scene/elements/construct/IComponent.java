package com.ejo.glowui.scene.elements.construct;

import com.ejo.glowui.scene.Scene;
import org.util.glowlib.math.Vector;

public interface IComponent {

    Vector setPos(Vector pos);

    Vector getPos();

    boolean updateMouseOver(Vector mousePos);

    boolean isMouseOver();

    Scene getScene();
}
