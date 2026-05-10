package me.kristijanbobovec.museum.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Hyperlink registerLink;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin(ActionEvent event) {
        // TODO: implement login logic
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        // TODO: navigate to register screen
    }
}
