package com.ejo.glowui.event.events;

import com.ejo.glowui.Window;
import com.ejo.glowlib.event.EventE;

public class TickEvent extends EventE {

    private Window window;

    @Override
    public void post(Object... args) {
        this.window = (Window) args[0];
        super.post(args);
    }

    public Window getWindow() {
        return window;
    }

}
