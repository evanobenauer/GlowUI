package com.ejo.glowui.scene.elements.shape.physics;

import com.ejo.glowui.scene.elements.shape.IShape;
import org.util.glowlib.math.Vector;

public class PhysicsDraggableUI extends PhysicsObjectUI {

    private boolean dragging;
    private Vector dragPos = Vector.NULL;

    public PhysicsDraggableUI(IShape shape, double mass, Vector velocity, Vector acceleration) {
        super(shape, mass, velocity, acceleration);
        this.dragging = false;
    }

    @Override
    public void tick() {
        super.tick();
        if (isDragging()) {
            resetMovement();
            setPos(getScene().getWindow().getMousePos().getAdded(dragPos.getMultiplied(-1)));
        } else {
            dragPos = getScene().getWindow().getMousePos().getAdded(getPos().getMultiplied(-1));
        }
    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
        super.onMouseClick(button, action, mods, mousePos);
        if (button == 0 && action == 0) dragging = false;
        if (isMouseOver()) if (button == 0 && action == 1) dragging = true;
    }

    public boolean isDragging() {
        return dragging;
    }

}
