package app.virus_simulation.person_state;


import app.virus_simulation.inter.IPerson;

public class HealthyState extends State{

    public HealthyState(IPerson person) {
        super(person);
    }

    public HealthyState() {}

    @Override
    public void update() {}

    @Override
    public void infect(IPerson person) {}

    @Override
    public State newInstance(IPerson person) {
        return new HealthyState(person);
    }

}
