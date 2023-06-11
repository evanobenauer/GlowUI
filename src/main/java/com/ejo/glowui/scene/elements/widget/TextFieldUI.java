package com.ejo.glowui.scene.elements.widget;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.RectangleUI;
import org.util.glowlib.math.Vector;
import org.util.glowlib.misc.ColorE;


//TODO: INCOMPLETE PLACEHOLDER
public class TextFieldUI extends WidgetUI {

    private ColorE color;

    public RectangleUI baseRect;

    public TextFieldUI(Scene scene, String name, Vector pos, Vector size, ColorE color, Runnable action) {
        super(scene,name,pos,size,true,true,action);
        this.color = color;
    }

    public TextFieldUI(Scene scene, Vector pos, Vector size, ColorE color, Runnable action) {
        this(scene,"",pos,size,color,action);
    }

    @Override
    protected void drawWidget() {

    }

    @Override
    public void tick() {
        baseRect = new RectangleUI(getScene(),getPos(),getSize(),getColor());
        super.tick();
    }

    @Override
    public void onKeyPress(int key, int scancode, int action, int mods) {
        //TODO MAKE THIS TEXT FIELD
    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        if (isMouseOver()) {
            if (action == 1) {
                //activate text field
            }
        }  else {
            //If the mouse is not over, unselect
        }
    }

    public void setColor(ColorE color) {
        this.color = color;
    }

    public ColorE getColor() {
        return color;
    }
}
