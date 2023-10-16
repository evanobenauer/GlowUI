package com.ejo.glowui.scene.elements;

import com.ejo.glowlib.event.EventAction;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.time.StopWatch;
import com.ejo.glowui.event.EventRegistry;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.widget.ButtonUI;
import com.ejo.glowui.util.Util;
import com.ejo.glowui.util.render.Fonts;
import com.ejo.glowui.util.render.GLManager;
import com.ejo.glowui.util.render.QuickDraw;

import java.util.ArrayList;
import java.util.Arrays;

public class SideBarUI extends ElementUI {

    private final ArrayList<ElementUI> elementList = new ArrayList<>();

    private final ButtonUI buttonUI;

    private String title;
    private Type type;
    private double width;
    private boolean open;
    private ColorE color;
    
    private final StopWatch openWatch = new StopWatch();
    protected int openPercent = 0;

    public EventAction openAnimation = new EventAction(EventRegistry.EVENT_RUN_MAINTENANCE, () -> {
        openWatch.start();
        if (openWatch.hasTimePassedMS(1)) {
            openPercent = (int) Util.getNextAnimationValue(isOpen(), openPercent, 0, 100, 2f);
            openWatch.restart();
        }
    });

    public SideBarUI(String title, Type type, double width, boolean open, ColorE color, ElementUI... elements) {
        super(Vector.NULL, true, true);
        this.title = title;
        this.type = type;
        this.width = width;
        this.color = color;

        setPos(Vector.NULL);

        this.open = open;

        this.buttonUI = new ButtonUI(Vector.NULL,Vector.NULL,getColor(), ButtonUI.MouseButton.LEFT,() -> setOpen(!isOpen()));

        addElements(elements);
        openAnimation.subscribe();
    }

    public SideBarUI(Type type, double width, boolean open, ColorE color,  ElementUI... elements) {
        this("",type,width,open,color,elements);
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        //Sets the position of the sidebar
        setPos(switch(getType()) {
            case TOP -> new Vector(0,0 - getWidth() * (100 - openPercent)/100);
            case LEFT -> new Vector(0 - getWidth()  * (100 - openPercent)/100,0);
            case BOTTOM -> new Vector(0, scene.getSize().getY() - getWidth()  * openPercent/100);
            case RIGHT -> new Vector(scene.getSize().getX() - getWidth() * openPercent/100,0);
        });

        updateButton(scene);
        buttonUI.draw(scene, mousePos);

        //Draw Background and Button Lines
        double border = 2;
        double size = 2;
        switch(getType()) {
            case TOP, BOTTOM -> {
                //Button Lines
                if (getButton().shouldRender()) {
                    QuickDraw.drawRect(getButton().getPos().getAdded(border, border * 2), new Vector(getButton().getSize().getX() - 2 * border, size), ColorE.WHITE);
                    QuickDraw.drawRect(getButton().getPos().getAdded(border, getButton().getSize().getY() / 2 - size / 2), new Vector(getButton().getSize().getX() - 2 * border, size), ColorE.WHITE);
                    QuickDraw.drawRect(getButton().getPos().getAdded(border, getButton().getSize().getY() - 2 * border - size), new Vector(getButton().getSize().getX() - 2 * border, size), ColorE.WHITE);
                }
                //Background
                QuickDraw.drawRect(getPos(),new Vector(scene.getSize().getX(),getWidth()),getColor());
                //Title
                GLManager.translate(getPos());
                QuickDraw.drawTextCentered(getTitle(), Fonts.getDefaultFont(20),new Vector(0,14),new Vector(scene.getSize().getX(),0),ColorE.WHITE);
                GLManager.translate(getPos().getMultiplied(-1));
            }
            case LEFT, RIGHT -> {
                //Button Lines
                if (getButton().shouldRender()) {
                    QuickDraw.drawRect(getButton().getPos().getAdded(border * 2, border), new Vector(size, getButton().getSize().getY() - 2 * border), ColorE.WHITE);
                    QuickDraw.drawRect(getButton().getPos().getAdded(getButton().getSize().getX() / 2 - size / 2, border), new Vector(size, getButton().getSize().getY() - 2 * border), ColorE.WHITE);
                    QuickDraw.drawRect(getButton().getPos().getAdded(getButton().getSize().getX() - 2 * border - size, border), new Vector(size, getButton().getSize().getY() - 2 * border), ColorE.WHITE);
                }
                //Background
                QuickDraw.drawRect(getPos(),new Vector(getWidth(),scene.getSize().getY()),getColor());
                //Title
                GLManager.translate(getPos());
                QuickDraw.drawTextCentered(getTitle(),Fonts.getDefaultFont(20),new Vector(0,14),new Vector(getWidth(),0),ColorE.WHITE);
                GLManager.translate(getPos().getMultiplied(-1));
            }
        }

        //Draw Elements
        GLManager.translate(getPos());
        for (ElementUI elementUI : getElementList()) {
            elementUI.draw(scene, mousePos.getAdded(getPos().getMultiplied(-1)));
        }
        GLManager.translate(getPos().getMultiplied(-1));
    }

    @Override
    public void tickElement(Scene scene, Vector mousePos) {
        this.buttonUI.tick(scene, mousePos);

        //Tick Elements
        for (ElementUI elementUI : getElementList()) {
            elementUI.tick(scene, mousePos.getAdded(getPos().getMultiplied(-1)));
        }
    }

    @Override
    public void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos) {
        this.buttonUI.onMouseClick(scene, button,action,mods,mousePos);

        for (ElementUI elementUI : getElementList()) {
            elementUI.onMouseClick(scene, button, action, mods, mousePos.getAdded(getPos().getMultiplied(-1)));
        }
    }

    @Override
    public void onKeyPress(Scene scene, int key, int scancode, int action, int mods) {
        for (ElementUI elementUI : getElementList()) {
            elementUI.onKeyPress(scene, key, scancode, action, mods);
        }
    }

    public void updateButton(Scene scene) {
        int size1 = 25;
        int size2 = 120;
        switch (getType()) {
            case TOP -> {
                buttonUI.setPos(getPos().getAdded(new Vector(scene.getSize().getX()/2 - getButton().getSize().getX()/2,getWidth())));
                buttonUI.setSize(new Vector(size2,size1));
            }
            case BOTTOM -> {
                buttonUI.setPos(getPos().getAdded(new Vector(scene.getSize().getX()/2 - getButton().getSize().getX()/2,-getButton().getSize().getY())));
                buttonUI.setSize(new Vector(size2,size1));
            }
            case LEFT -> {
                buttonUI.setPos(getPos().getAdded(new Vector(getWidth(),scene.getSize().getY()/2 - getButton().getSize().getY()/2)));
                buttonUI.setSize(new Vector(size1,size2));
            }
            case RIGHT -> {
                buttonUI.setPos(getPos().getAdded(new Vector(-getButton().getSize().getX(),scene.getSize().getY()/2 - getButton().getSize().getY()/2)));
                buttonUI.setSize(new Vector(size1,size2));
            }
        }
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        Vector screenSize = Vector.NULL; //TODO: Incomplete. Get the screen size
        switch (getType()) {
            case TOP,BOTTOM -> {
                boolean mouseOverX = mousePos.getX() >= getPos().getX() && mousePos.getX() <= getPos().getX() + screenSize.getX();
                boolean mouseOverY = mousePos.getY() >= getPos().getY() && mousePos.getY() <= getPos().getY() + getWidth();
                return mouseOver = mouseOverX && mouseOverY;
            }
            case LEFT,RIGHT -> {
                boolean mouseOverX = mousePos.getX() >= getPos().getX() && mousePos.getX() <= getPos().getX() + getWidth();
                boolean mouseOverY = mousePos.getY() >= getPos().getY() && mousePos.getY() <= getPos().getY() + screenSize.getY();
                return mouseOver = mouseOverX && mouseOverY;
            }
            default -> {
                return false;
            }
        }
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setColor(ColorE color) {
        this.color = color;
    }


    public String getTitle() {
        return title;
    }

    public Type getType() {
        return type;
    }

    public double getWidth() {
        return width;
    }

    public boolean isOpen() {
        return open;
    }

    public ColorE getColor() {
        return color;
    }

    public ButtonUI getButton() {
        return buttonUI;
    }


    private void addElements(ElementUI... elementUI) {
        elementList.addAll(Arrays.asList(elementUI));
    }

    public ArrayList<ElementUI> getElementList() {
        return elementList;
    }


    public enum Type {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

}
