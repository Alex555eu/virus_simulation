package app.virus_simulation.inter;

public interface ISimulatePopulation {

    /**
     * Method is responsible for conducting one full cycle of simulation.<br/><br/>
     * It makes every person interact with one another and try to update their health states.<br/>
     * When one of the 2 persons interacting with each other is infected, and the other is not, the infected tries to infect a healthy one.<br/><br/>
     * Method updates the objects visible in the running application, each representing one person from population.
     *
     */
    void cycle();

    int getCurrentCycleHealthyCount();

    int getCurrentCycleImmuneCount();

    int getCurrentCycleSymptomsCount();

    int getCurrentCycleNoSymptomsCount();

}
