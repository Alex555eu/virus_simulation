package app.virus_simulation.memento;

import app.virus_simulation.memento.PersonMemento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PopulationMemento implements Serializable {

    private int[] mapDimensions;
    private int populationSize;
    private double velocityConstraint;
    private List<PersonMemento> population = new ArrayList<>();

    public PopulationMemento(int[] mapDimensions, int populationSize, double velocityConstraint, List<PersonMemento> population) {
        this.mapDimensions = mapDimensions;
        this.populationSize = populationSize;
        this.velocityConstraint = velocityConstraint;
        this.population = population;
    }

    public PopulationMemento() {
    }

    public int[] getMapDimensions() {
        return mapDimensions;
    }

    public void setMapDimensions(int[] mapDimensions) {
        this.mapDimensions = mapDimensions;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public double getVelocityConstraint() {
        return velocityConstraint;
    }

    public void setVelocityConstraint(double velocityConstraint) {
        this.velocityConstraint = velocityConstraint;
    }

    public List<PersonMemento> getPopulation() {
        return population;
    }

    public void setPopulation(List<PersonMemento> population) {
        this.population = population;
    }
}
