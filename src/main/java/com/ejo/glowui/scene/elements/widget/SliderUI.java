package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.TextUI;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.Mouse;
import org.util.glowlib.math.MathE;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.misc.Container;
import org.util.glowlib.util.NumberUtil;

import java.awt.*;

//TODO Fix Colors on all widgets
public class SliderUI<T extends Number> extends WidgetUI {

    public enum Type {
        INTEGER, FLOAT
    }

    private final Container<T> container;

    private final Type type;

    private ColorE color;

    private T value;
    private final T min;
    private final T max;
    private final T step;

    private boolean displayValue;

    private boolean sliding;

    public SliderUI(Scene scene, Container<T> container, String title, T min, T max, T step, Vector pos, Vector size, ColorE color, Type type, boolean displayValue) {
        super(scene,title,pos,size,true,true,null);
        this.container = container;
        this.type = type;

        this.color = color;

        this.value = container.get();
        this.min = min;
        this.max = max;
        this.step = step;

        this.displayValue = displayValue;
        this.sliding = false;

        setAction(() -> container.set(value));
    }

    public SliderUI(Scene scene, Container<T> container, T min, T max, T step, Vector pos, Vector size, ColorE color,Type type,boolean displayValue) {
        this(scene, container, "", min, max, step, pos, size, color,type,displayValue);
    }


    @Override
    protected void drawWidget() {
        //Draw Background
        new RectangleUI(getScene(),getPos(),getSize(),new ColorE(50,50,50,200)).draw();

        //Draw the slider fill
        int borderY = 10; //TODO Figure out scaling math for the borders
        int borderX = 10;
        double valueRange = getMax().doubleValue() - getMin().doubleValue();
        double sliderWidth = getSize().getX() / valueRange * (getContainer().get().doubleValue() - getMin().doubleValue());
        new RectangleUI(getScene(),getPos().getAdded(new Vector(borderX,borderY)), new Vector(sliderWidth - borderX, getSize().getY() - borderY*2), new ColorE(0, 125, 200, 150)).draw();

        //Draw the slider node
        int nodeWidth = (int)(getSize().getY()/1.5f);
        double nodeX = sliderWidth - nodeWidth / 2f;
        if (nodeX + nodeWidth > getSize().getX()) nodeX = getSize().getX() - nodeWidth;
        if (nodeX < 0) nodeX = 0;
        new RectangleUI(getScene(),getPos().getAdded(new Vector(nodeX,0)),new Vector(nodeWidth,getSize().getY()),new ColorE(0,125,200,255)).draw();

        //Draw the slider text
        String title;
        if (shouldDisplayValue()) {
            if (getType().equals(Type.INTEGER)) {
                title = (hasTitle() ? getTitle() + ": " : "") + getContainer().get().intValue();
            } else {
                title = (hasTitle() ? getTitle() + ": " : "") + MathE.roundDouble(getContainer().get().doubleValue(), 2);
            }
        } else {
            title = getTitle();
        }
        //TODO: Fix text scaling
        TextUI text = new TextUI(getScene(),title,new Font("Arial", Font.PLAIN, (int)getSize().getY()),Vector.NULL,ColorE.WHITE);
        text.setPos(getPos().getAdded(new Vector(2,-1 + getSize().getY() / 2 - text.getHeight() / 2)));
        text.draw();
    }

    /**
     * Updates the value of the slider per every tick based off if the slider is sliding
     */
    @Override
    public void tick() {
        super.tick();
        if (sliding) { //Updates the value of the setting based off of the current width of the slider
            double valueRange = getMax().doubleValue() - getMin().doubleValue();
            double sliderWidth = getScene().getWindow().getMousePos().getX() - getPos().getX();
            double sliderPercent = NumberUtil.getBoundValue(sliderWidth,0,getSize().getX()).doubleValue() / getSize().getX();
            double calculatedValue = sliderPercent * valueRange + getMin().doubleValue();

            double val = MathE.roundDouble((((Math.round(calculatedValue / getStep().doubleValue())) * getStep().doubleValue())), 2); //Rounds the slider based off of the step val

            value = getType().equals(Type.INTEGER) ? (T) (Integer) (int) val : (T) (Double) val;

            getAction().run();
        }
    }

    /**
     * The mouse click sets the slider to be sliding for value setting
     */
    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        super.onMouseClick(button, action, mods, mousePos);
        if (action == Mouse.ACTION_CLICK)
            if (isMouseOver() && button == Mouse.BUTTON_LEFT.getId()) {
                sliding = true;
        }
        if (action == Mouse.ACTION_RELEASE) {
            if (button == Mouse.BUTTON_LEFT.getId()) {
                if (sliding) sliding = false;
            }
        }
    }

    public void setValueDisplayed(boolean displayValue) {
        this.displayValue = displayValue;
    }

    public void setColor(ColorE color) {
        this.color = color;
    }


    public boolean shouldDisplayValue() {
        return displayValue;
    }

    public Type getType() {
        return type;
    }

    public ColorE getColor() {
        return color;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public T getStep() {
        return step;
    }

    public Container<T> getContainer() {
        return container;
    }

}
