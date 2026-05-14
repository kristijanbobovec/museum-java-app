package me.kristijanbobovec.museum.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private SceneManager() {}

    private static Stage stage;
    private static ControllerFactory controllerFactory;

    public static void setStage(Stage stage) {
        SceneManager.stage = stage;
    }

    public static void setControllerFactory(ControllerFactory cf) {
        SceneManager.controllerFactory = cf;
    }

    public static void switchTo(String fxml) {
        if (stage == null || controllerFactory == null)
            throw new IllegalStateException("You must first set the stage and inject the controller factory!");

        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/views/" + fxml));
            loader.setControllerFactory(controllerFactory::create);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load view: " + fxml, e);
        }
    }
}
