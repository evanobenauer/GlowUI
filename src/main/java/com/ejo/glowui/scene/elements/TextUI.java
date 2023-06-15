package com.ejo.glowui.scene.elements;

import com.ejo.glowui.scene.Scene;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;

public class TextUI extends ElementUI {

    private Font font;
    private String text;
    private ColorE color;

    private FontMetrics fontMetrics;
    private ByteBuffer fontImageBuffer;

    public TextUI(Scene scene, String text, Font font, Vector pos, ColorE color) {
        super(scene, pos, true,true);
        this.font = font;
        this.text = text;
        this.color = color;

        this.fontMetrics = createFontMetrics(font);
        this.fontImageBuffer = createFontImageBuffer();
    }

    public TextUI(Scene scene, String text, String font, int modifier, int height, Vector pos, ColorE color) {
        this(scene, text, new Font(font,modifier,height),pos,color);
    }

    public TextUI(Scene scene, String text, String font, int height, Vector pos, ColorE color) {
        this(scene, text, font, Font.PLAIN, height, pos,color);
    }

    @Override
    public void draw() {
        super.draw();
        renderText(getPos().getX(), getPos().getY());
    }

    public void drawCentered(Vector size) {
        super.draw();
        renderText(getPos().getX() + size.getX()/2 - getWidth()/2,getPos().getY() + size.getY()/2 - getHeight()/2 - 2);
    }

    private void renderText(double x, double y) {
        if (getText().equals("")) return;
        GL.createCapabilities();
        GL11.glRasterPos2f((float)x, (float)y);
        GL11.glPixelZoom(1, -1);
        GL11.glDrawPixels(fontMetrics.stringWidth(getText()), fontMetrics.getHeight(), GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, fontImageBuffer);
    }


    private FontMetrics createFontMetrics(Font font) {
        BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = tempImage.getGraphics();
        graphics.setFont(font);
        return graphics.getFontMetrics();
    }

    private ByteBuffer createFontImageBuffer() {
        if (text.equals("")) return null;
        int imgWidth = fontMetrics.stringWidth(getText());
        int imgHeight = fontMetrics.getHeight();
        BufferedImage fontImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics graphics = fontImage.getGraphics();
        graphics.setFont(getFont());
        graphics.setColor(new Color(getColor().getHash()));
        graphics.drawString(getText(), 0, fontMetrics.getAscent());

        DataBuffer dataBuffer = fontImage.getRaster().getDataBuffer();
        int[] imageData = ((DataBufferInt) dataBuffer).getData();
        ByteBuffer buffer = BufferUtils.createByteBuffer(imageData.length * 4);
        for (int pixel : imageData) {
            buffer.putInt(pixel);
        }
        buffer.flip();
        return buffer;
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

    public ColorE getColor() {
        return color;
    }

    public double getWidth() {
        return fontMetrics.stringWidth(getText());
    }

    public double getHeight() {
        return getFont().getSize();
    }

    public void setFont(Font font) {
        this.font = font;
        this.fontMetrics = createFontMetrics(font);
        this.fontImageBuffer = createFontImageBuffer();
    }

    public void setFont(String font) {
        int size = this.font.getSize();
        this.font = new Font(font,Font.PLAIN,size);
        this.fontMetrics = createFontMetrics(this.font);
        this.fontImageBuffer = createFontImageBuffer();
    }

    public void setSize(int size) {
        String name = this.font.getName();
        this.font = new Font(name,Font.PLAIN,size);
        this.fontMetrics = createFontMetrics(this.font);
        this.fontImageBuffer = createFontImageBuffer();
    }

    public void setModifier(int modifier) {
        String name = this.font.getName();
        int size = this.font.getSize();
        this.font = new Font(name,modifier,size);
        this.fontMetrics = createFontMetrics(this.font);
        this.fontImageBuffer = createFontImageBuffer();
    }

    public void setText(String text) {
        this.text = text;
        this.fontImageBuffer = createFontImageBuffer();
    }

    public void setColor(ColorE color) {
        this.color = color;
        this.fontMetrics = createFontMetrics(font);
        this.fontImageBuffer = createFontImageBuffer();
    }

}
