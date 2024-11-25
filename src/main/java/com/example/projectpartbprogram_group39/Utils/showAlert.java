package com.example.projectpartbprogram_group39.Utils;

import javafx.scene.control.Alert;

public class showAlert {

    public static void alert(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
