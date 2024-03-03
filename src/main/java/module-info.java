module app.virus_simulation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens app.virus_simulation to javafx.fxml;
    exports app.virus_simulation;

    exports app.virus_simulation.memento;
    exports app.virus_simulation.impl;
    exports app.virus_simulation.person_state;
}