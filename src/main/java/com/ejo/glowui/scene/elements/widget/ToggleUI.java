package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.event.EventRegistry;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.TextUI;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.DrawUtil;
import com.ejo.glowui.util.Mouse;
import com.ejo.glowui.util.QuickDraw;
import com.ejo.glowlib.event.EventAction;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.misc.Container;
import com.ejo.glowlib.time.StopWatch;

import java.awt.*;

public class ToggleUI extends WidgetUI {

    private Container<Boolean> container;

    private ColorE color;

    private final StopWatch hoverWatch = new StopWatch();
    private float toggleFade = 0;

    public EventAction onMaintenance = new EventAction(EventRegistry.EVENT_RUN_MAINTENANCE, () -> {
        hoverWatch.start();
        if (hoverWatch.hasTimePassedMS(1)) {
            toggleFade = (int) DrawUtil.getNextFade(getContainer().get(),toggleFade,0,150,2f);
            hoverWatch.restart();
        }
    });

    public ToggleUI(String title, Vector pos, Vector size, ColorE color, Container<Boolean> container) {
        super(title, pos, size, true, true,null);
        this.container = container;
        this.color = color;

        setAction(() -> container.set(!container.get()));
        onMaintenance.subscribe();
    }

    public ToggleUI(Vector pos, Vector size, ColorE color, Container<Boolean> container) {
        this("",pos,size,color,container);
    }

    
    @Override
    protected void drawWidget(Scene scene, Vector mousePos) {
        QuickDraw.drawRect(getPos(),getSize(),DrawUtil.WIDGET_BACKGROUND);
        QuickDraw.drawRect(getPos(), getSize(), new ColorE(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), (int) toggleFade));

        double border = getSize().getY()/5;

        //Draw Text
        int size = (int)getSize().getY();
        setUpDisplayText(getTitle(),border,size);
        getDisplayText().drawCentered(scene, mousePos, getSize().getAdded(-border*2,-border*2).getAdded(-getSize().getX()/50,0));
    }

    @Override
    protected void tickWidget(Scene scene, Vector mousePos) {

    }

    @Override
    public void onKeyPress(Scene scene, int key, int scancode, int action, int mods) {
    }

    @Override
    public void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos) {
        if (isMouseOver()) {
            if (button == Mouse.BUTTON_LEFT.getId() && action == Mouse.ACTION_RELEASE) {
                getAction().run();
            }
        }
    }


    public void setColor(ColorE color) {
        this.color = color;
    }

    public void setContainer(Container<Boolean> container) {
        this.container = container;
    }


    public ColorE getColor() {
        return color;
    }

    public Container<Boolean> getContainer() {
        return container;
    }

}
