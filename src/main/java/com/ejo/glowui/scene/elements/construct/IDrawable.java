package com.ejo.glowui.scene.elements.construct;

public interface IDrawable {

    void draw();

    void setRendered(boolean shouldRender);

    boolean shouldRender();

}
