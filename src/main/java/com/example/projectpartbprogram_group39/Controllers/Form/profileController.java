package com.example.projectpartbprogram_group39.Controllers.Form;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class profileController {

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

    @FXML
    public void initialize() {
        // Ensure text fields are non-editable initially
        makeFieldsEditable(false);

        // Event listener to enable editing
        editBtn.setOnAction(event -> makeFieldsEditable(true));

        // Event listener to save changes and disable editing
        saveBtn1.setOnAction(event -> {
            makeFieldsEditable(false);
            showSaveConfirmation();
        });
    }

    private void makeFieldsEditable(boolean editable) {
        profileUsername.setEditable(editable);
        profileUserid.setEditable(editable);
        profileEmail.setEditable(editable);
    }

    private void showSaveConfirmation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Save Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Profile saved successfully!");
        alert.showAndWait();
    }
}




