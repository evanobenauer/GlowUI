package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowui.scene.elements.construct.IDrawable;
import com.ejo.glowui.scene.elements.construct.IComponent;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

public interface IShape extends IComponent, IDrawable {

    Vector setCenter(Vector pos);

    Vector getCenter();

    void setColor(ColorE color);

    ColorE getColor();

}
