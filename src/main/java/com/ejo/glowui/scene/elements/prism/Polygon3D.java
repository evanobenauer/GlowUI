package com.ejo.glowui.scene.elements.prism;

import com.ejo.glowlib.math.Angle;
import com.ejo.glowlib.math.MathE;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.util.NumberUtil;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import com.ejo.glowui.scene.elements.shape.CircleUI;
import com.ejo.glowui.scene.elements.shape.PolygonUI;
import com.ejo.glowui.util.render.Fonts;
import com.ejo.glowui.util.render.GLManager;
import com.ejo.glowui.util.render.QuickDraw;

/**
 * This class is an attempt to try and make a 3D object using the UI system
 */
public class Polygon3D extends ElementUI {

    protected final Vector[] originalVertices;
    private Vector[] vertices;

    private final double cameraZ;
    private Vector cameraPos;

    private Vector size;
    private Angle theta;
    private Angle phi;

    public Polygon3D(Vector pos, Vector size, double cameraZ, Angle theta, Angle phi,Vector[] vertices) {
        super(pos, true, true);
        this.size = size;
        this.theta = theta;
        this.phi = phi;
        this.cameraZ = cameraZ;
        this.vertices = vertices;
        this.originalVertices = vertices;
        updateRotation();
    }

    //TODO: Add camera distance to define scale and vertex size w/ cameraZ
    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        updateCameraPos(scene);
        updateRotation();
        //Draw Vertices
        for (int i = 0; i < getVertices().length; i++) {
            Vector vertex = getVertices()[i];
            if (scene.getWindow().isDebug()) QuickDraw.drawText(String.valueOf(MathE.roundDouble(vertex.getAdded(getPos()).getZ(), 1)), Fonts.getDefaultFont(12),vertex.getAdded(getPos()),ColorE.WHITE);
            Vector cameraDistance = getCameraPos().getSubtracted(vertex);
            CircleUI circle = new CircleUI(getPos().getAdded(vertex),ColorE.BLUE, NumberUtil.getBoundValue(1/cameraDistance.getZ() * 2000,0,10).doubleValue(), CircleUI.Type.MEDIUM);
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

            double radialX = theta.getUnitVector().getX() * xyRad;
            double radialY = theta.getUnitVector().getY() * xyRad;

            vertices[i] = new Vector(radialX, radialY, vertex.getZ() - relativeCenter.getZ()).getAdded(relativeCenter);
        }

        //Phi
        for (int i = 0; i < vertices.length; i++) {
            Vector vertex = vertices[i];
            Vector relativeCenter = getCenter().getSubtracted(getPos());
            Vector relativeVertex = vertex.getSubtracted(relativeCenter);
            double xzRad = new Vector(relativeVertex.getX(),relativeVertex.getZ()).getMagnitude();

            Angle phi = new Angle(Math.atan2(relativeVertex.getZ(),relativeVertex.getX()) + getPhi().getRadians());

            double radialX = phi.getUnitVector().getX() * xzRad;
            double radialZ = phi.getUnitVector().getY() * xzRad;

            vertices[i] = new Vector(radialX, vertex.getY() - relativeCenter.getY(), radialZ).getAdded(relativeCenter);
        }

        setVertices(vertices);
    }

    private void updateCameraPos(Scene scene) {
        this.cameraPos = new Vector(scene.getSize().getX(),scene.getSize().getY(),cameraZ);
    }

    public Polygon3D setCameraPos(Vector cameraPos) {
        this.cameraPos = cameraPos;
        return this;
    }

    public Polygon3D setCenter(Vector pos) {
        setPos(getPos().getSubtracted(getCenter()).getAdded(pos));
        return this;
    }

    public Polygon3D setSize(Vector size) {
        this.size = size;
        return this;
    }

    public Polygon3D setTheta(Angle theta) {
        this.theta = theta;
        return this;
    }

    public Polygon3D setPhi(Angle phi) {
        this.phi = phi;
        return this;
    }

    public Polygon3D setVertices(Vector[] vertices) {
        this.vertices = vertices;
        return this;
    }


    public Vector getCameraPos() {
        return cameraPos;
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

}