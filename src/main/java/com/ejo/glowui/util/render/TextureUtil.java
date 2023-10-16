package com.ejo.glowui.util.render;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.nio.ByteBuffer;

public class TextureUtil {

    public static ByteBuffer getImageBuffer(File imageFile, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics = img.createGraphics();
        try {
            graphics.scale((double) img.getWidth() / ImageIO.read(imageFile).getWidth(),(double) img.getHeight() / ImageIO.read(imageFile).getHeight());
            graphics.drawImage(ImageIO.read(imageFile), 0, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataBuffer dataBuffer = img.getRaster().createWritableChild(0, 0, img.getWidth(), img.getHeight(), 0, 0, new int[]{2, 1, 0}).getDataBuffer();
        int[] imageData = ((DataBufferInt) dataBuffer).getData();
        ByteBuffer buffer = BufferUtils.createByteBuffer(imageData.length * 16);
        for (int pixel : imageData) buffer.putInt(pixel);
        buffer.flip();
        return buffer;
    }

}
