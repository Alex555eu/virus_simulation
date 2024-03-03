package app.virus_simulation.impl;

import app.virus_simulation.inter.IPerson;
import app.virus_simulation.inter.IPopulation;
import app.virus_simulation.inter.ISimulatePopulation;
import app.virus_simulation.person_state.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;

public class SimulatePopulation implements ISimulatePopulation {

    private IPopulation population;
    private List<Circle> populationFx;

    private int cyclesSinceBeginning = 0;
    private int currentCycleHealthyCount;
    private int currentCycleImmuneCount;
    private int currentCycleSymptomsCount;
    private int currentCycleNoSymptomsCount;

    public SimulatePopulation(IPopulation population, List<Circle> populationFx) {
        this.population = population;
        this.populationFx = populationFx;
    }

    @Override
    public void cycle() {
        //long timeStamp = System.currentTimeMillis();
        cyclesSinceBeginning++;
        currentCycleHealthyCount = 0;
        currentCycleImmuneCount = 0;
        currentCycleSymptomsCount = 0;
        currentCycleNoSymptomsCount = 0;

        population.getPopulation().getFirst().updatePositionAndVelocity();
        for (int i = 0; i < population.getPopulationSize(); i++) {
            IPerson person = population.getPopulation().get(i);
            updatePopCount(person.getState());
            for (int j = i + 1; j < population.getPopulationSize(); j++) {
                IPerson another = population.getPopulation().get(j);
                if (i == 0)
                    another.updatePositionAndVelocity();
                if (person.getState().getClass() == NoSymptomState.class || person.getState().getClass() == SymptomState.class) {
                    person.infect(another);
                } else if (another.getState().getClass() == NoSymptomState.class || another.getState().getClass() == SymptomState.class) {
                    another.infect(person);
                }
            }
        }

        updateView();
        //System.out.println("time taken: " + (System.currentTimeMillis() - timeStamp));
    }

    @Override
    public int getCurrentCycleHealthyCount() {
        return currentCycleHealthyCount;
    }

    @Override
    public int getCurrentCycleImmuneCount() {
        return currentCycleImmuneCount;
    }

    @Override
    public int getCurrentCycleSymptomsCount() {
        return currentCycleSymptomsCount;
    }

    @Override
    public int getCurrentCycleNoSymptomsCount() {
        return currentCycleNoSymptomsCount;
    }

    private void updateView() {
        for(int i = 0; i < population.getPopulationSize(); i++) {
            IPerson person = population.getPopulation().get(i);
            Circle personFx = populationFx.get(i);
            personFx.setCenterX(person.getPosition().getComponents()[0]);
            personFx.setCenterY(person.getPosition().getComponents()[1]);
            personFx.setFill(updateColor(person.getState()));
        }
    }

    private Color updateColor(State state) {
        if (state.getClass() == ImmuneState.class)    return Color.CYAN;
        if (state.getClass() == HealthyState.class)   return Color.GREEN;
        if (state.getClass() == NoSymptomState.class) return Color.ORANGE;
        if (state.getClass() == SymptomState.class)   return Color.RED;
        else return null;
    }

    private void updatePopCount(State state) {
        if (state.getClass() == ImmuneState.class)    currentCycleImmuneCount++;
        if (state.getClass() == HealthyState.class)   currentCycleHealthyCount++;
        if (state.getClass() == NoSymptomState.class) currentCycleNoSymptomsCount++;
        if (state.getClass() == SymptomState.class)   currentCycleSymptomsCount++;
    }


}
