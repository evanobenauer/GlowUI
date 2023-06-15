package com.ejo.glowui.util;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.TextUI;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;

import java.awt.*;

public class QuickDraw {

    public static void drawRect(Scene scene, Vector pos, Vector size, ColorE color) {
        new RectangleUI(scene,pos,size,color).draw();
    }

    public static void drawText(Scene scene, String text, Font font, Vector pos, ColorE color) {
        if (text.equals("")) return;
        new TextUI(scene,text,font,pos,color).draw();
    }
}
