package app.virus_simulation.inter;

import app.virus_simulation.memento.PopulationMemento;

import java.util.List;

public interface IPopulation {

    void createNewPopulation();

    List<IPerson> getPopulation();

    int[] getMapDimensions();

     void setMapDimensions(int[] mapDimensions);

     int getPopulationSize();

     void setPopulationSize(int populationSize);

     double getVelocityConstraint();

     void setVelocityConstraint(double velocityConstraint);

     PopulationMemento takeSnapshot();

     void restoreMemento(PopulationMemento populationMemento);

}
