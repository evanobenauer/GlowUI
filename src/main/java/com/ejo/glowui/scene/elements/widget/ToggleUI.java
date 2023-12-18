package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowlib.setting.Container;
import com.ejo.glowui.event.EventRegistry;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.util.UIUtil;
import com.ejo.glowui.util.Mouse;
import com.ejo.glowui.util.render.QuickDraw;
import com.ejo.glowlib.event.EventAction;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.time.StopWatch;

public class ToggleUI extends SettingWidget<Boolean> {

    private ColorE color;

    private final StopWatch fadeWatch = new StopWatch();
    private float toggleFade = 0;

    public EventAction toggleAnimation = new EventAction(EventRegistry.EVENT_RUN_MAINTENANCE, () -> {
        fadeWatch.start();
        if (fadeWatch.hasTimePassedMS(1)) {
            toggleFade = (int) UIUtil.getNextAnimationValue(getContainer().get(), toggleFade,0,150,2f);
            fadeWatch.restart();
        }
    });

    public ToggleUI(String title, Vector pos, Vector size, ColorE color, Container<Boolean> container) {
        super(title, pos, size, true, true,container);
        updateValueFromContainer();
        this.color = color;

        toggleAnimation.subscribe();
    }

    public ToggleUI(Vector pos, Vector size, ColorE color, Container<Boolean> container) {
        this("",pos,size,color,container);
    }

    
    @Override
    protected void drawWidget(Scene scene, Vector mousePos) {
        QuickDraw.drawRect(getPos(),getSize(), UIUtil.WIDGET_BACKGROUND);
        QuickDraw.drawRect(getPos(), getSize(), new ColorE(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), (int) toggleFade));

        double border = 4;

        //Draw Text
        int size = (int)getSize().getY();
        setUpDisplayText(getTitle(),border,size);
        getDisplayText().drawCentered(scene, mousePos, getSize().getAdded(-border*2,-border*2).getAdded(-getSize().getX()/50,0));
    }

    @Override
    public void onKeyPress(Scene scene, int key, int scancode, int action, int mods) {
    }

    @Override
    public void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos) {
        if (isMouseOver()) {
            if (button == Mouse.BUTTON_LEFT.getId() && action == Mouse.ACTION_RELEASE) {
                this.value = !value;
                getAction().run();
            }
        }
    }


    public ToggleUI setColor(ColorE color) {
        this.color = color;
        return this;
    }


    public ColorE getColor() {
        return color;
    }


}
