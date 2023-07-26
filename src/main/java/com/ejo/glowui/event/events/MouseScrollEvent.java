package com.ejo.glowui.event.events;

import com.ejo.glowlib.event.EventE;
import com.ejo.glowlib.math.Vector;

public class MouseScrollEvent extends EventE {

    private long window;
    private int scroll;
    private Vector mousePos;

    @Override
    public void post(Object... args) {
        this.window = (long)args[0];
        this.scroll = (int)args[1];
        this.mousePos = (Vector)args[2];
        super.post(args);
    }

    public long getWindow() {
        return window;
    }

    public int getScroll() {
        return scroll;
    }

    public Vector getMousePos() {
        return mousePos;
    }

}
