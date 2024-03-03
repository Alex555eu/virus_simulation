package app.virus_simulation.person_state;

import app.virus_simulation.inter.IPerson;


public class ImmuneState extends State {

    public ImmuneState(IPerson person) {
        super(person);
    }

    public ImmuneState() {}

    @Override
    public void update() {}

    @Override
    public void infect(IPerson person) {
        // Immune person cannot infect others
    }

    @Override
    public State newInstance(IPerson person) {
        return new ImmuneState(person);
    }


}
