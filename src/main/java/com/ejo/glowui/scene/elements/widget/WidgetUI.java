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
import com.ejo.glowui.util.Fonts;

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
            hoverFade = (int)DrawUtil.getNextAnimationValue(isMouseOver(),hoverFade,0,75,2f);
            hoverWatch.restart();
        }
    });

    public WidgetUI(String title, Vector pos, Vector size, boolean shouldRender, boolean shouldTick, Runnable action) {
        super(pos, shouldRender,shouldTick);
        this.title = title;
        this.displayText = new TextUI(title, Fonts.getDefaultFont((int)size.getY()),pos,ColorE.WHITE);

        this.action = action;
        this.size = size;
        baseRect = new RectangleUI(Vector.NULL,Vector.NULL,new ColorE(0,0,0,0));
        onMaintenance.subscribe();
    }


    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        drawWidget(scene, mousePos);
        new RectangleUI(getBaseRect().getPos(), getBaseRect().getSize(), new ColorE(255, 255, 255, hoverFade)).draw(scene, mousePos);
    }

    /**
     * The DrawWidget method is the abstract method in which all widgets must draw their contents in. The method is called in drawElement for the widget
     * Make sure when drawing widgets, do NOT override drawElement() or draw()
     * @param scene
     * @param mousePos
     */
    protected abstract void drawWidget(Scene scene, Vector mousePos);

    @Override
    public void tickElement(Scene scene, Vector mousePos) {
        baseRect = new RectangleUI(getPos(),getSize(),new ColorE(0,0,0,0));
        tickWidget(scene,mousePos);
    }

    /**
     * The TickWidget method is the abstract method in which all widgets must place their tick contents in. The method is called in the tickElement method.
     * Make sure when ticking widgets, do NOT override tickElement() or tick()
     * @param scene
     * @param mousePos
     */
    protected abstract void tickWidget(Scene scene, Vector mousePos);

    @Override
    public abstract void onKeyPress(Scene scene, int key, int scancode, int action, int mods);

    @Override
    public abstract void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos);

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return baseRect.updateMouseOver(mousePos);
    }

    /**
     * Sets up the default widget display text. This text is a scaled top left oriented text. Position must be set after this method is called
     * @param text
     * @param border
     * @param textSize
     */
    public void setUpDisplayText(String text, double border, int textSize) {
        getDisplayText().setText(text);
        getDisplayText().setSize(textSize);
        double scaleValue = 1;
        getDisplayText().setScale(scaleValue);
        if (getDisplayText().getWidth() > getSize().getX() - border * 2) {
            scaleValue = (getSize().getX() - border*2) / getDisplayText().getWidth();
        }
        if (getDisplayText().getHeight() > (getSize().getY())) {
            double scaleValueHeight = (getSize().getY()) / getDisplayText().getHeight();
            if (scaleValueHeight < scaleValue && scaleValue != 1) scaleValue = scaleValueHeight;
        }
        getDisplayText().setScale(scaleValue);
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