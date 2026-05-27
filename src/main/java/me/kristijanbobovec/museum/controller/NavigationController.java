package me.kristijanbobovec.museum.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import me.kristijanbobovec.museum.manager.DatabaseManager;
import me.kristijanbobovec.museum.manager.SceneManager;
import me.kristijanbobovec.museum.manager.UserSession;
import me.kristijanbobovec.museum.service.AuthService;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.Messages;
import me.kristijanbobovec.museum.util.XMLExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

public class NavigationController {

    private static final Logger log = LoggerFactory.getLogger(NavigationController.class);
    @FXML
    private Menu adminMenu;

    @FXML
    private void initialize() {
        toggleAdminOptions();
    }

    private void toggleAdminOptions() {
        boolean isAdmin = UserSession.INSTANCE.getSession().isAdmin();

        adminMenu.setVisible(isAdmin);
    }

    @FXML
    private void handleHome() {
        SceneManager.switchTo("home.fxml");
    }
    @FXML
    private void handleCountry() {
        SceneManager.switchTo("country-crud.fxml");
    }

    @FXML
    private void handleCity() {
        SceneManager.switchTo("city-crud.fxml");
    }

    @FXML
    private void handleCollection() {
        SceneManager.switchTo("collection-crud.fxml");
    }

    @FXML
    private void handleMuseum() {
        SceneManager.switchTo("museum-crud.fxml");
    }

    @FXML
    private void handleExhibit() {
        SceneManager.switchTo("exhibit-crud.fxml");
    }

    @FXML
    private void handleCurator() {
        SceneManager.switchTo("curator-crud.fxml");
    }

    @FXML
    private void handleLogout(){
        AuthService.logout();
        SceneManager.switchTo("login.fxml");
    }


    // ADMIN OPTIONS
    @FXML
    private void handleMuseumXMLExport() {
        try {
            boolean exported = XMLExport.exportMuseums();
            if (exported) AlertHelper.displayAlert(Alert.AlertType.INFORMATION, "Museums Exported", "Museums exported successfully");
        }catch (SQLException e) {
            log.error(Messages.Database.LOAD_FAIL, e);
        } catch (IOException e) {
            log.error(Messages.XML.EXPORT_FAIL.formatted("Museums", UserSession.INSTANCE.getSession().username()), e);
        }

    }

    @FXML
    private void handleDeleteAll() {
        try {
            DatabaseManager.resetDatabase();
            handleHome();

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, "Data Reset", "Data is reset successfully!");
        } catch (SQLException e) {
            log.error(Messages.Database.RESET_FAIL, e);
            AlertHelper.displayAlert(Alert.AlertType.WARNING, "Data reset failed", "Data reset failed!");
        }
    }
}
