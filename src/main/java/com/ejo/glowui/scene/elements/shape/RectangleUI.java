package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import org.lwjgl.opengl.GL11;

public class RectangleUI extends PolygonUI implements IShape {

    private Vector size;
    private float outlineWidth;

    //TODO: Make order pos, color, size, etc...
    public RectangleUI(Vector pos, Vector size, boolean outlined, float outlineWidth, ColorE color) {
        super(pos,color, outlined, new Vector(0,0),new Vector(0,size.getY()),size,new Vector(size.getX(),0));
        this.size = size;
        this.outlineWidth = outlineWidth;
    }

    public RectangleUI(Vector pos, Vector size, ColorE color) {
        this(pos,size,false,1,color);
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        vertices[1] = new Vector(0,getSize().getY());
        vertices[2] = getSize();
        vertices[3] = new Vector(getSize().getX(),0);
        GL11.glLineWidth(getOutlineWidth());
        super.drawElement(scene, mousePos);
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        boolean mouseOverX = mousePos.getX() >= getPos().getX() && mousePos.getX() <= getPos().getX() + getSize().getX();
        boolean mouseOverY = mousePos.getY() >= getPos().getY() && mousePos.getY() <= getPos().getY() + getSize().getY();
        return mouseOver = mouseOverX && mouseOverY;
    }

    public RectangleUI setSize(Vector size) {
        this.size = size;
        return this;
    }

    public RectangleUI setOutlineWidth(float outlineWidth) {
        this.outlineWidth = outlineWidth;
        return this;
    }


    @Override
    public Vector getCenter() {
        return getPos().getAdded(getSize().getMultiplied(.5f));
    }

    public Vector getSize() {
        return size;
    }

    public float getOutlineWidth() {
        return outlineWidth;
    }

}