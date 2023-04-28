package com.ejo.glowui.screen.elements.shape;

import com.ejo.glowui.screen.Screen;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

public class PolygonUI extends ShapeUI {

    Vector[] vertices;

    public PolygonUI(Screen screen, Vector pos, ColorE color, Vector... vertices) {
        super(screen,pos,color,true);
        this.vertices = vertices;
    }

    @Override
    public void draw() {
        //TODO: Create a polygon
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        //TODO find how to do this lol
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
