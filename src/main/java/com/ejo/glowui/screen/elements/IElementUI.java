package com.ejo.glowui.screen.elements;

import com.ejo.glowui.screen.Screen;
import org.util.glowlib.math.Vector;

public interface IElementUI {

    void draw();

    void tick();

    void onKeyPress(int key, int scancode, int action, int mods);

    void onMouseClick(int button, int action, int mods, Vector mousePos);

    void setRendered(boolean shouldRender);

    boolean updateMouseOver(Vector mousePos);

    Vector setPos(Vector pos);


    boolean shouldRender();

    boolean isMouseOver();

    Vector getPos();

    Screen getScreen();

}
