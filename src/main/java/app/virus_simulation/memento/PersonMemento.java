package app.virus_simulation.memento;

import app.virus_simulation.impl.Vector2D;
import app.virus_simulation.inter.IPerson;
import app.virus_simulation.inter.IVector;
import app.virus_simulation.person_state.State;

import java.io.Serializable;


public class PersonMemento implements Serializable {

    private IVector position;
    private IVector target;
    private double velocity;
    private double velocityConstraint;
    private State state;
    private double dimensionConstraintX;
    private double dimensionConstraintY;

    public PersonMemento(IPerson self, IVector position, IVector target, double velocity, double velocityConstraint, State state, double dimensionConstraintX, double dimensionConstraintY) {
        this.position = new Vector2D(position);
        this.target = new Vector2D(target);
        this.velocity = velocity;
        this.velocityConstraint = velocityConstraint;
        this.state = state.newInstance(self);
        this.dimensionConstraintX = dimensionConstraintX;
        this.dimensionConstraintY = dimensionConstraintY;
    }

    public PersonMemento() {};

    public void setPosition(IVector position) {
        this.position = position;
    }

    public void setTarget(IVector target) {
        this.target = target;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setVelocityConstraint(double velocityConstraint) {
        this.velocityConstraint = velocityConstraint;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setDimensionConstraintX(double dimensionConstraintX) {
        this.dimensionConstraintX = dimensionConstraintX;
    }

    public void setDimensionConstraintY(double dimensionConstraintY) {
        this.dimensionConstraintY = dimensionConstraintY;
    }

    public IVector getPosition() {
        return position;
    }

    public IVector getTarget() {
        return target;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getVelocityConstraint() {
        return velocityConstraint;
    }

    public State getState() {
        return state;
    }

    public double getDimensionConstraintX() {
        return dimensionConstraintX;
    }

    public double getDimensionConstraintY() {
        return dimensionConstraintY;
    }
}
