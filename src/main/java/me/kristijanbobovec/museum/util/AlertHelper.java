package me.kristijanbobovec.museum.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertHelper {
    private AlertHelper() {}

    private static Alert createAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type, message);
        alert.setTitle(title);
        return alert;
    }

    public static void displayAlert(Alert.AlertType type, String title, String message) {
        createAlert(type, title, message).showAndWait();
    }

    public static void displayAlert(Alert.AlertType type, String title, String message, Runnable onOk) {
        Optional<ButtonType> result = createAlert(type, title, message).showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            onOk.run();
        }
    }
}
