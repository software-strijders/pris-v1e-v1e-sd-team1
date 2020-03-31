package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.user.Administrator;
import models.user.UserFactory;
import enums.UserType;
import utils.FXUtils;

public class AddUserController {

    @FXML private ComboBox<UserType> userTypeComboBox;
    @FXML private TextField firstNameTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField emailTextField;
    @FXML private PasswordField passwordTextfield;

    @FXML
    private void initialize() {
        userTypeComboBox.setValue(UserType.STUDENT);
        userTypeComboBox.setItems(FXCollections.observableArrayList(UserType.values()));

        // Clear textfields after user creation
        firstNameTextField.clear();
        surnameTextField.clear();
        emailTextField.clear();
        passwordTextfield.clear();
    }

    @FXML
    public void onCancelButtonClick(ActionEvent event) {
        initialize();
    }

    @FXML
    public void onRegisterButtonClick(ActionEvent event) {
        if (emailTextField.getText().isEmpty() ||
                passwordTextfield.getText().isEmpty() ||
                firstNameTextField.getText().isEmpty() ||
                surnameTextField.getText().isEmpty()) {
            // Zie comment over de AlertType in CreateClassWindowController
            FXUtils.showAlert("Gebruiker niet aangemaakt", Alert.AlertType.INFORMATION);
            return;
        }
        try {
            Administrator.addUser(
                    UserFactory.create(emailTextField.getText(),
                            passwordTextfield.getText(),
                            firstNameTextField.getText(),
                            surnameTextField.getText(),
                            userTypeComboBox.getValue()));
            FXUtils.showAlert("Gebruiker aangemaakt", Alert.AlertType.INFORMATION);
            initialize();
        } catch (IllegalArgumentException exception) {
            // Zie comment over de AlertType in CreateClassWindowController
            FXUtils.showAlert(exception.getMessage(), Alert.AlertType.INFORMATION);
        }
    }
}
