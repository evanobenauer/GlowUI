package com.ejo.glowui.util.render;

import com.ejo.glowlib.math.Vector;
import org.lwjgl.opengl.GL11;

public class GLManager {

    public static void translate(Vector vec) {
        GL11.glTranslated(vec.getX(),vec.getY(),vec.getZ());
    }

    public static void scale(Vector vec) {
        GL11.glScaled(vec.getX(),vec.getY(),vec.getZ());
    }

    public static void scale(double val) {
        GL11.glScaled(val,val,val);
    }

    public static void textureScale(Vector vec) {
        GL11.glPixelZoom((float) vec.getX(), (float)-vec.getY());
    }

    public static void textureScale(double val) {
        GL11.glPixelZoom((float) val, (float)-val);
    }

}
