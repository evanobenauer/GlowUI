package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import org.lwjgl.opengl.GL11;

public class CircleUI extends ElementUI implements IShape {

    private ColorE color;
    private double radius;
    private int vertexCount;

    public CircleUI(Vector pos, ColorE color, double radius, int vertexCount) {
        super(pos,true,true);
        this.color = color;
        this.radius = radius;
        this.vertexCount = vertexCount;
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        GL11.glColor4f(getColor().getRed() / 255f, getColor().getGreen() / 255f, getColor().getBlue() / 255f, getColor().getAlpha() / 255f);
        GL11.glDisable(GL11.GL_LINE_STIPPLE);
        GL11.glBegin(GL11.GL_POLYGON);
        double radianIncrement = 2*Math.PI / getVertexCount();
        for (int i = 1; i <= getVertexCount(); i++) {
            Vector vert = new Vector(Math.cos(radianIncrement*i),Math.sin(radianIncrement*i)).getMultiplied(getRadius());
            GL11.glVertex2f((float) getPos().getX() + (float) vert.getX(), (float) getPos().getY() + (float) vert.getY());
        }
        GL11.glEnd();
        GL11.glColor4f(1, 1, 1, 1);
    }

    @Override
    protected void tickElement(Scene scene, Vector mousePos) {
        //NONE
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        //TODO: Implement This
        return false;
    }


    @Override
    public Vector setCenter(Vector pos) {
        return setPos(pos);
    }

    @Override
    public void setColor(ColorE color) {
        this.color = color;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }


    @Override
    public Vector getCenter() {
        return getPos();
    }

    @Override
    public ColorE getColor() {
        return this.color;
    }

    public double getRadius() {
        return radius;
    }

    public int getVertexCount() {
        return vertexCount;
    }

}
