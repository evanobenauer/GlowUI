package com.ejo.glowui.util;

import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.util.NumberUtil;
import org.lwjgl.glfw.GLFW;

public class UIUtil {

    public static ColorE GLOW_BLUE = new ColorE(0,125,200);
    public static ColorE WIDGET_BACKGROUND = new ColorE(50,50,50,200);

    /**
     * This method is solely used for setting fade values in animations from any element
     */
    public static float getNextAnimationValue(boolean condition, float val, int min, int max, float step) {
        if (condition) {
            if (val < max) {
                val += step;
                forceRenderFrame();
            }
        } else {
            if (val > min) {
                val -= step;
                forceRenderFrame();
            }
        }
        val = NumberUtil.getBoundValue(val,min,max).floatValue();
        return val;
    }

    //Potential name changes: sendEconomyEvent, ignoreEconomy, addEconomyFrame
    public static void forceRenderFrame() {
        GLFW.glfwPostEmptyEvent();
    }

}
