package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowui.scene.Scene;
import org.lwjgl.opengl.GL11;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

public class RectangleUI extends PolygonUI implements IShape {

    private Vector size;

    public RectangleUI(Vector pos, Vector size, ColorE color) {
        super(pos,color, new Vector(0,0),new Vector(0,size.getY()),size,new Vector(size.getX(),0));
        this.size = size;
    }

    @Override
    public void drawElement(Scene scene, Vector mousePos) {
        vertices[1] = new Vector(0,getSize().getY());
        vertices[2] = getSize();
        vertices[3] = new Vector(getSize().getX(),0);
        super.drawElement(scene, mousePos);
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        boolean mouseOverX = mousePos.getX() >= getPos().getX() && mousePos.getX() <= getPos().getX() + getSize().getX();
        boolean mouseOverY = mousePos.getY() >= getPos().getY() && mousePos.getY() <= getPos().getY() + getSize().getY();
        return mouseOver = mouseOverX && mouseOverY;
    }


    public Vector getSize() {
        return size;
    }

    public void setSize(Vector size) {
        this.size = size;
    }

    @Override
    public Vector setCenter(Vector pos) {
        return setPos(pos.getAdded(getSize().getMultiplied(-.5f)));
    }

    @Override
    public Vector getCenter() {
        return getPos().getAdded(getSize().getMultiplied(.5f));
    }

}