package com.ejo.glowui.scene.elements.prism;

import com.ejo.glowlib.math.Angle;
import com.ejo.glowlib.math.MathE;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.math.VectorMod;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import com.ejo.glowui.scene.elements.shape.CircleUI;
import com.ejo.glowui.util.render.Fonts;
import com.ejo.glowui.util.render.QuickDraw;

/**
 * This class is an attempt to try and make a 3D object using the UI system
 */
public class Polygon3D extends ElementUI {

    protected final Vector[] originalVertices;
    private final Vector[] scaledVertices;
    private Vector[] vertices;

    private boolean cameraScaled;
    private double cameraScale;
    private Vector cameraPos;

    private Vector size;
    private Angle theta;
    private Angle phi;

    public Polygon3D(Vector pos, Vector size, Angle theta, Angle phi, boolean cameraScaled, double cameraZ, double cameraScale, Vector[] vertices) {
        super(pos, true, true);
        this.size = size;
        this.theta = theta;
        this.phi = phi;
        this.cameraScaled = cameraScaled;
        this.cameraScale = cameraScale;
        this.cameraPos = new Vector(getCenter().getX(), getCenter().getY(), cameraZ);
        this.vertices = vertices;
        this.originalVertices = vertices.clone();
        this.scaledVertices = vertices.clone();
        updateRotation();
    }

    public Polygon3D(Vector pos, Vector size, Angle theta, Angle phi, Vector[] vertices) {
        this(pos, size, theta, phi, false, 0, 0, vertices);
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        updateCameraPos();
        updateRotation();
        updateScaledVertices();
        //Draw Vertices
        for (int i = 0; i < getScaledVertices().length; i++) {
            Vector scaledVertex = getScaledVertices()[i];
            Vector vertex = getVertices()[i];
            Vector cameraDistance = getCameraPos().getSubtracted(vertex.getAdded(getPos()));
            double distanceScale = !isCameraScaled() ? 2 : getCameraScale() / cameraDistance.getZ();

            if (scene.getWindow().isDebug())
                QuickDraw.drawText(String.valueOf(MathE.roundDouble(scaledVertex.getAdded(getPos()).getZ(), 1)), Fonts.getDefaultFont(12), scaledVertex.getAdded(getPos()), ColorE.WHITE);

            CircleUI circle = new CircleUI(scaledVertex.getAdded(getPos()), ColorE.BLUE, 3 * distanceScale, CircleUI.Type.MEDIUM);
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


    public void updateCameraPos() {
        setCameraPos(new Vector(getCenter().getX(), getCenter().getY(), getCameraPos().getZ()));
    }

    public void updateRotation() {
        Vector[] vertices = originalVertices.clone();

        //Theta
        for (int i = 0; i < vertices.length; i++) {
            Vector vertex = vertices[i];
            Vector relativeCenter = getCenter().getSubtracted(getPos());
            Vector relativeVertex = vertex.getSubtracted(relativeCenter);
            double xyRad = new Vector(relativeVertex.getX(), relativeVertex.getY()).getMagnitude();

            Angle theta = new Angle(Math.atan2(relativeVertex.getY(), relativeVertex.getX()) + getTheta().getRadians());

            double radialX = theta.getUnitVector().getX() * xyRad;
            double radialY = theta.getUnitVector().getY() * xyRad;

            vertices[i] = new Vector(radialX, radialY, vertex.getZ() - relativeCenter.getZ()).getAdded(relativeCenter);
        }

        //Phi
        for (int i = 0; i < vertices.length; i++) {
            Vector vertex = vertices[i];
            Vector relativeCenter = getCenter().getSubtracted(getPos());
            Vector relativeVertex = vertex.getSubtracted(relativeCenter);
            double xzRad = new Vector(relativeVertex.getX(), relativeVertex.getZ()).getMagnitude();

            Angle phi = new Angle(Math.atan2(relativeVertex.getZ(), relativeVertex.getX()) + getPhi().getRadians());

            double radialX = phi.getUnitVector().getX() * xzRad;
            double radialZ = phi.getUnitVector().getY() * xzRad;

            vertices[i] = new Vector(radialX, vertex.getY() - relativeCenter.getY(), radialZ).getAdded(relativeCenter);
        }

        setVertices(vertices);
    }

    private void updateScaledVertices() {
        for (int i = 0; i < getVertices().length; i++) {
            Vector vertex = getVertices()[i];
            Vector cameraDistance = getCameraPos().getSubtracted(vertex.getAdded(getPos()));
            double distanceScale = getCameraScale() / cameraDistance.getZ();

            //Scales X and Y around the camera's X and Y based off of the Z distance
            VectorMod translation = vertex.getAdded(getPos()).getMod();
            translation.subtract(getCameraPos().getSubtracted(0, 0, getCameraPos().getZ()));
            if (isCameraScaled()) translation.scale(distanceScale, distanceScale);
            translation.add(getCameraPos().getSubtracted(0, 0, getCameraPos().getZ()));

            scaledVertices[i] = translation.subtract(getPos());
        }
    }


    public Polygon3D setCameraScaled(boolean cameraScaled) {
        this.cameraScaled = cameraScaled;
        return this;
    }

    public Polygon3D setCameraScale(double cameraScale) {
        this.cameraScale = cameraScale;
        return this;
    }

    private Polygon3D setCameraPos(Vector cameraPos) {
        this.cameraPos = cameraPos;
        return this;
    }

    public Polygon3D setCameraZ(double cameraZ) {
        setCameraPos(new Vector(getCenter().getX(), getCenter().getZ(), cameraZ));
        return this;
    }

    public Polygon3D setCenter(Vector pos) {
        setPos(getPos().getAdded(pos.getSubtracted(getCenter())));
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


    public boolean isCameraScaled() {
        return cameraScaled;
    }

    public double getCameraScale() {
        return cameraScale;
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

    public Vector[] getScaledVertices() {
        return scaledVertices;
    }
}