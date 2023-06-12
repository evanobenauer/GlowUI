package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.scene.Scene;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.Container;

//TODO: INCOMPLETE PLACEHOLDER
public class ModeCycleUI<T> extends WidgetUI {

    @SafeVarargs
    public ModeCycleUI(Scene scene, Container<T> container, String title, Vector pos, Vector size, T... modes) {
        super(scene, title,pos, size, true, true,null);
    }


    @Override
    protected void drawWidget() {

    }

}
