package app.virus_simulation.impl;

import app.virus_simulation.inter.IPerson;
import app.virus_simulation.inter.IVector;
import app.virus_simulation.memento.PersonMemento;
import app.virus_simulation.person_state.*;

import java.util.Random;


public class Person implements IPerson {

    private IVector position;
    private IVector target;
    private double velocity;
    private double velocityConstraint;
    private State state;
    private double dimensionConstraintX;
    private double dimensionConstraintY;

    private Random rand = new Random();

    public Person(double velocityConstraint, double dimensionConstraintX, double dimensionConstraintY) {
        this.velocityConstraint = velocityConstraint;
        this.dimensionConstraintX = dimensionConstraintX;
        this.dimensionConstraintY = dimensionConstraintY;
        this.position = spawnPerson();
        this.target = spawnPerson();
        this.velocity = rollForVelocity(velocityConstraint);
        this.state = new HealthyState(this);//rollNewState();

    }

    public Person() {};

    @Override
    public void infect(IPerson person){
        state.infect(person);
    }

    @Override
    public void updatePositionAndVelocity() {
        state.update(); // updates state of a person based on time spent being infected
        if (rand.nextInt(100) < 1) {  // chance(1%) of changing current target and velocity
            target = spawnPerson();
            velocity = rollForVelocity(velocityConstraint);
        }
        IVector direction = position.getVecDiff(target);
        double abs = direction.abs();
        if (abs <= velocity) {
            position = target;
            target = spawnPerson();
            velocity = rollForVelocity(velocityConstraint);
        } else { // move towards target with given velocity
            double stepFactor = velocity / abs;
            double[] pos = position.getComponents();
            double[] dir = direction.getComponents();
            double stepX = pos[0] + dir[0] * stepFactor;
            double stepY = pos[1] + dir[1] * stepFactor;
            position = new Vector2D(stepX, stepY);
        }
    }

    @Override
    public PersonMemento takeSnapshot() {
        return new PersonMemento(this, position, target, velocity, velocityConstraint, state, dimensionConstraintX, dimensionConstraintY);
    }

    @Override
    public void restoreMemento(PersonMemento memento) {
        this.position = memento.getPosition();
        this.target = memento.getTarget();
        this.velocity = memento.getVelocity();
        this.velocityConstraint = memento.getVelocityConstraint();
        this.state = memento.getState().newInstance(this);
        this.dimensionConstraintX = memento.getDimensionConstraintX();
        this.dimensionConstraintY = memento.getDimensionConstraintY();
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public IVector getPosition() {
        return position;
    }

    private State rollNewState() {
        int random = rand.nextInt(100);
        if (random < 98) {
            return new HealthyState(this);   // 98%
        } else if (random == 99) {
            return new SymptomState(this);   // 1%
        } else {
            return new NoSymptomState(this); // 1%
        }
    }

    private IVector spawnPerson() {
        int random = rand.nextInt(100);
        double tmpX = 0;
        double tmpY = 0;
        if (random < 25) {
            tmpX = rand.nextDouble(dimensionConstraintX);
            tmpY = dimensionConstraintY;
        } else if (random >= 25 && random < 50) {
            tmpX = dimensionConstraintX;
            tmpY = rand.nextDouble(dimensionConstraintY);
        } else if (random >= 50 && random < 75) {
            tmpX = rand.nextDouble(dimensionConstraintX);
        } else {
            tmpY = rand.nextDouble(dimensionConstraintY);
        }
        //return new Vector2D(tmpX, tmpY); // spawns at the borders of simulation
        return new Vector2D(rand.nextDouble(dimensionConstraintX), rand.nextDouble(dimensionConstraintY)); // spawns at the random place of simulation
    }

    private double rollForVelocity(double velocityConstraint) {
        return rand.nextDouble(velocityConstraint) * 0.4;
    }

}
