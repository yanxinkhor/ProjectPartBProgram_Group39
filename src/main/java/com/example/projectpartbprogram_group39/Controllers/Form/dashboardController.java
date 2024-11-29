package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Models.Trainee;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


public class dashboardController {

    @FXML
    public ImageView profile_img;
    @FXML
    private Text welcomeText;
    private Trainee trainee;
    private String gender;
    @FXML
    public Text displayUsername;
    @FXML
    public Text displayEmail;


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
            profile_img.setImage(new Image("/images/guy_profile.png"));
        }else if (gender.equals("Female")) {
            profile_img.setImage(new Image("/images/girl_profile.png"));
        }

    }
    public void displayWelcomeMessage(){
            if(welcomeText != null){
                welcomeText.setText("Welcome " + trainee.getUsername() +"!");
            }
        }



}





