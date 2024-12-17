package com.example.projectpartbprogram_group39.Controllers.Form;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class profileController implements Initializable {

    @FXML
    private Label profileUsernameMain;

    @FXML
    private TextField displayUsername,displayEmail,profileUsername,profileAge,profileEmail,
            profilePhoneNo,profileWeight,profileHeight,profilePassword;

    @FXML
    private ComboBox<String> profileGender;

    @FXML
    private ImageView genderImg;

    @FXML
    private HBox joinedClassCtn;

    String[] gender = {"Female","Male"};
    private boolean isEditing = false;
    private Trainee trainee;
    private DaoInterface<Trainee> profileDao = new DaoImplement<>("userInfo.txt",new TraineeMapper());
    private DaoInterface<Coach> coachDao = new DaoImplement<>("coaches.txt",new CoachMapper());
    private static final String ENCRYPTION_KEY = "1234567890123456";
    private static final AESEncryption aesEncryption = new AESEncryption(ENCRYPTION_KEY);
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
        trainee = TraineeSession.getInstance().getCurrentTrainee();
        if (trainee != null) {
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
                String decryptedPassword = null;
                try {
                    decryptedPassword = aesEncryption.decrypt(trainee.getPassword());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                profilePassword.setText(decryptedPassword);
            }else{
                profilePassword.setText("admin1234");
            }
        }

        if(trainee.getGender().equals("Female")){
            genderImg.setImage(femaleImg);
        }else if(trainee.getGender().equals("Male")){
            genderImg.setImage(maleImg);
        }


        profileGender.getItems().addAll(gender);
        try {
            displayJoinedClass();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleEditButtonClick() {
        isEditing = true;
        enableEdit();
    }

    @FXML
    private void handleSaveButtonClick() {

        if (trainee.getUsername().equals("Admin") &&
                !profileUsername.getText().equals("Admin")) {
            showAlert.alert(AlertType.ERROR, "Username for Admin cannot be changed.");
            profileUsername.setText("Admin");
            disableEdit();
            return;
        }
        if (trainee.getUsername().equals("Admin") &&
                !profilePassword.getText().equals("admin1234")) {
            showAlert.alert(AlertType.ERROR, "Password for Admin cannot be changed.");
            profilePassword.setText("admin1234");
            disableEdit();
            return;
        }

        if (validateBox()) {

            String username = profileUsername.getText();
            String email = profileEmail.getText();
            String ageStr = profileAge.getText();
            String gender = profileGender.getValue();
            String phoneNo = profilePhoneNo.getText();
            String weightStr =profileWeight.getText();
            String heightStr = profileHeight.getText();
            String password = profilePassword.getText() ;

            try{
                int age = Integer.parseInt(ageStr);
                double height = Double.parseDouble(heightStr);
                double weight = Double.parseDouble(weightStr);

                String encryptedPassword = aesEncryption.encrypt(password);

                Trainee updatedTrainee = new Trainee(username,age,gender,phoneNo,email,height,weight,encryptedPassword);
                if (trainee != null) {
                    profileDao.update(trainee, updatedTrainee);
                    showAlert.alert(AlertType.INFORMATION, "Profile updated successfully!");
                    trainee = updatedTrainee;

                    TraineeSession.getInstance().setCurrentTrainee(updatedTrainee);
                } else {
                    showAlert.alert(AlertType.ERROR, "No trainee found for update.");
                };

            }catch(NumberFormatException e){
                showAlert.alert(AlertType.ERROR,"Please enter a valid value");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            isEditing = false;
            disableEdit();
        } else {
            showAlert.alert(AlertType.ERROR,"Please fill in all fields in the second section.");
        }
    }

    private void enableEdit() {
        profileUsername.setEditable(true);
        profileAge.setEditable(true);
        profileGender.setDisable(false);
        profileEmail.setEditable(true);
        profilePhoneNo.setEditable(true);
        profileWeight.setEditable(true);
        profileHeight.setEditable(true);

    }

    private void disableEdit() {
        profileUsername.setEditable(false);
        profileAge.setEditable(false);
        profileGender.setDisable(true);
        profileEmail.setEditable(false);
        profilePhoneNo.setEditable(false);
        profileWeight.setEditable(false);
        profileHeight.setEditable(false);


        profileUsername.setOnMouseClicked(null);
        profileAge.setOnMouseClicked(null);
        profileGender.setOnMouseClicked(null);
        profileEmail.setOnMouseClicked(null);
        profilePhoneNo.setOnMouseClicked(null);
        profileWeight.setOnMouseClicked(null);
        profileHeight.setOnMouseClicked(null);
    }

    private boolean validateBox() {
        return !profileUsername.getText().isEmpty() &&
                !profileAge.getText().isEmpty() &&
                !profileGender.getValue().isEmpty() &&
                !profileEmail.getText().isEmpty() &&
                !profilePhoneNo.getText().isEmpty() &&
                !profileWeight.getText().isEmpty() &&
                !profileHeight.getText().isEmpty();
    }

    public void displayJoinedClass() throws IOException {
        joinedClassCtn.getChildren().clear();
        List<Coach> lists = coachDao.getAll();

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