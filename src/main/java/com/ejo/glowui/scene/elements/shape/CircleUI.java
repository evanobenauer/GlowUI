package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import org.lwjgl.opengl.GL11;

public class CircleUI extends ElementUI implements IShape {

    private ColorE color;
    private boolean outlined;
    private double radius;
    private Type type;

    public CircleUI(Vector pos, ColorE color, boolean outlined, double radius, Type type) {
        super(pos,true,true);
        this.color = color;
        this.outlined = outlined;
        this.radius = radius;
        this.type = type;
    }

    public CircleUI(Vector pos, ColorE color, double radius, Type type) {
        this(pos,color,false,radius,type);
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        GL11.glColor4f(getColor().getRed() / 255f, getColor().getGreen() / 255f, getColor().getBlue() / 255f, getColor().getAlpha() / 255f);
        GL11.glDisable(GL11.GL_LINE_STIPPLE);
        GL11.glBegin(isOutlined() ? GL11.GL_LINE_LOOP : GL11.GL_POLYGON);
        double radianIncrement = 2*Math.PI / getType().getVertices();
        for (int i = 1; i <= getType().getVertices(); i++) {
            Vector vert = new Vector(Math.cos(radianIncrement*i),Math.sin(radianIncrement*i)).getMultiplied(getRadius());
            GL11.glVertex2f((float) getPos().getX() + (float) vert.getX(), (float) getPos().getY() + (float) vert.getY());
        }
        GL11.glEnd();
        GL11.glColor4f(1, 1, 1, 1);
    }

    @Override
    protected void tickElement(Scene scene, Vector mousePos) {
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return mousePos.getAdded(getCenter().getMultiplied(-1)).getMagnitude() < getRadius();
    }


    @Override
    public Vector setCenter(Vector pos) {
        return setPos(pos);
    }

    @Override
    public void setColor(ColorE color) {
        this.color = color;
    }

    public void setOutlined(boolean outlined) {
        this.outlined = outlined;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setType(Type type) {
        this.type = type;
    }


    @Override
    public Vector getCenter() {
        return getPos();
    }

    @Override
    public ColorE getColor() {
        return this.color;
    }

    public boolean isOutlined() {
        return outlined;
    }

    public double getRadius() {
        return radius;
    }

    public Type getType() {
        return type;
    }


    public enum Type {
        POOR(10),
        LOW(16),
        MEDIUM(22),
        HIGH(28),
        ULTRA(34);

        private final int vertices;
        Type(int vertices) {
            this.vertices = vertices;
        }
        public int getVertices() {
            return vertices;
        }
    }

}
