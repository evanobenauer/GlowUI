package com.ejo.glowui.event.events;

import org.util.glowlib.event.EventE;
import org.util.glowlib.math.Vector;

public class MouseClickEvent extends EventE {

    public static int BUTTON_LEFT = 0;
    public static int BUTTON_RIGHT = 1;
    public static int BUTTON_MIDDLE = 2;

    public static int ACTION_CLICK = 1;
    public static int ACTION_RELEASE = 0;

    long window;
    int button;
    int action;
    int mods;
    Vector mousePos;

    @Override
    public void post(Object... args) {
        this.window = (long)args[0];
        this.button = (int)args[1];
        this.action = (int)args[2];
        this.mods = (int)args[3];
        this.mousePos = (Vector)args[4];
        super.post(args);
    }

    public long getWindow() {
        return window;
    }

    public int getButton() {
        return button;
    }

    public int getAction() {
        return action;
    }

    public int getMods() {
        return mods;
    }

    public Vector getMousePos() {
        return mousePos;
    }

}
