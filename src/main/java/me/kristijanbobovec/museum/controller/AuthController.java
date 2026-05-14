package me.kristijanbobovec.museum.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public abstract sealed class AuthController permits LoginController, RegisterController {
    @FXML protected Label errorLabel;

    protected void setErrorLabelVisibility(boolean state) {
        errorLabel.setManaged(state);
        errorLabel.setVisible(state);
    }

    protected abstract void updateUI();
}
