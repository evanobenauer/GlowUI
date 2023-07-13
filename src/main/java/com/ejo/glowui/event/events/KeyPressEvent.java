package com.ejo.glowui.event.events;

import com.ejo.glowlib.event.EventE;

public class KeyPressEvent extends EventE {

    private long window;
    private int key;
    private int scancode;
    private int action;
    private int mods;

    @Override
    public void post(Object... args) {
        this.window = (long)args[0];
        this.key = (int)args[1];
        this.scancode = (int)args[2];
        this.action = (int)args[3];
        this.mods = (int)args[4];
        super.post(args);
    }

    public long getWindow() {
        return window;
    }

    public int getKey() {
        return key;
    }

    public int getScancode() {
        return scancode;
    }

    public int getAction() {
        return action;
    }

    public int getMods() {
        return mods;
    }

}
