package com.ejo.glowui.scene.elements.construct;

import com.ejo.glowlib.math.Vector;

public interface IComponent {

    IComponent setPos(Vector pos);

    Vector getPos();

    boolean updateMouseOver(Vector mousePos);

    boolean isMouseOver();

}
