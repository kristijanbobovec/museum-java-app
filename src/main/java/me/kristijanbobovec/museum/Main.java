package me.kristijanbobovec.museum;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import me.kristijanbobovec.museum.database.DatabaseInitialization;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.Messages;

import java.sql.SQLException;

public class Main extends Application {
    private static boolean databaseException = false;

    @Override
    public void init() {
        try {
            DatabaseInitialization.init();
        } catch (SQLException e) {
            databaseException = true;
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        if (databaseException) {
            AlertHelper.displayAlert(Alert.AlertType.ERROR,
                    Messages.Server.SERVER_ERROR_HEADER,
                    Messages.Server.SERVER_ERROR_CONTENT,
                    Platform::exit);
        }

        // Starting point of application!
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
