package com.ejo.glowui.event.events;

import com.ejo.glowlib.event.EventE;
import com.ejo.glowlib.math.Vector;

public class MouseClickEvent extends EventE {

    private long window;
    private int button;
    private int action;
    private int mods;
    private Vector mousePos;

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
