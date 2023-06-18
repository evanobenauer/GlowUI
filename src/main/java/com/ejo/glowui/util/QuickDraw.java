package com.ejo.glowui.util;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.TextUI;
import com.ejo.glowui.scene.elements.shape.PolygonUI;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

import java.awt.*;

public class QuickDraw {

    public static void drawRect(Scene scene, Vector pos, Vector size, ColorE color) {
        new RectangleUI(scene,pos,size,color).draw();
    }

    //TODO: Calling on this method may cause unneeded lad from constantly regenerating a texture for the text. Optimize in the future
    public static void drawText(Scene scene, String text, Font font, Vector pos, ColorE color) {
        if (text.equals("")) return;
        new TextUI(scene,text,font,pos,color).draw();
    }

    public static void drawTextCentered(Scene scene, String text, Font font, Vector pos, Vector size, ColorE color) {
        if (text.equals("")) return;
        new TextUI(scene,text,font,pos,color).drawCentered(size);
    }

    public static void drawArrow(Scene scene, Vector pos, ColorE color, boolean back) {
        if (back) {
            new PolygonUI(scene, pos, color, new Vector(0, 0), new Vector(-30, 0), new Vector(-55, 25), new Vector(-25, 25)).draw();
            new PolygonUI(scene, pos, color, new Vector(0, 50), new Vector(-30, 50), new Vector(-55, 25), new Vector(-25, 25)).draw();
        } else {
            new PolygonUI(scene, pos, color, new Vector(0, 0), new Vector(30, 0), new Vector(55, 25), new Vector(25, 25)).draw();
            new PolygonUI(scene, pos, color, new Vector(0, 50), new Vector(30, 50), new Vector(55, 25), new Vector(25, 25)).draw();
        }
    }

    public static void drawFPSTPS(Scene scene, Vector pos, int size, boolean label) {
        QuickDraw.drawText(scene,(label ? "FPS: " : "") + scene.getWindow().getFPS(),new Font("Arial",Font.PLAIN,size),pos,ColorE.WHITE);
        QuickDraw.drawText(scene, (label ? "TPS: " : "") + scene.getWindow().getTPS(),new Font("Arial",Font.PLAIN,size),pos.getAdded(0,size + size/5),ColorE.WHITE);
    }
}
