package com.ejo.glowui.util.render;

import java.awt.*;

public class Fonts {

    public static Font DEFAULT_ARIAL = new Font("Arial",Font.PLAIN,12);

    public static Font getDefaultFont(int size) {
        return new Font(DEFAULT_ARIAL.getFontName(),DEFAULT_ARIAL.getStyle(),size);
    }
}
