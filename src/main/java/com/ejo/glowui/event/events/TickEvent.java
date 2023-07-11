package com.ejo.glowui.event.events;

import com.ejo.glowlib.math.Vector;
import com.ejo.glowui.Window;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowlib.event.EventE;

public class TickEvent extends EventE {

    Window window;
    Vector mousePos;

    @Override
    public void post(Object... args) {
        this.window = (Window) args[0];
        this.mousePos = (Vector) args[1];
        super.post(args);
    }

    public Window getWindow() {
        return window;
    }

    public Vector getMousePos() {
        return mousePos;
    }

}
