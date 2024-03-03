package app.virus_simulation;

import app.virus_simulation.impl.Population;
import app.virus_simulation.impl.SimulatePopulation;
import app.virus_simulation.inter.IPopulation;
import app.virus_simulation.inter.ISimulatePopulation;
import app.virus_simulation.memento.MementoCareTaker;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class MainApplicationController {

    @FXML
    private TextField populationSizeTextField;

    @FXML
    private TextField velocityConstraintTextField;

    @FXML
    private Pane simulationPane;

    @FXML
    private Label immuneCountLabel;

    @FXML
    private Label healthyCountLabel;

    @FXML
    private Label symptomsCountLabel;

    @FXML
    private Label noSymptomsCountLabel;

    private final int PERSON_FX_RADIUS = 5;
    private final int SIMULATION_WIDTH = 700 +(PERSON_FX_RADIUS*4);
    private final int SIMULATION_HEIGHT = 320 +(PERSON_FX_RADIUS*4);

    private IPopulation population;
    private MementoCareTaker mementoCareTaker;
    private ISimulatePopulation simulatePopulation;
    private AnimationTimer animationTimer;

    @FXML
    protected void onStartNewSimulationBtnClick() {
        String velocityConstraint = velocityConstraintTextField.getText();
        Double velocityConstraintNum = 0.0;
        try {
            velocityConstraintNum = Double.parseDouble(velocityConstraint);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String populationSize = populationSizeTextField.getText();
        Integer populationSizeNum = 0;
        try {
            populationSizeNum = Integer.parseInt(populationSize);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        population = new Population(velocityConstraintNum, SIMULATION_WIDTH, SIMULATION_HEIGHT, populationSizeNum);
        population.createNewPopulation();
        mementoCareTaker = new MementoCareTaker();

        startAnimation();

    }

    @FXML
    protected void onStopSimulationBtnClick() {
        animationTimer.stop();
    }

    @FXML
    protected void onResumeSimulationBtnClick() {
        animationTimer.start();
    }

    @FXML
    protected void onReloadFromFileBtnClick() {
        population = new Population(0, SIMULATION_WIDTH, SIMULATION_HEIGHT, 0);
        population.createNewPopulation();
        mementoCareTaker = new MementoCareTaker();

        mementoCareTaker.decode();
        mementoCareTaker.restore(population);

        startAnimation();
    }

    @FXML
    protected void onTakeSnapshotBtnClick() {
        mementoCareTaker.create(population);
    }

    @FXML
    protected void onLoadFromSnapBtnClick() {
        mementoCareTaker.restore(population);
    }

    @FXML
    protected void onSnapToFileBtnClick() {
        mementoCareTaker.create(population);
        mementoCareTaker.encode();
    }

    private void startAnimation() {
        simulationPane.setLayoutX(-PERSON_FX_RADIUS*2);
        simulationPane.setLayoutY(-PERSON_FX_RADIUS*2);

        if (animationTimer != null) // stop ongoing animation if it exists
            animationTimer.stop();

        simulationPane.getChildren().removeAll(simulationPane.getChildren()); // clear Pane of old elements

        List<Circle> populationFx = new ArrayList<>();
        for(int i = 0; i < population.getPopulationSize(); i++) {
            populationFx.add(new Circle(0,0,PERSON_FX_RADIUS));
            simulationPane.getChildren().add(populationFx.getLast());
        }
        simulatePopulation = new SimulatePopulation(population, populationFx);

        animationTimer = new AnimationTimer() {
            long timeStamp = 0;
            @Override
            public void handle(long l) {
                if ((System.currentTimeMillis() - timeStamp) > 40) { // 25fps
                    timeStamp = System.currentTimeMillis();
                    simulatePopulation.cycle();
                    immuneCountLabel.setText(String.valueOf(simulatePopulation.getCurrentCycleImmuneCount()));
                    healthyCountLabel.setText(String.valueOf(simulatePopulation.getCurrentCycleHealthyCount()));
                    symptomsCountLabel.setText(String.valueOf(simulatePopulation.getCurrentCycleSymptomsCount()));
                    noSymptomsCountLabel.setText(String.valueOf(simulatePopulation.getCurrentCycleNoSymptomsCount()));
                }
            }
        };
        animationTimer.start();
    }


}