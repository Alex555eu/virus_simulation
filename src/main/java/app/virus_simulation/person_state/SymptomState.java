package app.virus_simulation.person_state;

import app.virus_simulation.inter.IPerson;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SymptomState extends State{

    private Map<IPerson, Integer> healthyPersonInsideInfectionArea; // <person, cycles spent inside>
    private int cyclesSinceInfected;

    public SymptomState(IPerson person) {
        super(person);
        healthyPersonInsideInfectionArea = new HashMap<>();
        cyclesSinceInfected = 1;
    }

    public SymptomState() {
        healthyPersonInsideInfectionArea = new HashMap<>();
        cyclesSinceInfected = 1;
    }

    @Override
    public State newInstance(IPerson person) {
        return new SymptomState(person);
    }

    @Override
    public void update() {
        cyclesSinceInfected++;
        if (cyclesSinceInfected >= 500) { // 20s
            double chance = (cyclesSinceInfected - 500) / 2.5; // the longer infected the higher percentage of gaining immunity
            Random rand = new Random();
            if (rand.nextInt(200) < (int)chance) { // theoretically correct bound should be 100, but its pseudo random, so it will work nicer
                super.getPerson().setState(new ImmuneState(super.getPerson()));
            }
        }
    }

    @Override
    public void infect(IPerson person) {
        if (person.getState().getClass() == HealthyState.class &&
            super.getPerson().getPosition().distance(person.getPosition()) <= 20 // check if the distance between ill and healthy person is less than 20 pixels (2 meters)
            ){
                if (healthyPersonInsideInfectionArea.containsKey(person)) {      // if healthy person is already inside infection area
                    if (healthyPersonInsideInfectionArea.get(person) == 50) {    // if healthy person has been inside infection area for 2s (50 cycles)
                        healthyPersonInsideInfectionArea.remove(person);
                        person.setState(new SymptomState(person));
                    } else {
                        healthyPersonInsideInfectionArea.put(person, healthyPersonInsideInfectionArea.get(person)+1);
                    }
                } else {
                    healthyPersonInsideInfectionArea.put(person, 1);
                }
        } else {
            healthyPersonInsideInfectionArea.remove(person);
        }
    }


}


/*
    public void cleanUp() {
        if (!personInsideInfectionArea.isEmpty()) {
            personInsideInfectionArea.entrySet().removeIf(e -> super.getPerson()
                                                                    .getPosition()
                                                                    .distance(e.getKey().getPosition()) > 20);
        }
    }*/
