package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowlib.math.Angle;
import com.ejo.glowlib.math.MathE;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import org.lwjgl.opengl.GL11;

public class CircleUI extends ElementUI implements IShape {

    private static final Angle FULL = new Angle(360,true);

    private ColorE color;
    private boolean outlined;
    private double radius;
    private Angle range;
    private Type type;

    public CircleUI(Vector pos, ColorE color, boolean outlined, double radius, Angle range, Type type) {
        super(pos,true,true);
        this.color = color;
        this.outlined = outlined;
        this.radius = radius;
        this.range = range;
        this.type = type;
    }

    public CircleUI(Vector pos, ColorE color, double radius, Angle range, CircleUI.Type type) {
        this(pos,color,false,radius,range,type);
    }

    public CircleUI(Vector pos, ColorE color, boolean outlined, double radius, CircleUI.Type type) {
        this(pos,color,outlined,radius,FULL,type);
    }

    public CircleUI(Vector pos, ColorE color, double radius, Type type) {
        this(pos,color,false,radius,FULL,type);
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        GL11.glColor4f(getColor().getRed() / 255f, getColor().getGreen() / 255f, getColor().getBlue() / 255f, getColor().getAlpha() / 255f);
        GL11.glDisable(GL11.GL_LINE_STIPPLE);
        GL11.glBegin(isOutlined() ? GL11.GL_LINE_LOOP : GL11.GL_POLYGON);
        double radianIncrement = getRange().getRadians() / getType().getVertices();
        if (!getRange().equals(FULL)) GL11.glVertex2f((float) getPos().getX(), (float) getPos().getY()); //Center Vertex
        for (int i = 0; i <= getType().getVertices(); i++) {
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
        Vector relativeMousePos = mousePos.getSubtracted(getCenter());
        if (getRadius() < relativeMousePos.getMagnitude()) return false;
        if (getRange().equals(FULL)) return true;

        if (relativeMousePos.getY() > 0) return relativeMousePos.getTheta().getDegrees() <= getRange().getDegrees();
        else return relativeMousePos.getTheta().getDegrees() >= getRange().getDegrees();
    }


    @Override
    public CircleUI setCenter(Vector pos) {
        return (CircleUI) setPos(pos);
    }

    @Override
    public CircleUI setColor(ColorE color) {
        this.color = color;
        return this;
    }

    public CircleUI setOutlined(boolean outlined) {
        this.outlined = outlined;
        return this;
    }

    public CircleUI setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    public CircleUI setRange(Angle range) {
        this.range = range;
        return this;
    }

    public CircleUI setType(Type type) {
        this.type = type;
        return this;
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

    public Angle getRange() {
        return range;
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
