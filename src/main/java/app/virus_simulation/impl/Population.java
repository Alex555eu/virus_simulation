package app.virus_simulation.impl;

import app.virus_simulation.inter.IPerson;
import app.virus_simulation.inter.IPopulation;
import app.virus_simulation.memento.PersonMemento;
import app.virus_simulation.memento.PopulationMemento;
import app.virus_simulation.person_state.NoSymptomState;
import app.virus_simulation.person_state.SymptomState;

import java.util.ArrayList;
import java.util.List;

public class Population implements IPopulation {

    private int[] mapDimensions;
    private int populationSize;
    private double velocityConstraint;
    private List<IPerson> population = new ArrayList<>();


    public Population(double velocityConstraint, int dimensionConstraintX, int dimensionConstraintY, int populationSize) {
        this.mapDimensions = new int[]{dimensionConstraintX, dimensionConstraintY};
        this.populationSize = populationSize;
        this.velocityConstraint = velocityConstraint;
    }

    public Population() {};

    @Override
    public void createNewPopulation() {
        for(int i=0; i < populationSize; i++) {
            population.add(new Person(velocityConstraint, mapDimensions[0], mapDimensions[1]));
            if (i == populationSize-1) {
                population.getFirst().setState(new NoSymptomState(population.getFirst()));
                population.getLast().setState(new SymptomState(population.getLast()));
            }
        }
    }

    @Override
    public List<IPerson> getPopulation() {
        return population;
    }

    @Override
    public int[] getMapDimensions() {
        return mapDimensions;
    }

    @Override
    public void setMapDimensions(int[] mapDimensions) {
        this.mapDimensions = mapDimensions;
    }

    @Override
    public int getPopulationSize() {
        return populationSize;
    }

    @Override
    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    @Override
    public double getVelocityConstraint() {
        return velocityConstraint;
    }

    @Override
    public void setVelocityConstraint(double velocityConstraint) {
        this.velocityConstraint = velocityConstraint;
    }

    @Override
    public PopulationMemento takeSnapshot() {
        List<PersonMemento> mementos = new ArrayList<>();
        for(IPerson person : population) {
            mementos.add(person.takeSnapshot());
        }
        return new PopulationMemento(mapDimensions, populationSize, velocityConstraint, mementos);
    }

    @Override
    public void restoreMemento(PopulationMemento populationMemento) {
        this.mapDimensions = new int[]{populationMemento.getMapDimensions()[0], populationMemento.getMapDimensions()[1]};
        this.populationSize = populationMemento.getPopulationSize();
        this.velocityConstraint = populationMemento.getVelocityConstraint();
        if (this.population.isEmpty() || this.populationSize != populationMemento.getPopulationSize()) {
            this.population.removeAll(this.population);
            for (PersonMemento personMemento : populationMemento.getPopulation()) {
                IPerson person = new Person();
                person.restoreMemento(personMemento);
                this.population.add(person);
            }
        } else {
            for (int i = 0; i < populationSize; i++) {
                this.population.get(i).restoreMemento(populationMemento.getPopulation().get(i));
            }
        }
    }
}
