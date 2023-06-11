package com.ejo.glowui.util;

import org.util.glowlib.util.NumberUtil;

public class DrawUtil {


    /**
     * This method is solely used for setting fade values in animations from any element
     */
    public static float getNextFade(boolean condition, float fade, int min, int max, float speed) {
        if (condition) {
            if (fade < max) fade += speed;
        } else {
            if (fade > min) fade -= speed;
        }
        fade = NumberUtil.getBoundValue(fade,0,255).floatValue();
        return fade;
    }

}
