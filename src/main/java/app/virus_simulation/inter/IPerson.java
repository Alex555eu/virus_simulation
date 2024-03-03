package app.virus_simulation.inter;

import app.virus_simulation.memento.PersonMemento;
import app.virus_simulation.person_state.State;

public interface IPerson {

    void infect(IPerson person);

    /**
     * Updates the state of the person.<br/><br/>
     * Moves person from current position to targeted position with given velocity.<br/><br/>
     * Change of current target and velocity occurs with % of chance or when the person reaches the simulations border.
     */
    void updatePositionAndVelocity();

    State getState();

    void setState(State state);

    IVector getPosition();

    PersonMemento takeSnapshot();

    void restoreMemento(PersonMemento memento);
}
