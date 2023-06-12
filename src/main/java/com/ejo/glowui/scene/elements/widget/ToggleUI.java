package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.event.EventRegistry;
import com.ejo.glowui.event.events.MouseClickEvent;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.DrawUtil;
import com.ejo.glowui.util.Mouse;
import org.util.glowlib.event.EventAction;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.misc.Container;
import org.util.glowlib.time.StopWatch;

public class ToggleUI extends WidgetUI {

    private float toggleFade = 0;
    private final Container<Boolean> container;

    private final StopWatch hoverWatch = new StopWatch();

    public EventAction onMaintenance = new EventAction(EventRegistry.EVENT_RUN_MAINTENANCE, () -> {
        hoverWatch.start();
        if (hoverWatch.hasTimePassedMS(1)) {
            toggleFade = (int) DrawUtil.getNextFade(getContainer().get(),toggleFade,0,150,1.75f);
            hoverWatch.restart();
        }
    });

    public ToggleUI(Scene scene, Container<Boolean> container, String title, Vector pos, Vector size) {
        super(scene, title, pos, size, true, true,null);
        this.container = container;

        setAction(() -> container.set(!container.get()));
        onMaintenance.subscribe();
    }

    public ToggleUI(Scene scene, Container<Boolean> container, Vector pos, Vector size) {
        this(scene,container,"",pos,size);
    }

    @Override
    protected void drawWidget() {
        new RectangleUI(getScene(),getPos(), getSize(), new ColorE(50, 50, 50, 200)).draw();
        new RectangleUI(getScene(), getPos(), getSize(), new ColorE(0, 125, 200, (int) toggleFade)).draw();
        //RenderUtils.drawText(stack, getSetting().getKey(), getPos().getAdded(new Vector(2, getSize().getY() / 2 - RenderUtils.getStringHeight(Minecraft.getInstance().font) / 2)), ColorE.WHITE);
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

    public Container<Boolean> getContainer() {
        return container;
    }
}
