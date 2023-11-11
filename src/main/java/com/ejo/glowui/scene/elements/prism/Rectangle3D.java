package com.ejo.glowui.scene.elements.prism;

import com.ejo.glowlib.math.Angle;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.LineUI;
import com.ejo.glowui.scene.elements.shape.PolygonUI;

/**
 * This class is an attempt to try and make a 3D object using the UI system
 */
public class Rectangle3D extends Polygon3D {

    public Rectangle3D(Vector pos, Vector size, Angle theta, Angle phi, boolean cameraScaled, double cameraZ, double cameraScale) {
        super(pos,size,theta,phi, cameraScaled, cameraZ,cameraScale,new Vector[]{
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

    public Rectangle3D(Vector pos, Vector size, Angle theta, Angle phi) {
        this(pos,size,theta,phi,false,0,0);
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        super.drawElement(scene,mousePos);
        //new PolygonUI(getPos(),ColorE.GRAY,getScaledVertices()[0],getScaledVertices()[1],getScaledVertices()[2],getScaledVertices()[3]).draw(scene,mousePos);
        //new PolygonUI(getPos(),ColorE.WHITE,getScaledVertices()[4],getScaledVertices()[5],getScaledVertices()[6],getScaledVertices()[7]).draw(scene,mousePos);
        //new PolygonUI(getPos(),ColorE.GREEN,getScaledVertices()[0],getScaledVertices()[3],getScaledVertices()[7],getScaledVertices()[4]).draw(scene,mousePos);
        //new PolygonUI(getPos(),ColorE.RED,getScaledVertices()[1],getScaledVertices()[5],getScaledVertices()[6],getScaledVertices()[2]).draw(scene,mousePos);

        //Draw Scaled Lines
        for (int i = 0; i < 8; i++) {
            new LineUI(getPos().getAdded(getScaledVertices()[i]), getPos().getAdded(getScaledVertices()[i == 3 ? 0 : i == 7 ? 4 : i + 1]), ColorE.BLUE, LineUI.Type.PLAIN, 1).draw();
            if (i < 4) new LineUI(getPos().getAdded(getScaledVertices()[i]), getPos().getAdded(getScaledVertices()[i + 4]), ColorE.BLUE, LineUI.Type.PLAIN, 1).draw();
        }
    }
}