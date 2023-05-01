package com.ejo.glowui.scene.elements;

public interface IDrawable {

    void draw();
    void setRendered(boolean shouldRender);
    boolean shouldRender();

}
