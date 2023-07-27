package com.ejo.glowui.util;

import java.awt.*;

//TODO: List usable fonts
public class Fonts {

    public static Font DEFAULT_ARIAL = new Font("Arial",Font.PLAIN,12);



    public static Font getDefaultFont(int size) {
        return new Font(DEFAULT_ARIAL.getFontName(),DEFAULT_ARIAL.getStyle(),size);
    }
}
