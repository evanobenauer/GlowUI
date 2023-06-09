package com.ejo.glowui.scene.elements.shape.physics;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.IShape;
import com.ejo.glowlib.math.Vector;

public class PhysicsDraggableUI extends PhysicsObjectUI {

    private boolean dragging;
    private Vector dragPos = Vector.NULL;

    public PhysicsDraggableUI(IShape shape, double mass, Vector velocity, Vector acceleration) {
        super(shape, mass, velocity, acceleration);
        this.dragging = false;
    }

    @Override
    public void tickElement(Scene scene, Vector mousePos) {
        if (isDragging()) {
            resetMovement();
            setPos(scene.getWindow().getScaledMousePos().getAdded(dragPos.getMultiplied(-1)));
        } else {
            dragPos = scene.getWindow().getScaledMousePos().getAdded(getPos().getMultiplied(-1));
        }
        super.tickElement(scene,mousePos);
    }

    @Override
    public void onMouseClick(Scene scene, int button, int action, int mods, Vector mousePos) {
        if (button == 0 && action == 0) dragging = false;
        if (isMouseOver()) if (button == 0 && action == 1) dragging = true;
    }

    public boolean isDragging() {
        return dragging;
    }

}
