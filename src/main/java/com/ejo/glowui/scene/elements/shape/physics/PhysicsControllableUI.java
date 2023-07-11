package com.ejo.glowui.scene.elements.shape.physics;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.shape.IShape;
import com.ejo.glowui.util.Key;
import com.ejo.glowlib.math.Vector;

@Deprecated
public class PhysicsControllableUI extends PhysicsObjectUI {

    private double controlSpeed;

    public PhysicsControllableUI(IShape shape, double mass, Vector velocity, Vector acceleration, double controlSpeed) {
        super(shape, mass, velocity, acceleration);
        this.controlSpeed = controlSpeed;
    }

    @Override
    public void tick(Scene scene, Vector mousePos) {
        super.tick(scene, mousePos);
        if (Key.KEY_RIGHT.isKeyDown()) setPos(getPos().getAdded(Vector.I.getMultiplied(getControlSpeed())));
        if (Key.KEY_LEFT.isKeyDown()) setPos(getPos().getAdded(Vector.I.getMultiplied(-getControlSpeed())));
        if (Key.KEY_UP.isKeyDown()) setPos(getPos().getAdded(Vector.J.getMultiplied(-getControlSpeed())));
        if (Key.KEY_DOWN.isKeyDown()) setPos(getPos().getAdded(Vector.J.getMultiplied(getControlSpeed())));
    }

    public void setControlSpeed(double controlSpeed) {
        this.controlSpeed = controlSpeed;
    }

    public double getControlSpeed() {
        return controlSpeed;
    }

}
