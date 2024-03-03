package app.virus_simulation.person_state;

import app.virus_simulation.inter.IPerson;

import java.io.Serializable;

public abstract class State implements Serializable {

    private IPerson person;

    State(IPerson person) {
        this.person = person;
    }

    State() {};

    /**
     * Updates the current health state, allowing to gain immune state after a certain time spent being infected.
     */
    public abstract void update();

    /**
     * Tries to change another persons health state based on time spent inside infection area, and magnitude of symptoms
     * @param person
     */
    public abstract void infect(IPerson person);

    public abstract State newInstance(IPerson person);

    public IPerson getPerson() {
        return person;
    }
}
