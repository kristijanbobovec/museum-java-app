package me.kristijanbobovec.museum;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import me.kristijanbobovec.museum.database.DatabaseInitialization;
import me.kristijanbobovec.museum.event.AppSubscribers;
import me.kristijanbobovec.museum.manager.ControllerFactory;
import me.kristijanbobovec.museum.manager.SceneManager;
import me.kristijanbobovec.museum.repository.MuseumRepository;
import me.kristijanbobovec.museum.repository.MuseumRepositoryImpl;
import me.kristijanbobovec.museum.repository.UserRepository;
import me.kristijanbobovec.museum.repository.UserRepositoryImpl;
import me.kristijanbobovec.museum.service.AuthService;
import me.kristijanbobovec.museum.service.MuseumService;
import me.kristijanbobovec.museum.service.UserService;
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
            e.printStackTrace();
        }

        // Must because singleton is lazy loaded
        AppSubscribers subscribers = AppSubscribers.INSTANCE;
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

        // Registring repositories
        UserRepository userRepository = new UserRepositoryImpl();
        MuseumRepository museumRepository = new MuseumRepositoryImpl();

        // Registring services
        UserService userService = new UserService(userRepository);
        AuthService authService = new AuthService(userService);
        MuseumService museumService = new MuseumService(museumRepository);

        ControllerFactory cf = new ControllerFactory(authService);

        // Set primary stage in stagemanager!
        SceneManager.setStage(stage);
        SceneManager.setControllerFactory(cf);

        SceneManager.switchTo("login.fxml");
        stage.show();
    }
}
