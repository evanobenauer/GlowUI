package com.ejo.glowui.scene.elements.dimensional;

import com.ejo.glowlib.math.Angle;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import com.ejo.glowui.scene.elements.shape.CircleUI;
import com.ejo.glowui.scene.elements.shape.LineUI;

/**
 * This class is an attempt to try and make a 3D object using the UI system
 */
public class Polygon3D extends ElementUI {

    protected final Vector[] originalVertices;
    private Vector[] vertices;

    private Vector size;
    private Angle theta;
    private Angle phi;

    public Polygon3D(Vector pos, Vector size, Angle theta, Angle phi, Vector[] vertices) {
        super(pos, true, true);
        this.size = size;
        this.theta = theta;
        this.phi = phi;
        this.vertices = vertices;
        this.originalVertices = vertices;
        updateRotation();
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        updateRotation();
        //Draw Vertices
        for (int i = 0; i < getVertices().length; i++) {
            Vector vertex = getVertices()[i];
            CircleUI circle = new CircleUI(getPos().getAdded(vertex),ColorE.BLUE,Math.min(10,vertex.getZ() / 10), CircleUI.Type.MEDIUM);
            circle.draw();
        }
    }

    @Override
    protected void tickElement(Scene scene, Vector mousePos) {
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return false;
    }


    public void updateRotation() {
        Vector[] vertices = originalVertices.clone();

        //Theta
        for (int i = 0; i < vertices.length; i++) {
            Vector vertex = vertices[i];
            Vector relativeCenter = getCenter().getSubtracted(getPos());
            Vector relativeVertex = vertex.getSubtracted(relativeCenter);
            double xyRad = new Vector(relativeVertex.getX(),relativeVertex.getY()).getMagnitude();

            Angle theta = new Angle(Math.atan2(relativeVertex.getY(),relativeVertex.getX()) + getTheta().getRadians());

            double radialX = theta.getDirectionVector().getX() * xyRad;
            double radialY = theta.getDirectionVector().getY() * xyRad;

            vertices[i] = new Vector(radialX, radialY, vertex.getZ() - relativeCenter.getZ()).getAdded(relativeCenter);
        }

        //Phi
        for (int i = 0; i < vertices.length; i++) {
            Vector vertex = vertices[i];
            Vector relativeCenter = getCenter().getSubtracted(getPos());
            Vector relativeVertex = vertex.getSubtracted(relativeCenter);
            double xzRad = new Vector(relativeVertex.getX(),relativeVertex.getZ()).getMagnitude();

            Angle phi = new Angle(Math.atan2(relativeVertex.getZ(),relativeVertex.getX()) + getPhi().getRadians());

            double radialX = phi.getDirectionVector().getX() * xzRad;
            double radialZ = phi.getDirectionVector().getY() * xzRad;

            vertices[i] = new Vector(radialX, vertex.getY() - relativeCenter.getY(), radialZ).getAdded(relativeCenter);
        }

        setVertices(vertices);
    }


    public Vector getCenter() {
        return getPos().getAdded(getSize().getMultiplied(.5f));
    }


    public Vector getSize() {
        return size;
    }

    public Angle getTheta() {
        return theta;
    }

    public Angle getPhi() {
        return phi;
    }

    public Vector[] getVertices() {
        return vertices;
    }


    public void setSize(Vector size) {
        this.size = size;
    }

    public void setTheta(Angle theta) {
        this.theta = theta;
    }

    public void setPhi(Angle phi) {
        this.phi = phi;
    }

    public void setVertices(Vector[] vertices) {
        this.vertices = vertices;
    }
}