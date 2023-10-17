package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.util.render.GLManager;
import com.ejo.glowui.util.render.TextureUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.nio.ByteBuffer;

public class TexturedRectUI extends RectangleUI {

    private ByteBuffer imageBuffer;

    //TODO: Maybe add this for pulling textures off of 1 image
    private Vector texturePos;
    private Vector textureSize;

    public TexturedRectUI(Vector pos, Vector size, File textureFile) {
        super(pos, size, ColorE.NULL);
        setImage(textureFile);
    }

    @Override
    public void drawElement(Scene scene, Vector mousePos) {
        vertices[1] = new Vector(0,getSize().getY());
        vertices[2] = getSize();
        vertices[3] = new Vector(getSize().getX(),0);
        GL11.glRasterPos2f((float)getPos().getX(), (float)getPos().getY());
        GL11.glDrawPixels((int)getSize().getX(),(int)getSize().getY(), GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageBuffer);
    }

    public void setImage(File imageFile) {
        this.imageBuffer = TextureUtil.getImageBuffer(imageFile,(int)getSize().getX(),(int)getSize().getY());
    }

}
