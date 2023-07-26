package com.ejo.glowui.event.events;

import com.ejo.glowlib.event.EventE;
import com.ejo.glowlib.math.Vector;

public class MouseScrollEvent extends EventE {

    private long window;
    private int scrollX;
    private int scrollY;
    private Vector mousePos;

    @Override
    public void post(Object... args) {
        this.window = (long)args[0];
        this.scrollX = (int)args[1];
        this.scrollY = (int)args[2];
        this.mousePos = (Vector)args[3];
        super.post(args);
    }

    public long getWindow() {
        return window;
    }

    public int getScrollX() {
        return scrollX;
    }

    public int getScrollY() {
        return scrollY;
    }

    public Vector getMousePos() {
        return mousePos;
    }

}
