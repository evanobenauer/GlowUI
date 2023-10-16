package com.ejo.glowui.util.render;

import com.ejo.glowui.Window;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.TextUI;
import com.ejo.glowui.scene.elements.shape.PolygonUI;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowui.util.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class QuickDraw {

    public static void drawRect(Vector pos, Vector size, ColorE color) {
        new RectangleUI(pos,size,color).draw(null, Mouse.NULL_POS);
    }

    public static void drawQuad(Vector pos, Vector size, ColorE color, boolean outlined) {
        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        GL11.glDisable(GL11.GL_LINE_STIPPLE);
        GL11.glBegin(outlined ? GL11.GL_LINE_LOOP : GL11.GL_QUADS);
        GL11.glVertex2f((float) pos.getX(), (float) pos.getY());
        GL11.glVertex2f((float) pos.getX() + (float) size.getX(), (float) pos.getY());
        GL11.glVertex2f((float) pos.getX() + (float) size.getX(), (float) pos.getY() + (float) size.getY());
        GL11.glVertex2f((float) pos.getX(), (float) pos.getY() + (float) size.getY());
        GL11.glEnd();
        GL11.glColor4f(1, 1, 1, 1);
    }


    public static void drawText(String text, Font font, Vector pos, ColorE color) {
        if (text.equals("")) return;
        new TextUI(text,font,pos,color).draw(null, Mouse.NULL_POS);
    }

    public static void drawTextCentered(String text, Font font, Vector pos, Vector size, ColorE color) {
        if (text.equals("")) return;
        new TextUI(text,font,pos,color).drawCentered(null, Mouse.NULL_POS, size);
    }

    public static void drawArrow(Vector pos, ColorE color, double size, boolean back) {
        if (back) {
            new PolygonUI(pos.getAdded(size,0).getAdded(size/10,0), color, new Vector(0, 0), new Vector(-(size/2 + size/10), 0), new Vector(-(size + size/10), size/2), new Vector(-size/2, size/2)).draw(null, Mouse.NULL_POS);
            new PolygonUI(pos.getAdded(size,0).getAdded(size/10,0), color, new Vector(0, size), new Vector(-(size/2 + size/10), size), new Vector(-(size + size/10), size/2), new Vector(-size/2, size/2)).draw(null, Mouse.NULL_POS);
        } else {
            new PolygonUI(pos, color, new Vector(0, 0), new Vector(size/2 + size/10, 0), new Vector(size + size/10, size/2), new Vector(size/2, size/2)).draw(null, Mouse.NULL_POS);
            new PolygonUI(pos, color, new Vector(0, size), new Vector(size/2 + size/10, size), new Vector(size + size/10, size/2), new Vector(size/2, size/2)).draw(null, Mouse.NULL_POS);
        }
    }

    public static void drawFPSTPS(Scene scene, Vector pos, int size, boolean label, boolean showMax) {
        Window window = scene.getWindow();
        QuickDraw.drawText((label ? "FPS: " : "") + window.getFPS() + (showMax ? " (" + window.getMaxFPS() + (window.getVSync() ? "V" : "") + (window.isEconomic() ? "E" : "") + ")" : ""), Fonts.getDefaultFont(size),pos,ColorE.WHITE);
        QuickDraw.drawText((label ? "TPS: " : "") + window.getTPS() + (showMax ? " (" + window.getMaxTPS() + ")" : ""),Fonts.getDefaultFont(size),pos.getAdded(0,size + size/5),ColorE.WHITE);
    }
}
