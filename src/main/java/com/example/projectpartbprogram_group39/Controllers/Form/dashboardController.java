package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Models.Trainee;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class dashboardController {

@FXML
public Text username;
@FXML
public Text weight;
@FXML
public Text height;
@FXML
public Text age;
@FXML
public Text goalWeight;

private Trainee trainee;

    public void displayUserInfo(Trainee trainee) {
        this.trainee = trainee;
        if (trainee != null) {
            username.setText(trainee.getUsername());
            weight.setText(String.valueOf(trainee.getWeight()));
            height.setText(String.valueOf(trainee.getHeight()));
            age.setText(String.valueOf(trainee.getAge()));

        }
    }
}
