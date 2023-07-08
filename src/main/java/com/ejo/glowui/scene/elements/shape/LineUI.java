package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import org.lwjgl.opengl.GL11;

public class LineUI extends ElementUI implements IShape {

    private Vector pos2;
    private double width;
    private Type type;
    private ColorE colorE;

    public LineUI(Scene scene, Vector pos1, Vector pos2, double width, ColorE color, Type type) {
        super(scene, pos1, true, true);
        this.pos2 = pos2;
        this.width = width;
        this.type = type;
        this.colorE = color;
    }

    @Override
    public void draw() {
        super.draw();
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

        GL11.glBegin(GL11.GL_LINES);
        GL11.glColor4d(getColor().getRed(),getColor().getGreen(),getColor().getBlue(),getColor().getAlpha());
        GL11.glVertex2d(getPos1().getX(), getPos1().getY());
        GL11.glVertex2d(getPos2().getX(), getPos2().getY());
        GL11.glEnd();
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return false;
    }


    public void setPos1(Vector point1) {
        setPos(point1);
    }

    public void setPos2(Vector point2) {
        this.pos2 = point2;
    }

    public Vector setCenter(Vector pos) {
        return null ;//TODO FINISH
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setColor(ColorE color) {
        this.colorE = color;
    }


    public Vector getPos1() {
        return getPos();
    }

    public Vector getPos2() {
        return pos2;
    }

    public Vector getCenter() {
        return getPos2().getAdded(getPos1().getMultiplied(-1)).getMultiplied(.5);
    }

    public double getWidth() {
        return width;
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
