package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Models.Trainee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;


public class dashboardController {

    @FXML
    private Label displayUsername;

    Trainee trainee;

    public void displayWelcomeMessage(){
        displayUsername.setText(trainee.getUsername() + "!");
    }


}
