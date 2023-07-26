package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.DrawUtil;
import com.ejo.glowui.util.Mouse;
import com.ejo.glowlib.math.MathE;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;
import com.ejo.glowlib.misc.Container;
import com.ejo.glowlib.util.NumberUtil;
import com.ejo.glowui.util.QuickDraw;

import java.awt.*;

public class SliderUI<T extends Number> extends WidgetUI {

    private T value;
    private Container<T> container;

    private T min;
    private T max;
    private T step;
    private Type type;
    private boolean displayValue;

    private ColorE color;

    private boolean sliding;

    public SliderUI(String title, Vector pos, Vector size, ColorE color, Container<T> container, T min, T max, T step, Type type, boolean displayValue) {
        super(title,pos,size,true,true,null);
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

    public SliderUI(Vector pos, Vector size, ColorE color, Container<T> container, T min, T max, T step, Type type, boolean displayValue) {
        this("",pos,size,color,container,min,max,step,type,displayValue);
    }

    //Recommended for width to be *8 height
    @Override
    protected void drawWidget(Scene scene, Vector mousePos) {
        //Draw Background
        QuickDraw.drawRect(getPos(),getSize(),DrawUtil.WIDGET_BACKGROUND);

        double border = getSize().getY()/5;

        //Draw the slider fill
        double valueRange = getMax().doubleValue() - getMin().doubleValue();
        double sliderWidth = getSize().getX() / valueRange * (value.doubleValue() - getMin().doubleValue());
        QuickDraw.drawRect(getPos().getAdded(new Vector(border,border)), new Vector(sliderWidth - border, getSize().getY() - border*2), new ColorE(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), getColor().getAlpha() - 100));

        //Draw the slider node
        int nodeWidth = (int)(getSize().getY()/1.5f);
        double nodeX = sliderWidth - nodeWidth / 2f;
        if (nodeX + nodeWidth > getSize().getX()) nodeX = getSize().getX() - nodeWidth;
        if (nodeX < 0) nodeX = 0;
        QuickDraw.drawRect(getPos().getAdded(new Vector(nodeX,0)),new Vector(nodeWidth,getSize().getY()),getColor());

        //Draw Text
        String title;
        if (shouldDisplayValue()) {
            if (getType().equals(Type.INTEGER)) {
                title = (hasTitle() ? getTitle() + ": " : "") + value.intValue();
            } else {
                title = (hasTitle() ? getTitle() + ": " : "") + MathE.roundDouble(value.doubleValue(), 2);
            }
        } else {
            title = getTitle();
        }
        int size = (int)getSize().getY();
        setUpDisplayText(title,border,size);
        getDisplayText().setPos(getPos().getAdded(new Vector(border + border/5,-2 + getSize().getY() / 2 - getDisplayText().getHeight() / 2)));
        getDisplayText().draw(scene, mousePos);
    }

    /**
     * Updates the value of the slider per every tick based off if the slider is sliding
     */
    @Override
    protected void tickWidget(Scene scene, Vector mousePos) {
        this.value = getContainer().get(); //Consistently sync the value of the container and the value of the widget

        if (sliding) { //Updates the value of the setting based off of the current width of the slider
            double valueRange = getMax().doubleValue() - getMin().doubleValue();
            double sliderWidth = mousePos.getX() - getPos().getX();
            double sliderPercent = NumberUtil.getBoundValue(sliderWidth,0,getSize().getX()).doubleValue() / getSize().getX();
            double calculatedValue = sliderPercent * valueRange + getMin().doubleValue();

            double val = MathE.roundDouble((((Math.round(calculatedValue / getStep().doubleValue())) * getStep().doubleValue())), 2); //Rounds the slider based off of the step val

            value = getType().equals(Type.INTEGER) ? (T) (Integer) (int) val : (T) (Double) val;

            getAction().run();
        }
    }

    @Override
    public void onKeyPress(Scene scene, int key, int scancode, int action, int mods) {
    }

    /**
     * The mouse click sets the slider to be sliding for value setting
     */
    @Override
    public void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos) {
        if (action == Mouse.ACTION_CLICK) {
            if (isMouseOver() && button == Mouse.BUTTON_LEFT.getId()) {
                sliding = true;
            }
        }
        if (action == Mouse.ACTION_RELEASE) {
            if (button == Mouse.BUTTON_LEFT.getId()) {
                if (sliding) sliding = false;
            }
        }
    }

    public void setColor(ColorE color) {
        this.color = color;
    }

    public void setValueDisplayed(boolean displayValue) {
        this.displayValue = displayValue;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setStep(T step) {
        this.step = step;
    }

    public void setMax(T max) {
        this.max = max;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public void setContainer(Container<T> container) {
        this.container = container;
    }


    public ColorE getColor() {
        return color;
    }

    public boolean shouldDisplayValue() {
        return displayValue;
    }

    public Type getType() {
        return type;
    }

    public T getStep() {
        return step;
    }

    public T getMax() {
        return max;
    }

    public T getMin() {
        return min;
    }

    public Container<T> getContainer() {
        return container;
    }


    public enum Type {
        INTEGER, FLOAT
    }

}
