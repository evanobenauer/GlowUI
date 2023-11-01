package com.ejo.glowui.scene.elements.construct;

import com.ejo.glowlib.math.Vector;
import com.ejo.glowui.scene.Scene;

public interface IDrawable {

    void draw(Scene scene, Vector mousePos);

    IDrawable setRendered(boolean shouldRender);

    boolean shouldRender();

}
