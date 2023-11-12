package com.ejo.glowui.util.render;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.net.URL;
import java.nio.ByteBuffer;

public class TextureUtil {

    public static ByteBuffer getImageBuffer(URL imageURL, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = img.createGraphics();
        try {
            graphics.scale((double) img.getWidth() / ImageIO.read(imageURL).getWidth(),(double) img.getHeight() / ImageIO.read(imageURL).getHeight());
            graphics.drawImage(ImageIO.read(imageURL), 0, 0, null);
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        } catch (Exception e) {
            e.printStackTrace();
        }
        invertRB(img);

        DataBuffer dataBuffer = img.getRaster().getDataBuffer();
        int[] imageData = ((DataBufferInt) dataBuffer).getData();
        ByteBuffer buffer = BufferUtils.createByteBuffer(imageData.length * 16);
        for (int pixel : imageData) buffer.putInt(pixel);
        buffer.flip();
        return buffer;
    }

    private static void invertRB(BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color c = new Color(image.getRGB(x, y));
                Color c2 = new Color(c.getBlue(),c.getGreen(),c.getRed(),c.getAlpha());
                image.setRGB(x,y,c2.hashCode());
            }
        }
    }

}
