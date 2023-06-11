package com.ejo.glowui.scene.elements;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.nanovg.NanoVG.*;

//TODO: INCOMPLETE PLACEHOLDER
//https://github.com/SilverTiger/lwjgl3-tutorial/wiki/Fonts
public class TextUI extends ElementUI {

    private Font font;
    private String text;

    public TextUI(Scene scene, String text, Font font, Vector pos) {
        super(scene, pos, true,true);
        this.font = font;
        this.text = text;
    }

    @Override
    public void draw() {
        super.draw();
        //setFont(new Font("Arial", Font.PLAIN, 54));
        drawText(getText(), getPos().getX(), getPos().getY(), getFont());
    }

    public void drawCentered(Vector size) {
        drawText(getText(),getPos().getX() + size.getX() - getWidth()/2,getPos().getY() + size.getY() - getHeight()/2,getFont());
    }

    private void drawText(String text, double x, double y, Font font) {
    }


    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return false;
    }

    public Font getFont() {
        return font;
    }

    public String getText() {
        return text;
    }

    public double getWidth() {
        return 0d;
    }

    public double getHeight() {
       return 0d;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setText(String text) {
        this.text = text;
    }

}
