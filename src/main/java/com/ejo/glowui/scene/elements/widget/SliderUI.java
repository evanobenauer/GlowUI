package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.TextUI;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import com.ejo.glowui.util.Mouse;
import org.util.glowlib.math.MathE;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;
import org.util.glowlib.misc.Container;
import org.util.glowlib.setting.Setting;
import org.util.glowlib.setting.SettingUI;
import org.util.glowlib.util.NumberUtil;


//TODO: Minimums that are non-zero are broken
public class SliderUI<T extends Number> extends WidgetUI {

    private final Container<T> container;

    private ColorE color;

    private T value;
    private final T min;
    private final T max;
    private final T step;

    private boolean displayValue;

    private boolean sliding;

    public SliderUI(Scene scene, Container<T> container, String name, T min, T max, T step, Vector pos, Vector size, ColorE color, boolean displayValue) {
        super(scene,name,pos,size,true,true,null);
        this.color = color;
        this.container = container;

        this.value = container.get();
        this.min = min;
        this.max = max;
        this.step = step;

        this.displayValue = displayValue;
        this.sliding = false;

        setAction(() -> container.set(value));
    }

    public SliderUI(Scene scene, Container<T> container, T min, T max, T step, Vector pos, Vector size, ColorE color,boolean displayValue) {
        this(scene, container, "", min, max, step, pos, size, color,displayValue);
    }

    public SliderUI(Scene scene, SettingUI<T> settingUI, Vector pos, Vector size, ColorE color, boolean displayValue) {
        this(scene,settingUI, settingUI.getName(), settingUI.getMin(), settingUI.getMax(), settingUI.getStep(), pos, size, color,displayValue);
    }


    @Override
    protected void drawWidget() {
        //Draw Background
        new RectangleUI(getScene(),getPos(),getSize(),new ColorE(50,50,50,200)).draw();

        //Draw the slider fill
        int borderY = 10; //TODO Figure out scaling math for the borders
        int borderX = 10;
        double sliderWidth = getSize().getX() * (getContainer().get().doubleValue() / getMax().doubleValue());
        new RectangleUI(getScene(),getPos().getAdded(new Vector(borderX,borderY)), new Vector(sliderWidth - borderX, getSize().getY() - borderY*2), new ColorE(0, 125, 200, 150)).draw();

        //Draw the slider node
        int nodeWidth = (int)(getSize().getY()/1.5f);
        double nodeX = sliderWidth - nodeWidth / 2f;
        if (nodeX + nodeWidth > getSize().getX()) nodeX = getSize().getX() - nodeWidth;
        if (nodeX < 0) nodeX = 0;
        new RectangleUI(getScene(),getPos().getAdded(new Vector(nodeX,0)),new Vector(nodeWidth,getSize().getY()),new ColorE(0,125,200,255)).draw();

        //Draw the slider text
        String title;
        if (getContainer().getType().equals("integer")) {
            title = getName() + ": " + getContainer().get().intValue();
        } else {
            title = getName() + ": " + MathE.roundDouble(getContainer().get().doubleValue(),2);
        }
        TextUI text = new TextUI(getScene(),title,null,Vector.NULL);
        text.setPos(getPos().getAdded(new Vector(2,getSize().getY() / 2 - text.getHeight() / 2)));
        text.draw();
    }

    /**
     * Updates the value of the slider per every tick based off if the slider is sliding
     */
    @Override
    public void tick() {
        super.tick();
        if (sliding) { //Updates the value of the setting based off of the current width of the slider
            double settingRange = getMax().doubleValue() - getMin().doubleValue();
            double sliderWidth = getScene().getWindow().getMousePos().getX() - getPos().getX();
            double sliderPercent = NumberUtil.getBoundValue(sliderWidth,0,getSize().getX()).doubleValue() / getSize().getX();
            double calculatedValue = getMin().doubleValue() + (sliderPercent * settingRange);
            double val = MathE.roundDouble((((Math.round(calculatedValue / getStep().doubleValue())) * getStep().doubleValue())), 2); //Rounds the slider based off of the step val

            value = getContainer().getType().equals("integer") ? (T) (Integer) (int) val : (T) (Double) val;

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

    public void setColor(ColorE color) {
        this.color = color;
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

    //TODO: Convert to container and assign values
    public Setting<T> getContainer() {
        return (Setting<T>) container;
    }
}
