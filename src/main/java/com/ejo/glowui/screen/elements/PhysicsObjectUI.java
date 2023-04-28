package com.ejo.glowui.screen.elements;

import com.ejo.glowui.screen.Screen;
import com.ejo.glowui.screen.elements.shape.RectangleUI;
import com.ejo.glowui.screen.elements.shape.ShapeUI;
import org.util.glowlib.math.Vector;

/**
 * The PhysicsObject class is a container for any shape. The class uses the data from the shape and calculates kinematics to move
 * said shape anywhere on the screen
 */
public class PhysicsObjectUI implements IElementUI {

    private final ShapeUI shape;

    private double mass;
    private Vector velocity;
    private Vector acceleration;
    private Vector netForce;

    private double deltaT;

    private boolean disabled;

    public PhysicsObjectUI(ShapeUI shape, double mass, Vector velocity, Vector acceleration) {
        this.shape = shape;
        this.mass = mass;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.netForce = Vector.NULL;
        this.deltaT = 1f;
        this.disabled = false;
    }

    @Override
    public void draw() {
        if (shouldRender()) getShape().draw();
    }

    @Override
    public void tick() {
        getShape().tick();
        if (isDisabled()) resetMovement();
    }

    public void updateKinematics() {
        setVelocity(getVelocity().getAdded(getAcceleration().getMultiplied(getDeltaT())));
        setCenter(getCenter().getAdded(getVelocity().getMultiplied(getDeltaT())));
    }

    public void updateAccForce() {
        setAcceleration(getNetForce().getMultiplied(1 / getMass()));
        setNetForce(Vector.NULL); //Resets the force after acceleration calculation
    }

    public void resetMovement() {
        setNetForce(Vector.NULL);
        setAcceleration(Vector.NULL);
        setVelocity(Vector.NULL);
    }

    @Override
    public void onKeyPress(int key, int scancode, int action, int mods) {
    }

    @Override
    public void onMouseClick(int button, int action, int mods, Vector mousePos) {
    }


    @Override
    public void setRendered(boolean shouldRender) {
        getShape().setRendered(shouldRender);
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return getShape().updateMouseOver(mousePos);
    }


    public double setMass(double mass) {
        return this.mass = mass;
    }

    public Vector setPos(Vector pos) {
        return getShape().setPos(pos);
    }

    public Vector setCenter(Vector pos) {
        return getShape().setCenter(pos);
    }

    public Vector setVelocity(Vector velocity) {
        return this.velocity = velocity;
    }

    public Vector setAcceleration(Vector acceleration) {
        return this.acceleration = acceleration;
    }

    public Vector setNetForce(Vector netForce) {
        return this.netForce = netForce;
    }

    public double setDeltaT(double deltaT) {
        return this.deltaT = deltaT;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }


    @Override
    public boolean shouldRender() {
        return getShape().shouldRender();
    }

    @Override
    public boolean isMouseOver() {
        return getShape().isMouseOver();
    }

    @Override
    public Screen getScreen() {
        return getShape().getScreen();
    }


    public double getMass() {
        return mass;
    }

    public Vector getPos() {
        return getShape().getPos();
    }

    public Vector getCenter() {
        return getShape().getCenter();
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public Vector getNetForce() {
        return netForce;
    }

    public double getDeltaT() {
        return deltaT;
    }

    public boolean isDisabled() {
        return disabled;
    }


    public ShapeUI getShape() {
        return shape;
    }


}
