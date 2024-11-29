package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.Models.Trainee;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


public class NavigationController {

    @FXML
    public ImageView profile_img;
    @FXML
    private Label welcomeText;
    private Trainee trainee;
    private String gender;
    @FXML
    public Text displayUsername;
    @FXML
    public Text displayEmail;

    private final Image femaleProfile = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girl_profile.png"));
    private final Image maleProfile = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guy_profile.png"));

    public void displayUserDetails(Trainee trainee) {
        this.trainee = trainee;
        if (trainee != null) {
            displayUsername.setText(trainee.getUsername());
            displayEmail.setText(trainee.getEmail());
            gender = trainee.getGender();
        }
        displayWelcomeMessage();
        checkGender();
    }

    public void checkGender() {
        if (gender.equals("Male")) {
            profile_img.setImage(maleProfile);
        }else if (gender.equals("Female")) {
            profile_img.setImage(femaleProfile);
        }

    }
    public void displayWelcomeMessage(){
        if(welcomeText != null){
            welcomeText.setText("Welcome " + trainee.getUsername() +"!");
        }
    }



}