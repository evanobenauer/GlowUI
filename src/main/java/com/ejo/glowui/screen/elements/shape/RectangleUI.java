package com.ejo.glowui.screen.elements.shape;

import com.ejo.glowui.screen.Screen;
import com.ejo.glowui.screen.elements.shape.ShapeUI;
import org.lwjgl.opengl.GL11;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

public class RectangleUI extends ShapeUI {

    Vector size;

    public RectangleUI(Screen screen, Vector pos, Vector size, ColorE color) {
        super(screen,pos,color,true);
        this.size = size;
    }

    @Override
    public void draw() {
        if (shouldRender()) {
            GL11.glColor4f(getColor().getRed() / 255f, getColor().getGreen() / 255f, getColor().getBlue() / 255f,getColor().getAlpha()/255f);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f((float) getPos().getX(), (float) getPos().getY());
            GL11.glVertex2f((float) (getPos().getX() + getSize().getX()), (float) getPos().getY());
            GL11.glVertex2f((float) (getPos().getX() + getSize().getX()), (float) (getPos().getY() + getSize().getY()));
            GL11.glVertex2f((float) getPos().getX(), (float) (getPos().getY() + getSize().getY()));
            GL11.glEnd();
            GL11.glColor4f(1,1,1,1);
        }
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