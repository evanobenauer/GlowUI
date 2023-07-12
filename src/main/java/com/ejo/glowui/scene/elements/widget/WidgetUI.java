package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.event.EventRegistry;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import com.ejo.glowui.scene.elements.TextUI;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.DrawUtil;
import com.ejo.glowlib.event.EventAction;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.time.StopWatch;

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

    public WidgetUI(String title, Vector pos, Vector size, boolean shouldRender, boolean shouldTick, Runnable action) {
        super(pos, shouldRender,shouldTick);
        this.title = title;
        this.displayText = new TextUI(title,new Font("Arial",Font.PLAIN,(int)size.getY()),pos,ColorE.WHITE);

        this.action = action;
        this.size = size;
        baseRect = new RectangleUI(Vector.NULL,Vector.NULL,new ColorE(0,0,0,0));
        onMaintenance.subscribe();
    }

    /**
     * Always make sure when creating a child class of an interactable that you re-instantiate the baseRect with the stats of your
     * interactable inside the draw method
     */
    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        drawWidget(scene, mousePos);
        new RectangleUI(getBaseRect().getPos(), getBaseRect().getSize(), new ColorE(255, 255, 255, hoverFade)).draw(scene, mousePos);
    }

    protected abstract void drawWidget(Scene scene, Vector mousePos);

    @Override
    public void tickElement(Scene scene, Vector mousePos) {
        baseRect = new RectangleUI(getPos(),getSize(),new ColorE(0,0,0,0));
        tickWidget(scene,mousePos);
    }

    protected abstract void tickWidget(Scene scene, Vector mousePos);

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return baseRect.updateMouseOver(mousePos);
    }

    public void setUpDisplayText(String text, double border, int textSize) {
        getDisplayText().setText(text);
        getDisplayText().setSize(textSize);
        getDisplayText().setScale(1);
        if (getDisplayText().getWidth() > getSize().getX() - border * 2) {
            getDisplayText().setScale((getSize().getX() - border*2) / getDisplayText().getWidth());
        }
        getDisplayText().setPos(getPos().getAdded(border,border));
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