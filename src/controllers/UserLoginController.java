package controllers;

import enums.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.user.User;
import utils.FXUtils;
import utils.Utils;
import java.util.InputMismatchException;

public class UserLoginController {

    @FXML private TextField emailField;
    @FXML private TextField passwordField;
    @FXML private Label userTypeLabel;

    private String email;
    private String password;
    private UserType userType;

    @FXML
    public void handleLogin(ActionEvent event) {
        try {
            this.obtainCredentials();
            this.logInUser();
            this.clearPassword();
            this.loadMainWindow(event);
        } catch (InputMismatchException exception) {
            FXUtils.showInfo(exception.getMessage());
        } catch (IllegalArgumentException exception) {
            this.clearPassword();
            FXUtils.showInfo("Het e-mailadres of wachtwoord is incorrect :(");
        } catch (Exception exception) {
            this.clearPassword();
            FXUtils.showError(exception);
        }
    }

    private void obtainCredentials() throws InputMismatchException {
        this.email = this.emailField.getText().trim();
        this.password = this.passwordField.getText();

        if (this.email.isEmpty())
            throw new InputMismatchException("Het e-mailadres ontbreekt :(");
        else if (!Utils.isEmailValid(this.email))
            throw new InputMismatchException("Het e-mailadres is ongeldig :(");
        else if (this.password.isEmpty())
            throw new InputMismatchException("Het wachtwoord ontbreekt :(");
    }

    private void logInUser() throws IllegalArgumentException {
        User.setLoggedInUser(User.authenticateUser(this.email, this.password, this.userType));
    }

    private void clearPassword() {
        this.passwordField.clear();
        this.password = null;
    }

    private void loadMainWindow(ActionEvent event) {
        FXUtils.loadView("Hoofdmenu", "/views/Mytendance.fxml", event);
    }

    @FXML
    public void handleSwitchRole(ActionEvent event) {
        FXUtils.loadView("Selecteer rol", "/views/RoleSelection.fxml", event);
    }

    public void setUserType(UserType type) {
        this.userTypeLabel.setText(type.toString());
        this.userType = type;
    }
}
