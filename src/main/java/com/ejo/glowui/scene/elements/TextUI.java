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

    public TextUI(Scene scene, String text, String font, int size, Vector pos, ColorE color) {
        super(scene, pos, true,true);
        this.font = new Font(font,Font.PLAIN,size);
        this.text = text;
        this.color = color;

        this.fontMetrics = createFontMetrics(this.font);
        this.fontImageBuffer = createFontImageBuffer();
    }

    @Override
    public void draw() {
        super.draw();
        if (getText().equals("")) return;
        renderText(getPos().getX(), getPos().getY());
    }

    public void drawCentered(Vector size) {
        super.draw();
        if (getText().equals("")) return;
        renderText(getPos().getX() + size.getX() - getWidth()/2,getPos().getY() + size.getY() - getHeight()/2);
    }

    private void renderText(double x, double y) {
        GL.createCapabilities();
        GL11.glRasterPos2f((float)x, (float)y);
        GL11.glPixelZoom(1, -1);
        GL11.glDrawPixels(fontMetrics.stringWidth(getText()), fontMetrics.getHeight(), GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, fontImageBuffer);
    }


    private FontMetrics createFontMetrics(Font font) {
        BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics graphics = tempImage.getGraphics();
        graphics.setFont(font);
        return graphics.getFontMetrics();
    }

    private ByteBuffer createFontImageBuffer() {
        int imgWidth = fontMetrics.stringWidth(getText());
        int imgHeight = fontMetrics.getHeight();
        BufferedImage fontImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_4BYTE_ABGR);

        Graphics graphics = fontImage.getGraphics();
        graphics.setFont(getFont());
        graphics.setColor(new Color(getColor().getHash()));
        graphics.drawString(getText(), 0, fontMetrics.getAscent());

        DataBuffer dataBuffer = fontImage.getRaster().getDataBuffer();
        byte[] imageData = ((DataBufferByte) dataBuffer).getData();
        ByteBuffer buffer = BufferUtils.createByteBuffer(imageData.length * 4);
        for (byte pixel : imageData) {
            buffer.put(pixel);
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

    public void setText(String text) {
        this.text = text;
        this.fontImageBuffer = createFontImageBuffer();
    }

    public void setColor(ColorE color) {
        this.color = color;
    }

}
