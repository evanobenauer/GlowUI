package com.ejo.glowui.util;

import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.util.NumberUtil;
import org.lwjgl.glfw.GLFW;

public class DrawUtil {

    public static ColorE GLOW_BLUE = new ColorE(0,125,200);
    public static ColorE WIDGET_BACKGROUND = new ColorE(50,50,50,200);

    /**
     * This method is solely used for setting fade values in animations from any element
     */
    public static float getNextAnimValue(boolean condition, float val, int min, int max, float step) {
        if (condition) {
            if (val < max) {
                val += step;
                GLFW.glfwPostEmptyEvent();
            }
        } else {
            if (val > min) {
                val -= step;
                GLFW.glfwPostEmptyEvent();
            }
        }
        val = NumberUtil.getBoundValue(val,min,max).floatValue();
        return val;
    }

}
