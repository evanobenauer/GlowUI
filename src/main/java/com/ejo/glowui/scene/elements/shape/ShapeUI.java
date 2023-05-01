package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

public abstract class ShapeUI extends ElementUI {

    private ColorE color;

    public ShapeUI(Scene scene, Vector pos, ColorE color, boolean shouldRender) {
        super(scene,pos,shouldRender);
        this.color = color;
    }

    public abstract Vector setCenter(Vector pos);

    public void setColor(ColorE color) {
        this.color = color;
    }


    public abstract Vector getCenter();

    public ColorE getColor() {
        return color;
    }

}
