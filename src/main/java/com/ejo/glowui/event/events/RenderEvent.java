package com.ejo.glowui.event.events;

import com.ejo.glowlib.math.Vector;
import com.ejo.glowui.Window;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowlib.event.EventE;

public class RenderEvent extends EventE {

    Window window;

    @Override
    public void post(Object... args) {
        this.window = (Window) args[0];
        super.post(args);
    }

    public Window getWindow() {
        return window;
    }

}
