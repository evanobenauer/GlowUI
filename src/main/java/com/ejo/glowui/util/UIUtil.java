package com.ejo.glowui.util;

import org.lwjgl.glfw.GLFW;

public class UIUtil {

    public static void forceEconRenderFrame() {
        GLFW.glfwPostEmptyEvent();
    }

}
