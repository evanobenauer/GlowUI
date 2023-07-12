package com.ejo.glowui.scene.elements;

import com.ejo.glowlib.event.EventAction;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.time.StopWatch;
import com.ejo.glowui.event.EventRegistry;
import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.widget.ButtonUI;
import com.ejo.glowui.util.DrawUtil;
import com.ejo.glowui.util.GLManager;
import com.ejo.glowui.util.QuickDraw;
import org.lwjgl.opengl.GL11;

import java.awt.*;
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

    private Vector barPos;

    private final StopWatch stopWatch = new StopWatch();
    protected int openPercent = 0;

    public EventAction onMaintenance = new EventAction(EventRegistry.EVENT_RUN_MAINTENANCE, () -> {
        stopWatch.start();
        if (stopWatch.hasTimePassedMS(1)) {
            openPercent = (int) DrawUtil.getNextFade(isOpen(), openPercent,0,100,2f);
            stopWatch.restart();
        }
    });

    public SideBarUI(String title, Type type, double width, boolean open, ColorE color, ElementUI... elements) {
        super(Vector.NULL, true, true);
        this.title = title;
        this.type = type;
        this.width = width;
        this.color = color;

        this.barPos = Vector.NULL;

        this.open = open; //Maybe set default open

        this.buttonUI = new ButtonUI(Vector.NULL,Vector.NULL,getColor(),() -> setOpen(!isOpen()));

        addElements(elements);
        onMaintenance.subscribe();
    }

    public SideBarUI(Type type, double width, boolean open, ColorE color,  ElementUI... elements) {
        this("",type,width,open,color,elements);
    }

    @Override
    public void draw(Scene scene, Vector mousePos) {
        //Sets the position of the sidebar
        setBarPos(switch(getType()) {
            case TOP -> new Vector(0,0 - getWidth() * (100 - openPercent)/100);
            case LEFT -> new Vector(0 - getWidth()  * (100 - openPercent)/100,0);
            case BOTTOM -> new Vector(0, scene.getSize().getY() - getWidth()  * openPercent/100);
            case RIGHT -> new Vector(scene.getSize().getX() - getWidth() * openPercent/100,0);
        });

        super.draw(scene, mousePos);

        updateButton(scene);
        buttonUI.draw(scene, mousePos);

        //Draw Background and Button Lines
        double border = 2;
        double size = 2;
        switch(getType()) {
            case TOP, BOTTOM -> {
                //Button Lines
                QuickDraw.drawRect(getButtonPos().getAdded(border,border*2),new Vector(getButtonSize().getX() - 2*border,size),ColorE.WHITE);
                QuickDraw.drawRect(getButtonPos().getAdded(border,getButtonSize().getY()/2 - size / 2),new Vector(getButtonSize().getX() - 2*border,size),ColorE.WHITE);
                QuickDraw.drawRect(getButtonPos().getAdded(border,getButtonSize().getY() - 2*border - size),new Vector(getButtonSize().getX() - 2*border,size),ColorE.WHITE);
                //Background
                QuickDraw.drawRect(getBarPos(),new Vector(scene.getSize().getX(),getWidth()),getColor());
                //Title
                GLManager.translate(getBarPos());
                QuickDraw.drawTextCentered(getTitle(),new Font("Arial",Font.PLAIN,20),new Vector(0,14),new Vector(scene.getSize().getX(),0),ColorE.WHITE);
                GLManager.translate(getBarPos().getMultiplied(-1));
            }
            case LEFT, RIGHT -> {
                //Button Lines
                QuickDraw.drawRect(getButtonPos().getAdded(border*2,border),new Vector(size,getButtonSize().getY() - 2*border),ColorE.WHITE);
                QuickDraw.drawRect(getButtonPos().getAdded(getButtonSize().getX()/2 - size / 2,border),new Vector(size,getButtonSize().getY() - 2*border),ColorE.WHITE);
                QuickDraw.drawRect(getButtonPos().getAdded(getButtonSize().getX() - 2*border - size,border),new Vector(size,getButtonSize().getY() - 2*border),ColorE.WHITE);
                //Background
                QuickDraw.drawRect(getBarPos(),new Vector(getWidth(),scene.getSize().getY()),getColor());
                //Title
                GLManager.translate(getBarPos());
                QuickDraw.drawTextCentered(getTitle(),new Font("Arial",Font.PLAIN,20),new Vector(0,14),new Vector(getWidth(),0),ColorE.WHITE);
                GLManager.translate(getBarPos().getMultiplied(-1));
            }
        }

        //Draw Elements
        GLManager.translate(getBarPos());
        for (ElementUI elementUI : getElementList()) {
            elementUI.draw(scene, mousePos.getAdded(getBarPos().getMultiplied(-1)));
        }
        GLManager.translate(getBarPos().getMultiplied(-1));
    }

    @Override
    public void tick(Scene scene, Vector mousePos) {
        super.tick(scene, mousePos);
        this.buttonUI.tick(scene, mousePos);

        //Tick Elements
        for (ElementUI elementUI : getElementList()) {
            elementUI.tick(scene, mousePos.getAdded(getBarPos().getMultiplied(-1)));
        }
    }

    @Override
    public void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos) {
        super.onMouseClick(scene, button, action, mods, mousePos);
        this.buttonUI.onMouseClick(scene, button,action,mods,mousePos);

        for (ElementUI elementUI : getElementList()) {
            elementUI.onMouseClick(scene, button, action, mods, mousePos.getAdded(getBarPos().getMultiplied(-1)));
        }
    }

    @Override
    public void onKeyPress(Scene scene, int key, int scancode, int action, int mods) {
        super.onKeyPress(scene, key, scancode, action, mods);

        for (ElementUI elementUI : getElementList()) {
            elementUI.onKeyPress(scene, key, scancode, action, mods);
        }
    }

    public void updateButton(Scene scene) {
        int height = 25;
        int length = 120;
        switch (getType()) {
            case TOP -> {
                buttonUI.setPos(getBarPos().getAdded(new Vector(scene.getSize().getX()/2 - getButtonSize().getX()/2,getWidth())));
                buttonUI.setSize(new Vector(length,height));
            }
            case BOTTOM -> {
                buttonUI.setPos(getBarPos().getAdded(new Vector(scene.getSize().getX()/2 - getButtonSize().getX()/2,-getButtonSize().getY())));
                buttonUI.setSize(new Vector(length,height));
            }
            case LEFT -> {
                buttonUI.setPos(getBarPos().getAdded(new Vector(getWidth(),scene.getSize().getY()/2 - getButtonSize().getY()/2)));
                buttonUI.setSize(new Vector(height,length));
            }
            case RIGHT -> {
                buttonUI.setPos(getBarPos().getAdded(new Vector(-getButtonSize().getX(),scene.getSize().getY()/2 - getButtonSize().getY()/2)));
                buttonUI.setSize(new Vector(height,length));
            }
        }
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return false;
    }


    public Vector setButtonPos(Vector pos) {
        return this.buttonUI.setPos(pos);
    }

    public void setButtonSize(Vector size) {
        this.buttonUI.setSize(size);
    }

    public double getWidth() {
        return width;
    }

    public Vector getBarPos() {
        return barPos;
    }

    public void setColor(ColorE color) {
        this.color = color;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Vector getButtonPos() {
        return this.buttonUI.getPos();
    }

    public Vector getButtonSize() {
        return this.buttonUI.getSize();
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setBarPos(Vector barPos) {
        this.barPos = barPos;
    }

    public ColorE getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public boolean isOpen() {
        return open;
    }

    public String getTitle() {
        return title;
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
