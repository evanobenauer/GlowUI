package com.ejo.glowui.scene.elements.shape;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.util.render.TextureUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWImage;
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

    public TexturedRectUI(Vector pos, Vector size, ColorE color) {
        super(pos, size, color);
        setImage(new File(""));
    }

    @Override
    public void drawElement(Scene scene, Vector mousePos) {
        super.drawElement(scene, mousePos);
        //Add texture code here
    }


    private void drawTexturedRectangle() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glRasterPos2f((float)getPos().getX(), (float)getPos().getY());
        GL11.glDrawPixels((int)getSize().getX(),(int)getSize().getY(), GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageBuffer);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public void setImage(File imageFile) {
        this.imageBuffer = TextureUtil.getImageBuffer(imageFile,(int)getSize().getX(),(int)getSize().getY());
    }

}
