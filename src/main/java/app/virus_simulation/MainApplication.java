package app.virus_simulation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    private final int APPLICATION_WIDTH = 700;
    private final int APPLICATION_HEIGHT = 520;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), APPLICATION_WIDTH, APPLICATION_HEIGHT);
        stage.setTitle("Virus Simulation");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}