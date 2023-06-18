package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.event.EventRegistry;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.TextUI;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.DrawUtil;
import com.ejo.glowui.util.Mouse;
import com.ejo.glowui.util.QuickDraw;
import org.util.glowlib.event.EventAction;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.misc.Container;
import org.util.glowlib.time.StopWatch;

import java.awt.*;

public class ToggleUI extends WidgetUI {

    private final Container<Boolean> container;

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

    public ToggleUI(Scene scene, String title, Vector pos, Vector size, ColorE color, Container<Boolean> container) {
        super(scene, title, pos, size, true, true,null);
        this.container = container;
        this.color = color;

        setAction(() -> container.set(!container.get()));
        onMaintenance.subscribe();
    }

    public ToggleUI(Scene scene, Vector pos, Vector size, ColorE color, Container<Boolean> container) {
        this(scene,"",pos,size,color,container);
    }

    
    @Override
    protected void drawWidget() {
        new RectangleUI(getScene(),getPos(), getSize(), DrawUtil.WIDGET_BACKGROUND).draw();
        new RectangleUI(getScene(), getPos(), getSize(), new ColorE(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), (int) toggleFade)).draw();

        //Draw Text
        getDisplayText().setText(getTitle());
        getDisplayText().setSize((int) getSize().getY());
        getDisplayText().setPos(getPos());
        getDisplayText().drawCentered(getSize());
    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        super.onMouseClick(button, action, mods, mousePos);
        if (isMouseOver()) {
            if (button == Mouse.BUTTON_LEFT.getId() && action == Mouse.ACTION_RELEASE) {
                getAction().run();
            }
        }
    }


    public void setColor(ColorE color) {
        this.color = color;
    }


    public ColorE getColor() {
        return color;
    }

    public Container<Boolean> getContainer() {
        return container;
    }

}
