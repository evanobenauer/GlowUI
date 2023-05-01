package com.ejo.glowui.scene.elements.interactable;

import com.ejo.glowui.event.EventRegistry;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import org.util.glowlib.event.EventAction;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.time.StopWatch;

public abstract class InteractableUI extends ElementUI {

    protected RectangleUI baseRect;

    private Runnable action;

    private final StopWatch hoverWatch = new StopWatch();

    int hoverFade = 0;

    public InteractableUI(Scene scene, Vector pos, boolean shouldRender, Runnable action) {
        super(scene, pos, shouldRender);
        this.action = action;
        baseRect = new RectangleUI(scene,Vector.NULL,Vector.NULL,new ColorE(0,0,0,0));
        onMaintenance.subscribe();
    }

    /**
     * This EventAction injects into the Window Maintenance to consistently update the hover fade rectangle. Whenever an
     * intractable is hovered over, it will gain a slight highlight that fades in upon mouse hover which indicates that it
     * can be clicked
     */
    public EventAction onMaintenance = new EventAction(EventRegistry.EVENT_RUN_MAINTENANCE, () -> {
        hoverWatch.start();
        if (hoverWatch.hasTimePassedMS(1)) {
            if (getBaseRect().isMouseOver()) {
                if (hoverFade < 255) hoverFade++;
            } else {
                if (hoverFade > 0) hoverFade--;
            }
            hoverWatch.restart();
        }
    });

    /**
     * Always make sure when creating a child class of an interactable that you reinstantiate the baseRect with the stats of your
     * interactable inside of the draw method
     */
    @Override
    public void draw() {
        super.draw();
        new RectangleUI(getScene(), getBaseRect().getPos(), getBaseRect().getSize(), new ColorE(255, 255, 255, hoverFade)).draw();
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return baseRect.updateMouseOver(mousePos);
    }

    public void setAction(Runnable action) {
        this.action = action;
    }

    public Runnable getAction() {
        return action;
    }

    public RectangleUI getBaseRect() {
        return baseRect;
    }

}