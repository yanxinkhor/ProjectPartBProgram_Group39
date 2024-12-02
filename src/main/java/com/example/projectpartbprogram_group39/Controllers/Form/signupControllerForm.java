package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Controllers.Service.signupController;
import com.example.projectpartbprogram_group39.DAO.TraineeDao.TraineeDaoImp;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.Encryption;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;



public class signupControllerForm {
    @FXML
    private TextField txtUsername, txtAge, txtPhoneNo, txtEmail, txtHeight, txtWeight;


    @FXML
    private RadioButton femaleRadioBtn, maleRadioBtn;

    @FXML
    private ToggleGroup Gender;

    @FXML
    private PasswordField txtPassword, txtPasswordConfirmed;

    @FXML
    private Button passwordBtn, passwordConfirmedBtn;

    @FXML
    private ImageView visiblePassword, visibleConfirmedPass;

    @FXML
    private TextField txtPasswordVisible, txtVisiblePasswordConfirmed;

    private final Image passwordVisible = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/password_visible.png"));
    private final Image passwordInvisible = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/password_invisible.png"));
    private final signupController signupService = new signupController();

    public void signup(ActionEvent e) throws IOException{
        String username = txtUsername.getText();
        String ageString = txtAge.getText();
        String phoneNoString = txtPhoneNo.getText();
        String email = txtEmail.getText();
        String gender = femaleRadioBtn.isSelected() ? "Female" : maleRadioBtn.isSelected() ? "Male" : "";
        String heightString = txtHeight.getText();
        String weightString = txtWeight.getText();
        String password = txtPassword.getText();
        String passwordConfirmed = txtPasswordConfirmed.getText();

        if(signupService.validateSignupInfo(username,ageString,phoneNoString,email,gender,heightString,
                weightString,password,passwordConfirmed)){
            showAlert.alert(Alert.AlertType.INFORMATION, "Signup successful!");
            back(e);
        }

    }
    boolean isVisible = false;

    public void displayPassword(ActionEvent e){

        Button sourceBtn = (Button)e.getSource();

        if(sourceBtn == passwordBtn){
            changeVisibilityIcon(txtPassword, txtPasswordVisible, visiblePassword);
        }else if(sourceBtn == passwordConfirmedBtn){
            changeVisibilityIcon(txtPasswordConfirmed, txtVisiblePasswordConfirmed, visibleConfirmedPass);
        }

    }

    public void changeVisibilityIcon(PasswordField passwordField, TextField textField, ImageView imgView){
        if(isVisible){
            passwordField.setText(textField.getText());
            textField.setVisible(false);
            passwordField.setVisible(true);

            imgView.setImage(passwordVisible);
            isVisible = false;

        }else{
            textField.setText(passwordField.getText());
            passwordField.setVisible(false);
            textField.setVisible(true);

            imgView.setImage(passwordInvisible);
            isVisible = true;
        }
    }

    public void back(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/login-view.fxml"));
        Scene loginScene = new Scene(loader.load());
        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.setScene(loginScene);
        stage.show();

    }

}
