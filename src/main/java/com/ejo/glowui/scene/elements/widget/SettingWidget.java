package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.setting.Container;
import com.ejo.glowui.scene.Scene;

public abstract class SettingWidget<T> extends WidgetUI {

    protected T value;
    private Container<T> container;

    public SettingWidget(String title, Vector pos, Vector size, boolean shouldRender, boolean shouldTick, Container<T> container) {
        super(title, pos, size, shouldRender, shouldTick, null);
        this.container = container;
        setAction(() -> getContainer().set(value));
    }

    @Override
    protected void tickWidget(Scene scene, Vector mousePos) {
        this.value = getContainer().get();
    }


    public SettingWidget<T> setContainer(Container<T> container) {
        this.container = container;
        return this;
    }

    public Container<T> getContainer() {
        return container;
    }

}
