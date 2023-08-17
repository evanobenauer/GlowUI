package com.ejo.glowui.util;

import com.ejo.glowlib.math.Vector;
import com.ejo.glowui.event.EventRegistry;
import org.lwjgl.glfw.GLFW;
import com.ejo.glowlib.event.EventAction;

import java.util.HashMap;

/**
 * This class has the purpose of utilizing an immediate keyDown detection. I don't know if I want to keep it, but it does work
 */
public class Mouse {

    public static HashMap<Integer, Mouse> buttonList = new HashMap<>();

    public static final Vector NULL_POS = new Vector(-1,-1);

    public static int ACTION_CLICK = 1;
    public static int ACTION_RELEASE = 0;

    public static Mouse BUTTON_LEFT = new Mouse(0);
    public static Mouse BUTTON_RIGHT = new Mouse(1);
    public static Mouse BUTTON_MIDDLE = new Mouse(2);


    public static EventAction onMouse = new EventAction(EventRegistry.EVENT_MOUSE_CLICK, true, () -> {
        int button = EventRegistry.EVENT_MOUSE_CLICK.getButton();
        int action = EventRegistry.EVENT_MOUSE_CLICK.getAction();
        int mods = EventRegistry.EVENT_MOUSE_CLICK.getMods();
        try {
            buttonList.get(button).update(button, action, mods);
        } catch (NullPointerException e) {
            System.out.println("GLOWUI: Mouse Button Object Not Registered!");
        }
    });

    //------------------------------------------

    private final int id;
    private boolean isButtonDown;

    public Mouse(int id) {
        this.id = id;
        buttonList.put(id,this);
    }

    public void update(int key, int action, int mods) {
        if (getId() == key) {
            if (action == GLFW.GLFW_PRESS) setButtonDown(true);
            if (action == GLFW.GLFW_RELEASE) setButtonDown(false);
        }
    }

    public void setButtonDown(boolean buttonDown) {
        isButtonDown = buttonDown;
    }

    public boolean isButtonDown() {
        return isButtonDown;
    }

    public int getId() {
        return id;
    }

}
