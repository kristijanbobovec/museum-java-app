package me.kristijanbobovec.museum.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import me.kristijanbobovec.museum.exception.ValidationException;
import me.kristijanbobovec.museum.manager.SceneManager;
import me.kristijanbobovec.museum.service.AuthService;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.Messages;

import java.sql.SQLException;

public final class RegisterController extends AuthController {

    private final AuthService authService;

    public RegisterController(AuthService authService) {
        this.authService = authService;
    }

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            setErrorLabelVisibility(false);
            authService.register(usernameField.getText(), emailField.getText(), firstNameField.getText(), lastNameField.getText(), passwordField.getText());
            updateUI();
        } catch (ValidationException e) {
            setErrorLabelVisibility(true);
            errorLabel.setText(e.getMessage());
        } catch (SQLException e) {
            AlertHelper.displayAlert(Alert.AlertType.ERROR,
                    Messages.Server.SERVER_ERROR_HEADER,
                    Messages.Server.SERVER_ERROR_CONTENT,
                    Platform::exit);
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        // navigation to the login screen
        SceneManager.switchTo("login.fxml");
    }

    protected void updateUI() {
        SceneManager.switchTo("login.fxml");

        AlertHelper.displayAlert(Alert.AlertType.INFORMATION,
                Messages.Auth.REGISTER_SUCCEED_HEADER,
                Messages.Auth.REGISTER_SUCCEED_CONTENT);
    }
}
