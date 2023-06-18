package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import org.lwjgl.opengl.GL11;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

public class PolygonUI extends ElementUI implements IShape {

    protected Vector[] vertices;
    private ColorE color;

    public PolygonUI(Scene scene, Vector pos, ColorE color, Vector... vertices) {
        super(scene,pos,true,true);
        this.color = color;
        this.vertices = vertices;
    }

    @Override
    public void draw() {
        super.draw();
        GL11.glColor4f(getColor().getRed() / 255f, getColor().getGreen() / 255f, getColor().getBlue() / 255f, getColor().getAlpha() / 255f);
        GL11.glBegin(GL11.GL_POLYGON);
        for (Vector vert : getVertices()) GL11.glVertex2f((float) getPos().getX() + (float)vert.getX(), (float) getPos().getY() + (float) vert.getY());
        GL11.glEnd();
        GL11.glColor4f(1, 1, 1, 1);
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

    public Vector getCenter() {
        //int minY = Collections.min(getVertices())
        return null;
    }

    public Vector[] getVertices() {
        return vertices;
    }

    public void setColor(ColorE color) {
        this.color = color;
    }

    public ColorE getColor() {
        return color;
    }


}
