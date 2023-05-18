package com.ejo.glowui.scene.elements.construct;

public interface ITick {

    void tick();

    void setTicking(boolean shouldTick);

    boolean shouldTick();

}
