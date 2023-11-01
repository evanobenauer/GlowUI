package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.scene.Scene;
import org.lwjgl.opengl.GL11;

public class GradientRectangleUI extends RectangleUI {

    private ColorE color1;
    private ColorE color2;

    private Type type;

    public GradientRectangleUI(Vector pos, Vector size, ColorE color1, ColorE color2, Type type) {
        super(pos, size, ColorE.NULL);
        this.color1 = color1;
        this.color2 = color2;
        this.type = type;
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        vertices[1] = new Vector(0,getSize().getY());
        vertices[2] = getSize();
        vertices[3] = new Vector(getSize().getX(),0);
        GL11.glColor4f(getColor().getRed() / 255f, getColor().getGreen() / 255f, getColor().getBlue() / 255f, getColor().getAlpha() / 255f);
        GL11.glDisable(GL11.GL_LINE_STIPPLE);
        GL11.glBegin(isOutlined() ? GL11.GL_LINE_LOOP : GL11.GL_POLYGON);
        boolean cycle = true;
        boolean swap = false;
        for (Vector vert : getVertices()) {
            ColorE col = cycle ? getColor2() : getColor1();
            GL11.glColor4f(col.getRed() / 255f, col.getGreen() / 255f, col.getBlue() / 255f, col.getAlpha() / 255f);
            GL11.glVertex2f((float) getPos().getX() + (float) vert.getX(), (float) getPos().getY() + (float) vert.getY());
            if (getType().equals(Type.VERTICAL)) swap = !swap;
            if (swap) cycle = !cycle;
            if (getType().equals(Type.HORIZONTAL)) swap = !swap;
        }
        GL11.glEnd();
        GL11.glColor4f(1, 1, 1, 1);
    }

    public GradientRectangleUI setColor1(ColorE color1) {
        this.color1 = color1;
        return this;
    }

    public GradientRectangleUI setColor2(ColorE color2) {
        this.color2 = color2;
        return this;
    }

    public GradientRectangleUI setType(Type type) {
        this.type = type;
        return this;
    }

    public ColorE getColor1() {
        return color1;
    }

    public ColorE getColor2() {
        return color2;
    }

    public Type getType() {
        return type;
    }

    @Override
    public ColorE getColor() {
        return getColor1();
    }


    public enum Type {
        VERTICAL,
        HORIZONTAL
    }
}
