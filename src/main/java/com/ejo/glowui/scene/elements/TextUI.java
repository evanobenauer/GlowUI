package com.ejo.glowui.scene.elements;

import com.ejo.glowui.scene.Scene;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class TextUI extends ElementUI {

    private Font font;
    private String text;
    private ColorE color;

    private float scale = 1;

    private FontMetrics fontMetrics;
    private ByteBuffer fontImageBuffer;

    public TextUI(String text, Font font, Vector pos, ColorE color) {
        super(pos, true,true);
        this.font = font;
        this.text = text;
        this.color = color;

        this.fontMetrics = createFontMetrics(font);
        this.fontImageBuffer = createFontImageBuffer();
    }

    public TextUI(String text, String font, int modifier, int height, Vector pos, ColorE color) {
        this(text, new Font(font,modifier,height),pos,color);
    }

    public TextUI(String text, String font, int height, Vector pos, ColorE color) {
        this(text, font, Font.PLAIN, height, pos,color);
    }

    @Override
    public void draw(Scene scene, Vector mousePos) {
        super.draw(scene, mousePos);
        renderText(scene, getPos().getX(), getPos().getY());
    }

    public void drawCentered(Scene scene, Vector mousePos, Vector size) {
        super.draw(scene, mousePos);
        renderText(scene, getPos().getX() + size.getX()/2 - getWidth()/2,getPos().getY() + size.getY()/2 - getHeight()/2 - 2);
    }

    private void renderText(Scene scene, double x, double y) {
        if (getText().equals("")) return;
        GL11.glRasterPos2f((float)x, (float)y);
        //GL11.glPixelZoom(1, -1); //Disabled due to text scaling in Window draw
        int imgWidth = fontMetrics.stringWidth(getText()) + 4;
        int imgHeight = fontMetrics.getHeight() + 4;
        GL11.glDrawPixels(imgWidth,imgHeight, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, fontImageBuffer);
    }


    private FontMetrics createFontMetrics(Font font) {
        BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = tempImage.getGraphics();
        graphics.setFont(font);
        return graphics.getFontMetrics();
    }

    private ByteBuffer createFontImageBuffer() {
        if (getText().equals("")) return null;
        int imgWidth = fontMetrics.stringWidth(getText()) + 4;
        int imgHeight = fontMetrics.getHeight() + 4;
        BufferedImage fontImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);

        //Draw Text Using Graphics
        Graphics2D graphics = (Graphics2D) fontImage.getGraphics();
        graphics.setFont(getFont());
        graphics.setColor(new Color(getColor().getHash()));

        graphics.scale(getScale(),getScale());
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.drawString(getText(), 0, fontMetrics.getAscent());

        //Get DataBuffer From Graphics
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


    public Vector getSize() {
        return new Vector(getWidth(),getFont().getSize());
    }

    public void setColor(ColorE color) {
        if (this.color.equals(color)) return;
        this.color = color;
        this.fontMetrics = createFontMetrics(font);
        this.fontImageBuffer = createFontImageBuffer();
    }

    public void setFont(Font font) {
        if (this.font.equals(font)) return;
        this.font = font;
        this.fontMetrics = createFontMetrics(font);
        this.fontImageBuffer = createFontImageBuffer();
    }

    public void setFont(String font) {
        if (this.font.getName().equals(font)) return;
        int size = this.font.getSize();
        this.font = new Font(font,Font.PLAIN,size);
        this.fontMetrics = createFontMetrics(this.font);
        this.fontImageBuffer = createFontImageBuffer();
    }

    public void setSize(int size) {
        if (this.font.getSize() == size) return;
        String name = this.font.getName();
        this.font = new Font(name,Font.PLAIN,size);
        this.fontMetrics = createFontMetrics(this.font);
        this.fontImageBuffer = createFontImageBuffer();
    }

    public void setModifier(int modifier) {
        if (this.font.getStyle() == modifier) return;
        String name = this.font.getName();
        int size = this.font.getSize();
        this.font = new Font(name,modifier,size);
        this.fontMetrics = createFontMetrics(this.font);
        this.fontImageBuffer = createFontImageBuffer();
    }

    public void setText(String text) {
        if (this.text.equals(text)) return;
        this.text = text;
        this.fontImageBuffer = createFontImageBuffer();
    }

    public void setScale(double scale) {
        this.scale = (float)scale;
        this.fontImageBuffer = createFontImageBuffer();
    }


    public ColorE getColor() {
        return color;
    }

    public Font getFont() {
        return font;
    }

    public String getText() {
        return text;
    }

    public float getScale() {
        return scale;
    }

    public double getWidth() {
        return fontMetrics.stringWidth(getText()) * getScale();
    }

    public double getHeight() {
        return getFont().getSize() * getScale();
    }

}
