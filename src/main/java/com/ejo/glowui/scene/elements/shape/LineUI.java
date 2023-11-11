package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowlib.math.Angle;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import org.lwjgl.opengl.GL11;

public class LineUI extends ElementUI implements IShape {

    private ColorE colorE;

    private double width;
    private Type type;

    private Vector[] vertices;

    public LineUI(ColorE color, Type type, double width, Vector... vertices) {
        super(vertices[0], true,true);
        this.vertices = vertices;
        this.colorE = color;
        this.type = type;
        this.width = width;
    }

    public LineUI(Vector pos, Angle angle, double length, ColorE color, Type type, double width) {
        this(color,type,width,pos,pos.getAdded(angle.getUnitVector().getMultiplied(length)));
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        GL11.glLineWidth((float)getWidth());

        switch(getType()) {
            case PLAIN -> {
                GL11.glDisable(GL11.GL_LINE_STIPPLE);
                GL11.glEnable(GL11.GL_LINE_STRIP);
            }
            case DOTTED -> {
                GL11.glEnable(GL11.GL_LINE_STIPPLE);
                GL11.glLineStipple(3,(short)0xAAAA);
            }
            case DASHED -> {
                GL11.glEnable(GL11.GL_LINE_STIPPLE);
                GL11.glLineStipple(5,(short)0xBBBB);
            }
        }

        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glColor4d(getColor().getRed(),getColor().getGreen(),getColor().getBlue(),getColor().getAlpha());
        for (Vector vertex : getVertices()) GL11.glVertex2d(vertex.getX(), vertex.getY());
        GL11.glEnd();
    }

    @Override
    protected void tickElement(Scene scene, Vector mousePos) {
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return false;
    }


    public void setVertices(Vector[] vertices) {
        this.vertices = vertices;
    }

    @Override
    public LineUI setPos(Vector pos) {
        getVertices()[0] = pos;
        return this;
    }

    public LineUI setCenter(Vector pos) {
        Vector oldCenter = getCenter();
        for (int i = 0; i < getVertices().length; i++) {
            Vector vertex = getVertices()[i];
            getVertices()[i] = vertex.getAdded(pos.getSubtracted(oldCenter));
        }
        return this;
    }

    public LineUI setWidth(double width) {
        this.width = width;
        return this;
    }

    public LineUI setType(Type type) {
        this.type = type;
        return this;
    }

    public LineUI setColor(ColorE color) {
        this.colorE = color;
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

        return new Vector(getVertices()[iMaxX].getX() + getVertices()[iMinX].getX(),getVertices()[iMaxY].getY() + getVertices()[iMinY].getY()).getMultiplied(.5);
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        double length = 0;
        for (int i = 0; i < getVertices().length; i++) {
            boolean isThereNextVertex = i == getVertices().length - 1;
            Vector vertex = getVertices()[i];
            Vector nextVertex = (isThereNextVertex) ? getVertices()[i + 1] : null;
            if (isThereNextVertex) length += nextVertex.getSubtracted(vertex).getMagnitude();
        }

        return length;
    }

    public Type getType() {
        return type;
    }

    public ColorE getColor() {
        return colorE;
    }


    public enum Type {
        PLAIN,
        DOTTED,
        DASHED
    }
}
