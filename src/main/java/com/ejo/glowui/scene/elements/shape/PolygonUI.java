package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import org.lwjgl.opengl.GL11;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

public class PolygonUI extends ElementUI implements IShape {

    protected Vector[] vertices;
    private ColorE color;

    public PolygonUI(Vector pos, ColorE color, Vector... vertices) {
        super(pos,true,true);
        this.color = color;
        this.vertices = vertices;
    }

    @Override
    public void drawElement(Scene scene, Vector mousePos) {
        GL11.glColor4f(getColor().getRed() / 255f, getColor().getGreen() / 255f, getColor().getBlue() / 255f, getColor().getAlpha() / 255f);
        GL11.glBegin(GL11.GL_POLYGON);
        for (Vector vert : getVertices()) GL11.glVertex2f((float) getPos().getX() + (float)vert.getX(), (float) getPos().getY() + (float) vert.getY());
        GL11.glEnd();
        GL11.glColor4f(1, 1, 1, 1);
    }

    @Override
    protected void tickElement(Scene scene, Vector mousePos) {
    }

    @Override
    public void onKeyPress(Scene scene, int key, int scancode, int action, int mods) {
    }

    @Override
    public void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos) {
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
