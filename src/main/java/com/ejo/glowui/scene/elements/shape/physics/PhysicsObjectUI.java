package com.ejo.glowui.scene.elements.shape.physics;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import com.ejo.glowui.scene.elements.shape.IShape;
import com.ejo.glowui.scene.elements.shape.PolygonUI;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

/**
 * The PhysicsObject class is a container for any shape. The class uses the data from the shape and calculates kinematics to move
 * said shape anywhere on the screen
 */
public class PhysicsObjectUI extends ElementUI implements IShape {

    protected final IShape shape;

    private double mass;
    private Vector velocity;
    private Vector acceleration;
    private Vector netForce;

    private double deltaT;

    private boolean disabled;

    public PhysicsObjectUI(IShape shape, double mass, Vector velocity, Vector acceleration) {
        super(shape.getPos(), shape.shouldRender(),true);
        this.shape = shape;
        this.mass = mass;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.netForce = Vector.NULL;
        this.deltaT = 1f;
        this.disabled = false;
    }

    @Override
    protected void drawElement(Scene scene, Vector mousePos) {
        shape.draw(scene, mousePos);
    }

    @Override
    protected void tickElement(Scene scene, Vector mousePos) {
        if (!isDisabled()) {
            updateAccForce();
            updateKinematics();
        } else {
            resetMovement();
        }
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

    public boolean isColliding(IShape object) {
        if (shape instanceof PolygonUI && object instanceof PolygonUI) {
            //TODO: Add code here
        }
        return false;
    }
    /*
    public boolean isColliding(BoundingBox.Line line) {
        return false;
    }
     */

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return shape.updateMouseOver(mousePos);
    }


    public double setMass(double mass) {
        return this.mass = mass;
    }

    public Vector setPos(Vector pos) {
        return shape.setPos(pos);
    }

    public Vector setCenter(Vector pos) {
        return shape.setCenter(pos);
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

    public void setColor(ColorE color) {
        shape.setColor(color);
    }


    public double getMass() {
        return mass;
    }

    public Vector getPos() {
        return shape.getPos();
    }

    public Vector getCenter() {
        return shape.getCenter();
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

    public ColorE getColor() {
        return shape.getColor();
    }


    public IShape getShape() {
        return shape;
    }

}
