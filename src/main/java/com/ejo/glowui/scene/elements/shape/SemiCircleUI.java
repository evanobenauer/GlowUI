package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowlib.math.Angle;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.scene.Scene;
import org.lwjgl.opengl.GL11;

public class SemiCircleUI extends CircleUI {

    private Angle range;

    public SemiCircleUI(Vector pos, ColorE color, boolean outlined, double radius, Angle range, CircleUI.Type type) {
        super(pos,color,outlined,radius,type);
        this.range = range;
    }

    public SemiCircleUI(Vector pos, ColorE color, double radius, Angle range, CircleUI.Type type) {
        super(pos,color,false,radius,type);
        this.range = range;
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        GL11.glColor4f(getColor().getRed() / 255f, getColor().getGreen() / 255f, getColor().getBlue() / 255f, getColor().getAlpha() / 255f);
        GL11.glDisable(GL11.GL_LINE_STIPPLE);
        GL11.glBegin(isOutlined() ? GL11.GL_LINE_LOOP : GL11.GL_POLYGON);
        double radianIncrement = getRange().getRadians() / getType().getVertices();
        GL11.glVertex2f((float) getPos().getX(), (float) getPos().getY());
        for (int i = 0; i <= getType().getVertices(); i++) {
            Vector vert = new Vector(Math.cos(radianIncrement*i),Math.sin(radianIncrement*i)).getMultiplied(getRadius());
            GL11.glVertex2f((float) getPos().getX() + (float) vert.getX(), (float) getPos().getY() + (float) vert.getY());
        }
        GL11.glEnd();
        GL11.glColor4f(1, 1, 1, 1);
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        Vector relativeMousePos = mousePos.getSubtracted(getCenter());
        if (getRadius() < relativeMousePos.getMagnitude()) return false;

        if (relativeMousePos.getY() > 0) return relativeMousePos.getTheta().getDegrees() <= getRange().getDegrees();
        else return relativeMousePos.getTheta().getDegrees() >= getRange().getDegrees();
    }

    public void setRange(Angle range) {
        this.range = range;
    }

    public Angle getRange() {
        return range;
    }

}
