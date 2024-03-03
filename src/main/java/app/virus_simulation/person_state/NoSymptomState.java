package app.virus_simulation.person_state;


import app.virus_simulation.inter.IPerson;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NoSymptomState extends State implements Serializable {

    private Map<IPerson, Integer> healthyPersonInsideInfectionArea; // <person, cycles spent inside>
    private int cyclesSinceInfected;


    public NoSymptomState(){
        healthyPersonInsideInfectionArea = new HashMap<>();
    };

    public NoSymptomState(IPerson person) {
        super(person);
        healthyPersonInsideInfectionArea = new HashMap<>();
        cyclesSinceInfected = 1;
    }

    @Override
    public State newInstance(IPerson person) {
        return new NoSymptomState(person);
    }

    @Override
    public void update() {
        cyclesSinceInfected++;
        if (cyclesSinceInfected >= 500) {
            double chance = (cyclesSinceInfected - 500) / 2.5;
            Random rand = new Random();
            if (rand.nextInt(200) < (int)chance) { // theoretically correct bound should be 100
                super.getPerson().setState(new ImmuneState(super.getPerson()));
            }
        }
    }

    @Override
    public void infect(IPerson person) {
        if (person.getState().getClass() == HealthyState.class &&
            super.getPerson().getPosition().distance(person.getPosition()) <= 20 // check if the distance between ill and healthy person is less than 20 pixels (2 meters)
        ){
            if (healthyPersonInsideInfectionArea.containsKey(person)) {          // if healthy person is already inside infection area
                if (healthyPersonInsideInfectionArea.get(person) == 50) {        // if healthy person has been inside infection area for 2s (50 cycles)
                    if ((int)Math.floor(Math.random()*2) == 0) {                 // 50% chance of infection
                        person.setState(new NoSymptomState(person));
                    }
                    healthyPersonInsideInfectionArea.remove(person);
                } else {
                    healthyPersonInsideInfectionArea.put(person, healthyPersonInsideInfectionArea.get(person) + 1);
                }
            } else {
                healthyPersonInsideInfectionArea.put(person, 1);
            }
        } else {
            healthyPersonInsideInfectionArea.remove(person);
        }
    }

}
