package com.example.projectpartbprogram_group39.Controllers.Form;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class profileController {

    // Fields for the first box
    @FXML
    private Button editBtn;

    @FXML
    private TextField profileUsername;

    @FXML
    private TextField profileUserid;

    @FXML
    private TextField profileEmail;

    @FXML
    private Button saveBtn1;

    // Fields for the second box
    @FXML
    private Button editBtn2;

    @FXML
    private TextField profileUsername2;

    @FXML
    private TextField profileAge;

    @FXML
    private TextField profileGender;

    @FXML
    private TextField profileEmail2;

    @FXML
    private TextField profilePhoneNo;

    @FXML
    private TextField profileWeight;

    @FXML
    private TextField profileHeight;

    @FXML
    private Button saveBtn2;

    private boolean isEditing = false;

    @FXML
    public void initialize() {
        // Disable editing for all fields on page load
        disableEditingFirstBox();
        disableEditingSecondBox();
    }

    // First box methods
    @FXML
    private void handleEditButtonClick() {
        isEditing = true;
        enableEditingFirstBox();
    }

    @FXML
    private void handleSaveButtonClick() {
        isEditing = false;
        disableEditingFirstBox();
        showSuccessAlert();
    }

    private void enableEditingFirstBox() {
        profileUsername.setEditable(true);
        profileUserid.setEditable(true);
        profileEmail.setEditable(true);

        // Clear fields on click while editing
        profileUsername.setOnMouseClicked(event -> {
            if (isEditing) profileUsername.clear();
        });
        profileUserid.setOnMouseClicked(event -> {
            if (isEditing) profileUserid.clear();
        });
        profileEmail.setOnMouseClicked(event -> {
            if (isEditing) profileEmail.clear();
        });
    }

    private void disableEditingFirstBox() {
        profileUsername.setEditable(false);
        profileUserid.setEditable(false);
        profileEmail.setEditable(false);

        // Remove clear handlers
        profileUsername.setOnMouseClicked(null);
        profileUserid.setOnMouseClicked(null);
        profileEmail.setOnMouseClicked(null);
    }

    // Second box methods
    @FXML
    private void handleEditButton2Click() {
        isEditing = true;
        enableEditingSecondBox();
    }

    @FXML
    private void handleSaveButton2Click() {
        if (validateIntegerFields()) {
            isEditing = false;
            disableEditingSecondBox();
            showSuccessAlert();
        }
    }

    private void enableEditingSecondBox() {
        profileUsername2.setEditable(true);
        profileAge.setEditable(true);
        profileGender.setEditable(true);
        profileEmail2.setEditable(true);
        profilePhoneNo.setEditable(true);
        profileWeight.setEditable(true);
        profileHeight.setEditable(true);

        // Clear fields on click while editing
        profileUsername2.setOnMouseClicked(event -> {
            if (isEditing) profileUsername2.clear();
        });
        profileAge.setOnMouseClicked(event -> {
            if (isEditing) profileAge.clear();
        });
        profileGender.setOnMouseClicked(event -> {
            if (isEditing) profileGender.clear();
        });
        profileEmail2.setOnMouseClicked(event -> {
            if (isEditing) profileEmail2.clear();
        });
        profilePhoneNo.setOnMouseClicked(event -> {
            if (isEditing) profilePhoneNo.clear();
        });
        profileWeight.setOnMouseClicked(event -> {
            if (isEditing) profileWeight.clear();
        });
        profileHeight.setOnMouseClicked(event -> {
            if (isEditing) profileHeight.clear();
        });
    }

    private void disableEditingSecondBox() {
        profileUsername2.setEditable(false);
        profileAge.setEditable(false);
        profileGender.setEditable(false);
        profileEmail2.setEditable(false);
        profilePhoneNo.setEditable(false);
        profileWeight.setEditable(false);
        profileHeight.setEditable(false);

        // Remove clear handlers
        profileUsername2.setOnMouseClicked(null);
        profileAge.setOnMouseClicked(null);
        profileGender.setOnMouseClicked(null);
        profileEmail2.setOnMouseClicked(null);
        profilePhoneNo.setOnMouseClicked(null);
        profileWeight.setOnMouseClicked(null);
        profileHeight.setOnMouseClicked(null);
    }

    private boolean validateIntegerFields() {
        try {
            Integer.parseInt(profileAge.getText());
            Integer.parseInt(profilePhoneNo.getText());
            Integer.parseInt(profileWeight.getText());
            Integer.parseInt(profileHeight.getText());
            return true;
        } catch (NumberFormatException e) {
            showErrorAlert("All fields for Age, Weight, Height, and Phone Number must be integers!");
            return false;
        }
    }

    private void showSuccessAlert() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Update Successful");
            alert.setHeaderText(null);
            alert.setContentText("Profile updated successfully!");
            alert.showAndWait();
        });
    }

    private void showErrorAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}







