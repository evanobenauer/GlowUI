package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowui.scene.elements.construct.IDrawable;
import com.ejo.glowui.scene.elements.construct.IComponent;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

public interface IShape extends IComponent, IDrawable {

    Vector setCenter(Vector pos);

    void setColor(ColorE color);

    Vector getCenter();

    ColorE getColor();

}
