package com.ejo.glowui.event.events;

import com.ejo.glowui.Window;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowlib.event.EventE;

public class RenderEvent extends EventE {

    Window window;
    Scene scene;

    @Override
    public void post(Object... args) {
        this.window = (Window) args[0];
        this.scene = (Scene) args[1];
        super.post(args);
    }

    public Window getWindow() {
        return window;
    }

    public Scene getScene() {
        return scene;
    }

}
