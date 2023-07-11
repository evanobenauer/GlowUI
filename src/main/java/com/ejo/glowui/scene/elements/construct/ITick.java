package com.ejo.glowui.scene.elements.construct;

import com.ejo.glowlib.math.Vector;
import com.ejo.glowui.scene.Scene;

public interface ITick {

    void tick(Scene scene, Vector mousePos);

    void setTicking(boolean shouldTick);

    boolean shouldTick();

}
