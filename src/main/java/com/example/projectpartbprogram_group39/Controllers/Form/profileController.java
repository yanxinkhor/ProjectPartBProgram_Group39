package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.TraineeMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.AESEncryption;
import com.example.projectpartbprogram_group39.Utils.TraineeSession;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class profileController implements Initializable {

    @FXML
    private TextField displayUsername;

    @FXML
    private TextField displayEmail;


    @FXML
    private TextField profileUsername;

    @FXML
    private TextField profileAge;

    @FXML
    private ComboBox<String> profileGender;

    @FXML
    private TextField profileEmail;

    @FXML
    private TextField profilePhoneNo;

    @FXML
    private TextField profileWeight;

    @FXML
    private TextField profileHeight;

    @FXML
    private TextField profilePassword;

    @FXML
    private ImageView genderImg;

    String[] gender = {"Female","Male"};
    private boolean isEditing = false;
    private Trainee trainee;
    private DaoInterface<Trainee> profileDao = new DaoImplement<>("userInfo.txt",new TraineeMapper());
    private static final String ENCRYPTION_KEY = "1234567890123456";
    private static final AESEncryption aesEncryption = new AESEncryption(ENCRYPTION_KEY);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        trainee = TraineeSession.getInstance().getCurrentTrainee();
        if (trainee != null) {
            displayUsername.setText(trainee.getUsername());
            displayEmail.setText(trainee.getEmail());
            profileUsername.setText(trainee.getUsername());

            profileAge.setText(String.valueOf(trainee.getAge()));
            profileGender.setValue(trainee.getGender());
            profileEmail.setText(trainee.getEmail());
            profilePhoneNo.setText(trainee.getPhoneNo());
            profileWeight.setText(String.valueOf(trainee.getWeight()));
            profileHeight.setText(String.valueOf(trainee.getHeight()));
            String decryptedPassword = null;
            try {
                decryptedPassword = aesEncryption.decrypt(trainee.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
            }
            profilePassword.setText(decryptedPassword);
        } else {
            System.out.println("Trainee is null, cannot display profile.");
        }

        profileGender.getItems().addAll(gender);
    }

    @FXML
    private void handleEditButtonClick() {
        isEditing = true;
        enableEditingSecondBox();
    }

    @FXML
    private void handleSaveButtonClick() {
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
            disableEditingSecondBox();
        } else {
            showAlert.alert(AlertType.ERROR,"Please fill in all fields in the second section.");
        }
    }

    private void enableEditingSecondBox() {
        profileUsername.setEditable(true);
        profileAge.setEditable(true);
        profileGender.setEditable(true);
        profileEmail.setEditable(true);
        profilePhoneNo.setEditable(true);
        profileWeight.setEditable(true);
        profileHeight.setEditable(true);

        // Clear fields on click while editing
        profileUsername.setOnMouseClicked(event -> {
            if (isEditing) profileUsername.clear();
        });
        profileAge.setOnMouseClicked(event -> {
            if (isEditing) profileAge.clear();
        });
        profileGender.setOnMouseClicked(event -> {
            if (isEditing) profileGender.getSelectionModel().clearSelection();
        });
        profileEmail.setOnMouseClicked(event -> {
            if (isEditing) profileEmail.clear();
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
        profileUsername.setEditable(false);
        profileAge.setEditable(false);
        profileGender.setEditable(false);
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


}