package com.ejo.glowui.util;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.TextUI;
import com.ejo.glowui.scene.elements.shape.PolygonUI;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

import java.awt.*;

public class QuickDraw {

    private static final Vector NULL_POS = new Vector(-1,-1);

    public static void drawRect(Vector pos, Vector size, ColorE color) {
        new RectangleUI(pos,size,color).draw(null, NULL_POS);
    }

    public static void drawText(String text, Font font, Vector pos, ColorE color) {
        if (text.equals("")) return;
        new TextUI(text,font,pos,color).draw(null, NULL_POS);
    }

    public static void drawTextCentered(String text, Font font, Vector pos, Vector size, ColorE color) {
        if (text.equals("")) return;
        new TextUI(text,font,pos,color).drawCentered(null, NULL_POS, size);
    }

    public static void drawArrow(Vector pos, ColorE color, double size, boolean back) {
        if (back) {
            new PolygonUI(pos.getAdded(size,0).getAdded(size/10,0), color, new Vector(0, 0), new Vector(-(size/2 + size/10), 0), new Vector(-(size + size/10), size/2), new Vector(-size/2, size/2)).draw(null, NULL_POS);
            new PolygonUI(pos.getAdded(size,0).getAdded(size/10,0), color, new Vector(0, size), new Vector(-(size/2 + size/10), size), new Vector(-(size + size/10), size/2), new Vector(-size/2, size/2)).draw(null, NULL_POS);
        } else {
            new PolygonUI(pos, color, new Vector(0, 0), new Vector(size/2 + size/10, 0), new Vector(size + size/10, size/2), new Vector(size/2, size/2)).draw(null, NULL_POS);
            new PolygonUI(pos, color, new Vector(0, size), new Vector(size/2 + size/10, size), new Vector(size + size/10, size/2), new Vector(size/2, size/2)).draw(null, NULL_POS);
        }
    }

    public static void drawFPSTPS(Scene scene, Vector pos, int size, boolean label) {
        QuickDraw.drawText((label ? "FPS: " : "") + scene.getWindow().getFPS(),new Font("Arial",Font.PLAIN,size),pos,ColorE.WHITE);
        QuickDraw.drawText((label ? "TPS: " : "") + scene.getWindow().getTPS(),new Font("Arial",Font.PLAIN,size),pos.getAdded(0,size + size/5),ColorE.WHITE);
    }
}
