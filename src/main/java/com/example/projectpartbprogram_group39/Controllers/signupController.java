package com.example.projectpartbprogram_group39.Controllers;

import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class signupController {
    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtPhoneNo;

    @FXML
    private TextField txtEmail;

    @FXML
    private RadioButton femaleRadioBtn, maleRadioBtn;

    @FXML
    private TextField txtHeight;

    @FXML
    private TextField txtWeight;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPasswordConfirmed;

    @FXML
    private Button createBtn, backBtn;


    public void signup(ActionEvent e) {
        String username = txtUsername.getText();
        String ageString = txtAge.getText();
        String phoneNoString = txtPhoneNo.getText();
        String email = txtEmail.getText();
        String gender = femaleRadioBtn.isSelected() ? "Female" : maleRadioBtn.isSelected() ? "Male" : "";
        String heightString = txtHeight.getText();
        String weightString = txtWeight.getText();
        String password = txtPassword.getText();
        String passwordConfirmed = txtPasswordConfirmed.getText();


        if (username.isEmpty() || ageString.isEmpty() ||phoneNoString.isEmpty() || email.isEmpty() || gender.isEmpty() ||
                heightString.isEmpty() || weightString.isEmpty() || password.isEmpty() || passwordConfirmed.isEmpty()) {
            showAlert.alert(Alert.AlertType.ERROR, "Please fill in all the credential");
        }

        if (!password.equals(passwordConfirmed)) {
            showAlert.alert(Alert.AlertType.ERROR, "The password do not match!");
            txtPasswordConfirmed.setText("");
        }

        try{
            int age = Integer.parseInt(ageString);
            int phoneNo = Integer.parseInt(phoneNoString);
            double height = Double.parseDouble(heightString);
            double weight = Double.parseDouble(weightString);


        }catch(NumberFormatException event){
            showAlert.alert(Alert.AlertType.ERROR,"please enter a valid credential");
        }


    }

//    private boolean userExist(String username, String password){
//
//    }

   /* private void storeTraineeInfo(Trainee trainee) throws IOException{

    }*/

    public void back(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/FXML/login-view.fxml"));
        Scene loginScene = new Scene(loader.load());
        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.setScene(loginScene);
        stage.show();

    }


}
