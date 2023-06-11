package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.scene.Scene;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.Container;
import org.util.glowlib.setting.SettingUI;

//TODO: INCOMPLETE PLACEHOLDER
public class ModeCycleUI<T> extends WidgetUI {

    public ModeCycleUI(Scene scene, Container<T> container, String name, Vector pos, Vector size, T... modes) {
        super(scene, name,pos, size, true, true,null);
    }

    public ModeCycleUI(Scene scene, SettingUI<T> settingUI, Vector pos, Vector size) {
        this(scene,settingUI,settingUI.getName(),pos,size, settingUI.getModes());
    }

    @Override
    protected void drawWidget() {

    }

}
