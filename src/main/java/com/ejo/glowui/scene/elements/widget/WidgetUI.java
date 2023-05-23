package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.event.EventRegistry;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.DrawUtil;
import org.util.glowlib.event.EventAction;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.time.StopWatch;

//TODO: for all widgets, add an option to display the text or not
public abstract class WidgetUI extends ElementUI {

    private RectangleUI baseRect;
    private Vector size;
    private Runnable action;

    private final StopWatch hoverWatch = new StopWatch();
    protected int hoverFade = 0;

    /**
     * This EventAction injects into the Window Maintenance to consistently update the hover fade rectangle. Whenever an
     * intractable is hovered over, it will gain a slight highlight that fades in upon mouse hover which indicates that it
     * can be clicked
     */
    public EventAction onMaintenance = new EventAction(EventRegistry.EVENT_RUN_MAINTENANCE, () -> {
        hoverWatch.start();
        if (hoverWatch.hasTimePassedMS(1)) {
            hoverFade = (int)DrawUtil.getNextFade(isMouseOver(),hoverFade,0,75,1.75f);
            hoverWatch.restart();
        }
    });

    public WidgetUI(Scene scene, Vector pos, Vector size, boolean shouldRender, boolean shouldTick, Runnable action) {
        super(scene, pos, shouldRender,shouldTick);
        this.action = action;
        this.size = size;
        baseRect = new RectangleUI(scene,Vector.NULL,Vector.NULL,new ColorE(0,0,0,0));
        onMaintenance.subscribe();
    }

    /**
     * Always make sure when creating a child class of an interactable that you reinstantiate the baseRect with the stats of your
     * interactable inside the draw method
     */
    @Override
    public void draw() {
        super.draw();
        drawWidget();
        new RectangleUI(getScene(), getBaseRect().getPos(), getBaseRect().getSize(), new ColorE(255, 255, 255, hoverFade)).draw();
    }

    protected abstract void drawWidget();

    @Override
    public void tick() {
        if (!shouldTick()) return;
        baseRect = new RectangleUI(getScene(),getPos(),getSize(),new ColorE(0,0,0,0));
        super.tick();
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return baseRect.updateMouseOver(mousePos);
    }


    public void setSize(Vector size) {
        this.size = size;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }


    public Vector getSize() {
        return size;
    }

    public Runnable getAction() {
        return action;
    }

    public RectangleUI getBaseRect() {
        return baseRect;
    }

}