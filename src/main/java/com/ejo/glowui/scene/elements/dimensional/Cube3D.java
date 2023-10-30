package com.ejo.glowui.scene.elements.dimensional;

import com.ejo.glowlib.math.Angle;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.util.NumberUtil;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import com.ejo.glowui.scene.elements.shape.CircleUI;
import com.ejo.glowui.scene.elements.shape.LineUI;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.render.QuickDraw;

/**
 * This class is an attempt to try and make a 3D object using the UI system
 */
public class Cube3D extends Polygon3D {

    public Cube3D(Vector pos, Vector size, Angle theta, Angle phi) {
        super(pos,size,theta,phi, new Vector[]{
                Vector.NULL,
                new Vector(size.getX(), 0),
                new Vector(size.getX(), size.getY()),
                new Vector(0, size.getY()),

                new Vector(0, 0, size.getZ()),
                new Vector(size.getX(), 0, size.getZ()),
                size,
                new Vector(0, size.getY(), size.getZ())});
        updateRotation();
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        updateRotation();
        //Draw Lines
        for (int i = 0; i < 8; i++) {
            new LineUI(getPos().getAdded(getVertices()[i]), getPos().getAdded(getVertices()[i == 3 ? 0 : i == 7 ? 4 : i + 1]), ColorE.BLUE, LineUI.Type.PLAIN, 1).draw();
            if (i < 4) new LineUI(getPos().getAdded(getVertices()[i]), getPos().getAdded(getVertices()[i + 4]), ColorE.BLUE, LineUI.Type.PLAIN, 1).draw();
        }
        //Draw Vertices
        for (int i = 0; i < getVertices().length; i++) {
            Vector vertex = getVertices()[i];
            CircleUI circle = new CircleUI(getPos().getAdded(vertex),ColorE.BLUE,Math.min(10,vertex.getZ() / 10), CircleUI.Type.MEDIUM);
            circle.draw();
        }
    }
}