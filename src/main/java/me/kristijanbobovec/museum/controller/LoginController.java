package me.kristijanbobovec.museum.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import me.kristijanbobovec.museum.exception.WrongCredentialsException;
import me.kristijanbobovec.museum.manager.SceneManager;
import me.kristijanbobovec.museum.manager.UserSession;
import me.kristijanbobovec.museum.service.AuthService;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.Messages;

import java.sql.SQLException;

public final class LoginController extends AuthController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            setErrorLabelVisibility(false);
            authService.login(usernameField.getText(), passwordField.getText());

            // If login succeed
            updateUI();
        } catch (WrongCredentialsException e) {
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
    private void handleRegister(ActionEvent event) {
        SceneManager.switchTo("register.fxml");
    }

    protected void updateUI() {
        SceneManager.switchTo("home.fxml");

        AlertHelper.displayAlert(Alert.AlertType.INFORMATION,
                Messages.Auth.LOGIN_SUCCEED_HEADER,
                Messages.Auth.LOGIN_SUCCEED_CONTENT.
                        formatted(UserSession.INSTANCE.getSession().firstName(),
                                UserSession.INSTANCE.getSession().lastName()));
    }


}
