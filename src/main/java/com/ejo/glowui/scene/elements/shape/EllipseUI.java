package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowui.scene.Scene;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

public class EllipseUI extends ShapeUI {

    public EllipseUI(Scene scene, Vector pos, Vector size, ColorE color) {
        super(scene,pos,color,true);
    }

    @Override
    public void draw() {
        //Make Ellipse draw code here
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        //TODO FINISH THIS
        return false;
    }

    @Override
    public Vector setCenter(Vector pos) {
        return null;
    }

    @Override
    public Vector getCenter() {
        return null;
    }
}
