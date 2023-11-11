package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowlib.math.Angle;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.scene.Scene;

import java.util.ArrayList;

public class RegularPolygonUI extends PolygonUI implements IShape {

    private double radius;
    private int vertexCount;
    private Angle rotation;

    public RegularPolygonUI(Vector pos, ColorE color, boolean outlined, double radius, int vertexCount, Angle rotation) {
        super(pos,color,outlined);
        this.radius = radius;
        this.vertexCount = vertexCount;
        this.rotation = rotation;
        updateVertices();
    }

    public RegularPolygonUI(Vector pos, ColorE color, double radius, int vertexCount, Angle rotation) {
        this(pos,color,false,radius,vertexCount,rotation);
    }

    public RegularPolygonUI(Vector pos, ColorE color, boolean outlined, double radius, int vertexCount) {
        this(pos,color,outlined,radius,vertexCount,null);
    }

    public RegularPolygonUI(Vector pos, ColorE color, double radius, int vertexCount) {
        this(pos,color,false,radius,vertexCount,null);
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        updateVertices();
        super.drawElement(scene,mousePos);
    }

    private void updateVertices() {
        double rot = (getRotation() != null ? getRotation().getRadians() : 0);
        double radianIncrement = 2*Math.PI / getVertexCount();
        ArrayList<Vector> verticesList = new ArrayList<>();
        for (int i = 1; i <= getVertexCount(); i++) {
            Vector vert = new Vector(Math.cos(radianIncrement*i + rot),Math.sin(radianIncrement*i + rot)).getMultiplied(getRadius());
            verticesList.add(vert);
        }
        vertices = verticesList.toArray(new Vector[0]);
    }

    @Override
    public RegularPolygonUI setCenter(Vector pos) {
        return (RegularPolygonUI) setPos(pos);
    }

    public RegularPolygonUI setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    public RegularPolygonUI setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
        return this;
    }

    public RegularPolygonUI setRotation(Angle rotation) {
        this.rotation = rotation;
        return this;
    }


    @Override
    public Vector getCenter() {
        return getPos();
    }

    public double getRadius() {
        return radius;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public Angle getRotation() {
        return rotation;
    }

}
