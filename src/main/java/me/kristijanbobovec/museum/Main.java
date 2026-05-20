package me.kristijanbobovec.museum;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import me.kristijanbobovec.museum.database.DatabaseInitialization;
import me.kristijanbobovec.museum.event.AppSubscribers;
import me.kristijanbobovec.museum.factory.ControllerFactory;
import me.kristijanbobovec.museum.manager.SceneManager;
import me.kristijanbobovec.museum.repository.*;
import me.kristijanbobovec.museum.service.*;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class Main extends Application {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static boolean databaseException = false;

    @Override
    public void init() {
        try {
            DatabaseInitialization.init();
        } catch (SQLException e) {
            databaseException = true;
            log.error(Messages.Database.DATABASE_FAIL_CREATION, e);
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
        ExhibitRepository exhibitRepository = new ExhibitRepositoryImpl();
        CountryRepository countryRepository = new CountryRepositoryImpl();
        MuseumCollectionRepository museumCollectionRepository = new MuseumCollectionRepositoryImpl();
        CityRepository cityRepository = new CityRepositoryImpl();

        // Registring services
        UserService userService = new UserService(userRepository);
        AuthService authService = new AuthService(userService);
        MuseumService museumService = new MuseumService(museumRepository);
        ExhibitService exhibitService = new ExhibitService(exhibitRepository);
        MuseumCollectionService museumCollectionService = new MuseumCollectionService(museumCollectionRepository);
        CountryService countryService = new CountryService(countryRepository);
        CityService cityService = new CityService(cityRepository);

        ControllerFactory cf = new ControllerFactory(authService, museumService, exhibitService, museumCollectionService, countryService,
                cityService);

        // Set primary stage in stagemanager!
        SceneManager.setStage(stage);
        SceneManager.setControllerFactory(cf);

        SceneManager.switchTo("login.fxml");
        stage.show();
    }
}
