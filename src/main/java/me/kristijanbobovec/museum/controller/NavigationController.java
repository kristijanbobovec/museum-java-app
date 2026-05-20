package me.kristijanbobovec.museum.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import me.kristijanbobovec.museum.manager.SceneManager;
import me.kristijanbobovec.museum.manager.UserSession;
import me.kristijanbobovec.museum.service.AuthService;

public class NavigationController {

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


    /// ADMIN OPTIONS
    @FXML
    private void handleCreateBackup() {
        System.out.println("kreiram backup");
    }

    @FXML
    private void handleLoadBackup() {
        System.out.println("loadam backup");
    }

    @FXML
    private void handleDeleteAll() {
        System.out.println("deleted all database");
    }
}
