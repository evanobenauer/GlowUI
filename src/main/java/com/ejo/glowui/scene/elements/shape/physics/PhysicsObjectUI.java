package com.ejo.glowui.scene.elements.shape.physics;

import com.ejo.glowui.scene.Scene;
import com.ejo.glowui.scene.elements.ElementUI;
import com.ejo.glowui.scene.elements.shape.IShape;
import com.ejo.glowui.scene.elements.shape.LineUI;
import com.ejo.glowlib.math.Vector;
import com.ejo.glowlib.misc.ColorE;

import java.util.ArrayList;

/**
 * The PhysicsObject class is a container for any shape. The class uses the data from the shape and calculates kinematics to move
 * said shape anywhere on the screen.
 * NOTE: Physics objects will NOT render properly in economy mode as they do not post empty events. You MUST use standard mode to render properly
 */
public class PhysicsObjectUI extends ElementUI implements IShape {

    protected final IShape shape;

    private double mass;
    private Vector velocity;
    private Vector acceleration;
    private Vector netForce;

    public double spin;
    private double omega;
    private double alpha;
    private double netTorque;

    private double deltaT;

    private boolean disabled;

    public PhysicsObjectUI(IShape shape, double mass, Vector velocity, Vector netForce) {
        super(shape.getPos(), shape.shouldRender(),true);
        this.shape = shape;
        this.mass = mass;
        this.velocity = velocity;
        this.acceleration = Vector.NULL;
        this.netForce = netForce;
        this.spin = 0;
        this.omega = 0;
        this.alpha = 0;
        this.netTorque = 0;
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
            updateAlphaFromTorque();
            updateRotationalKinematics();
        } else {
            resetMovement();
        }
    }

    @Override
    public boolean updateMouseOver(Vector mousePos) {
        return shape.updateMouseOver(mousePos);
    }


    public void updateKinematics() {
        setVelocity(getVelocity().getAdded(getAcceleration().getMultiplied(getDeltaT())));
        setCenter(getCenter().getAdded(getVelocity().getMultiplied(getDeltaT())));
    }

    public void updateAccForce() {
        setAcceleration(getNetForce().getMultiplied(1 / getMass()));
    }

    private void updateAlphaFromTorque() {
        double I = 1;//(double) 2 /5 * getMass() * Math.pow(getPolygon().getRadius(),2);
        alpha = netTorque / I;
    }

    private void updateRotationalKinematics() {
        omega += alpha*getDeltaT();
        spin += omega*getDeltaT();
    }

    public void resetMovement() {
        setNetForce(Vector.NULL);
        setAcceleration(Vector.NULL);
        setVelocity(Vector.NULL);
    }

    //TODO: Add collisions with shapes and lines here
    public boolean isColliding(IShape object) {
        return false;
    }

    public boolean isColliding(LineUI line) {
        return false;
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

    private Vector setAcceleration(Vector acceleration) {
        return this.acceleration = acceleration;
    }

    public Vector setNetForce(Vector netForce) {
        return this.netForce = netForce;
    }

    public void setSpin(double spin) {
        this.spin = spin;
    }

    public void setOmega(double omega) {
        this.omega = omega;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setNetTorque(double netTorque) {
        this.netTorque = netTorque;
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

    public double getSpin() {
        return spin;
    }

    public double getOmega() {
        return omega;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getNetTorque() {
        return netTorque;
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
