package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Controllers.Service.ProfileController;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.CoachMapper;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.TraineeMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Coach;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.AESEncryption;
import com.example.projectpartbprogram_group39.Utils.TraineeSession;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileControllerForm implements Initializable {

    @FXML
    private Label profileUsernameMain;

    @FXML
    private TextField displayUsername,displayEmail,profileUsername,profileAge,profileEmail,
            profilePhoneNo,profileWeight,profileHeight,profilePassword;

    @FXML
    private ComboBox<String> profileGender;

    @FXML
    private Button saveBtn;

    @FXML
    private ImageView genderImg;

    @FXML
    private HBox joinedClassCtn;

    String[] gender = {"Female","Male"};
    private boolean isEditing = false;
    private Trainee trainee;

    private ProfileController profileController = new ProfileController();
    Image maleImg = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guy_profile.png"));
    Image femaleImg = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girl_profile.png"));

    Image coach1 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_4.png"));
    Image coach2 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_2.png"));
    Image coach3 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_3.png"));
    Image coach4 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_1.png"));
    Image coach5 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girlCoach_2.png"));
    Image coach6 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girlCoach_1.png"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveBtn.setDisable(true);
        profileGender.getItems().addAll(gender);
        trainee = profileController.getCurrentTrainee();
        if (trainee != null) {
            updateProfileFields();
        }
        try {
            displayJoinedClass();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateProfileFields() {
        profileUsernameMain.setText(trainee.getUsername() + "'s Profile");
        displayUsername.setText(trainee.getUsername());
        displayEmail.setText(trainee.getEmail());
        profileUsername.setText(trainee.getUsername());
        profileAge.setText(String.valueOf(trainee.getAge()));
        profileGender.setValue(trainee.getGender());
        profileEmail.setText(trainee.getEmail());
        profilePhoneNo.setText(trainee.getPhoneNo());
        profileWeight.setText(String.valueOf(trainee.getWeight()));
        profileHeight.setText(String.valueOf(trainee.getHeight()));

        if (!trainee.getPassword().equals("admin1234")) {
            String decryptedPassword = profileController.decryptPassword(trainee.getPassword());
            profilePassword.setText(decryptedPassword);
        } else {
            profilePassword.setText("admin1234");
        }

        updateGenderImage();
    }

    private void updateGenderImage() {
        if (trainee.getGender().equals("Female")) {
            genderImg.setImage(femaleImg);
        } else if (trainee.getGender().equals("Male")) {
            genderImg.setImage(maleImg);
        }
    }

    @FXML
    private void handleEditButtonClick() {
        if (trainee.getUsername().equals("Admin") && trainee.getPassword().equals("admin1234")) {
            showAlert.alert(AlertType.WARNING, "You cannot edit the Admin profile.");
            return;
        }
        isEditing = true;
        enableEdit();
        saveBtn.setDisable(false);
    }

    @FXML
    private void handleSaveButtonClick() {
        String username = profileUsername.getText();
        String email = profileEmail.getText();
        String ageStr = profileAge.getText();
        String gender = profileGender.getValue();
        String phoneNo = profilePhoneNo.getText();
        String weightStr = profileWeight.getText();
        String heightStr = profileHeight.getText();
        String password = profilePassword.getText();

        if (profileController.validateProfileFields(username, email, ageStr, gender, phoneNo, weightStr, heightStr)) {
            try {
                profileController.updateProfile(username, ageStr, gender, phoneNo, email, weightStr, heightStr, password);
                showAlert.alert(AlertType.INFORMATION, "Profile updated successfully!");
                disableEdit();
                saveBtn.setDisable(true);
                profileUsernameMain.setText(username + "'s Profile");
                displayUsername.setText(username);
                displayEmail.setText(email);
                if(gender.equals("Male")){
                    genderImg.setImage(maleImg);
                }else{
                    genderImg.setImage(femaleImg);
                }

            } catch (NumberFormatException e) {
                showAlert.alert(AlertType.ERROR, "Please enter valid values.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert.alert(AlertType.ERROR, "Please fill in all the fields.");
        }
    }

    private void enableEdit() {
        profileUsername.setStyle("-fx-background-color:white;");
        profileAge.setStyle("-fx-background-color:white;");
        profileEmail.setStyle("-fx-background-color:white;");
        profilePhoneNo.setStyle("-fx-background-color:white;");
        profileWeight.setStyle("-fx-background-color:white;");
        profileHeight.setStyle("-fx-background-color:white;");

        profileUsername.setEditable(true);
        profileAge.setEditable(true);
        profileGender.setDisable(false);
        profileEmail.setEditable(true);
        profilePhoneNo.setEditable(true);
        profileWeight.setEditable(true);
        profileHeight.setEditable(true);

    }

    private void disableEdit() {
        profileUsername.setStyle("-fx-background-color:transparent;");
        profileAge.setStyle("-fx-background-color:transparent;");
        profileEmail.setStyle("-fx-background-color:transparent;");
        profilePhoneNo.setStyle("-fx-background-color:transparent;");
        profileWeight.setStyle("-fx-background-color:transparent;");
        profileHeight.setStyle("-fx-background-color:transparent;");

        profileUsername.setEditable(false);
        profileAge.setEditable(false);
        profileGender.setDisable(true);
        profileEmail.setEditable(false);
        profilePhoneNo.setEditable(false);
        profileWeight.setEditable(false);
        profileHeight.setEditable(false);
    }

    public void displayJoinedClass() throws IOException {
        joinedClassCtn.getChildren().clear();
        List<Coach> lists = profileController.getCoachesList();

        if (lists.isEmpty()) {
            Label noClasses = new Label("No classes joined yet.");
            joinedClassCtn.getChildren().add(noClasses);
            return;
        }

        for(Coach list:lists){
            if(list.getIsJoined()){
                ImageView coachImg = new ImageView();

                switch(list.getName()){
                    case "Ethan Carter":
                        coachImg.setImage(coach1);
                        break;
                    case "Lucas Bennett":
                        coachImg.setImage(coach2);
                        break;
                    case "Max Donovan":
                        coachImg.setImage(coach3);
                        break;
                    case "Nathan Pierce":
                        coachImg.setImage(coach4);
                        break;
                    case "Olivia Harper":
                        coachImg.setImage(coach5);
                        break;
                    case "Mia Collins":
                        coachImg.setImage(coach6);
                        break;
                }

                coachImg.setFitHeight(100);
                coachImg.setFitWidth(100);
                joinedClassCtn.getChildren().add(coachImg);
            }
        }
    }

}