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
    protected void drawElement(Scene scene, Vector mousePos) {
        GL11.glColor4f(getColor().getRed() / 255f, getColor().getGreen() / 255f, getColor().getBlue() / 255f, getColor().getAlpha() / 255f);
        GL11.glDisable(GL11.GL_LINE_STIPPLE);
        GL11.glBegin(isOutlined() ? GL11.GL_LINE_LOOP : GL11.GL_POLYGON);
        for (Vector vert : getVertices()) GL11.glVertex2f((float) getPos().getX() + (float) vert.getX(), (float) getPos().getY() + (float) vert.getY());
        GL11.glEnd();
        GL11.glColor4f(1, 1, 1, 1);
    }

    @Override
    protected void tickElement(Scene scene, Vector mousePos) {
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return false; //TODO finish this
    }


    public PolygonUI setCenter(Vector pos) {
        setPos(getPos().getSubtracted(getCenter()).getAdded(pos));
        return this;
    }

    public PolygonUI setOutlined(boolean outlined) {
        this.outlined = outlined;
        return this;
    }

    public PolygonUI setColor(ColorE color) {
        this.color = color;
        return this;
    }


    public Vector[] getVertices() {
        return vertices;
    }

    public Vector getCenter() {
        int iMinX = 0;
        int iMaxX = 0;

        int iMinY = 0;
        int iMaxY = 0;

        for (int i = 0; i < getVertices().length; i++) {
            Vector vertex = getVertices()[i];
            if (vertex.getX() > getVertices()[iMaxX].getX()) iMaxX = i;
            if (vertex.getX() < getVertices()[iMinX].getX()) iMinX = i;
            if (vertex.getY() > getVertices()[iMaxY].getY()) iMaxY = i;
            if (vertex.getY() < getVertices()[iMinY].getY()) iMinY = i;
        }

        return new Vector(getVertices()[iMaxX].getX() + getVertices()[iMinX].getX(),getVertices()[iMaxY].getY() + getVertices()[iMinY].getY()).getMultiplied(.5).getAdded(getPos());
    }

    public boolean isOutlined() {
        return outlined;
    }

    public ColorE getColor() {
        return color;
    }

}
