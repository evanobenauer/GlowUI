package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowui.scene.Scene;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

public class TexturedRectUI extends RectangleUI {

    public TexturedRectUI(Scene scene, Vector pos, Vector size, ColorE color) {
        super(scene, pos, size, color);
    }

    @Override
    public void draw() {
        super.draw();
        //Add texture code here
    }

}
