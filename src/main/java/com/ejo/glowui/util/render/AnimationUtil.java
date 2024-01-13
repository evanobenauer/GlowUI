package com.ejo.glowui.util.render;

import com.ejo.glowlib.util.NumberUtil;
import com.ejo.glowui.util.UIUtil;

public class AnimationUtil {

    /**
     * This method is solely used for setting fade values in animations from any element
     */
    public static float getNextAnimationValue(boolean condition, float val, int min, int max, float step) {
        if (condition) {
            if (val < max) {
                val += step;
                UIUtil.forceEconRenderFrame();
            }
        } else {
            if (val > min) {
                val -= step;
                UIUtil.forceEconRenderFrame();
            }
        }
        val = NumberUtil.getBoundValue(val,min,max).floatValue();
        return val;
    }

}
