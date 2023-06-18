package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.event.EventRegistry;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import com.ejo.glowui.scene.elements.TextUI;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.DrawUtil;
import org.util.glowlib.event.EventAction;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.time.StopWatch;

import java.awt.*;

public abstract class WidgetUI extends ElementUI {

    private String title;
    private final TextUI displayText;

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
            hoverFade = (int)DrawUtil.getNextFade(isMouseOver(),hoverFade,0,75,2f);
            hoverWatch.restart();
        }
    });

    public WidgetUI(Scene scene, String title, Vector pos, Vector size, boolean shouldRender, boolean shouldTick, Runnable action) {
        super(scene, pos, shouldRender,shouldTick);
        this.title = title;
        this.displayText = new TextUI(scene,title,new Font("Arial",Font.PLAIN,(int)size.getY()),pos,ColorE.WHITE);

        this.action = action;
        this.size = size;
        baseRect = new RectangleUI(scene,Vector.NULL,Vector.NULL,new ColorE(0,0,0,0));
        onMaintenance.subscribe();
    }

    /**
     * Always make sure when creating a child class of an interactable that you re-instantiate the baseRect with the stats of your
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


    public void removeTitle() {
        setTitle("");
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSize(Vector size) {
        this.size = size;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }


    public boolean hasTitle() {
        return !getTitle().equals("");
    }

    public String getTitle() {
        return title;
    }

    public Vector getSize() {
        return size;
    }

    public Runnable getAction() {
        return action;
    }

    public TextUI getDisplayText() {
        return displayText;
    }

    public RectangleUI getBaseRect() {
        return baseRect;
    }

}