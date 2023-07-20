package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import org.lwjgl.opengl.GL11;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

public class PolygonUI extends ElementUI implements IShape {

    protected Vector[] vertices;
    private ColorE color;
    private boolean outlined;

    public PolygonUI(Vector pos, ColorE color, Vector... vertices) {
        super(pos, true, true);
        this.color = color;
        this.vertices = vertices;
        this.outlined = false;
    }

    public PolygonUI(Vector pos, ColorE color, boolean outlined, Vector... vertices) {
        this(pos, color, vertices);
        this.outlined = outlined;
    }

    @Override
    public void drawElement(Scene scene, Vector mousePos) {
        GL11.glColor4f(getColor().getRed() / 255f, getColor().getGreen() / 255f, getColor().getBlue() / 255f, getColor().getAlpha() / 255f);
        if (isOutlined()) {
            GL11.glBegin(GL11.GL_LINE_LOOP);
        } else {
            GL11.glBegin(GL11.GL_POLYGON);
        }
        for (Vector vert : getVertices()) GL11.glVertex2f((float) getPos().getX() + (float) vert.getX(), (float) getPos().getY() + (float) vert.getY());
        GL11.glEnd();
        GL11.glColor4f(1, 1, 1, 1);
    }

    @Override
    protected void tickElement(Scene scene, Vector mousePos) {
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        //TODO find how to do this lol
        return false;
    }


    public Vector setCenter(Vector pos) {
        //TODO figure this out too
        return null;
    }

    public void setOutlined(boolean outlined) {
        this.outlined = outlined;
    }

    public void setColor(ColorE color) {
        this.color = color;
    }


    public Vector[] getVertices() {
        return vertices;
    }

    public Vector getCenter() {
        //int minY = Collections.min(getVertices())
        return null;
    }

    public boolean isOutlined() {
        return outlined;
    }

    public ColorE getColor() {
        return color;
    }

}
