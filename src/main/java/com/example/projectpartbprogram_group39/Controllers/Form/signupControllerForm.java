package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Controllers.Service.signupController;
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
import java.util.HashMap;
import java.util.Map;

public class signupControllerForm {
    @FXML
    private TextField txtUsername, txtAge, txtPhoneNo, txtEmail, txtHeight, txtWeight;

    @FXML
    private RadioButton femaleRadioBtn, maleRadioBtn;

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
    private final Map<String, Boolean> visibleMapping = new HashMap<>();

    public void initialize(){
        keyPressedHandler();
    }

    public void signup(ActionEvent e) throws IOException{
        String username = txtUsername.getText();
        String ageString = txtAge.getText();
        String phoneNoString = txtPhoneNo.getText();
        String email = txtEmail.getText();
        String gender = femaleRadioBtn.isSelected() ? "Female" : maleRadioBtn.isSelected() ? "Male" : "";
        String heightString = txtHeight.getText();
        String weightString = txtWeight.getText();
        String password = txtPassword.isVisible() ? txtPassword.getText() : txtPasswordVisible.getText();
        String passwordConfirmed = txtPasswordConfirmed.isVisible() ? txtPasswordConfirmed.getText() : txtVisiblePasswordConfirmed.getText();

        if(signupService.validateSignupInfo(username,ageString,phoneNoString,email,gender,heightString,
                weightString,password,passwordConfirmed)){
            showAlert.alert(Alert.AlertType.INFORMATION, "Signup successful!");
            back(e);
        }
    }

    public void displayPassword(ActionEvent e){

        Button sourceBtn = (Button)e.getSource();

        if(sourceBtn == passwordBtn){
            changeVisibilityIcon(txtPassword, txtPasswordVisible, visiblePassword);
        }else if(sourceBtn == passwordConfirmedBtn){
            changeVisibilityIcon(txtPasswordConfirmed, txtVisiblePasswordConfirmed, visibleConfirmedPass);
        }

    }

    public void changeVisibilityIcon(PasswordField passwordField, TextField textField, ImageView imgView){
        String keyField = "passwordField";
        boolean isVisible = visibleMapping.getOrDefault(keyField,false);


        if(isVisible){
            passwordField.setText(textField.getText());
            textField.setVisible(false);
            passwordField.setVisible(true);
            imgView.setImage(passwordInvisible);

        }else{
            textField.setText(passwordField.getText());
            passwordField.setVisible(false);
            textField.setVisible(true);
            imgView.setImage(passwordVisible);
        }
        visibleMapping.put(keyField,!isVisible);
    }

    public void back(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/login-view.fxml"));
        Scene loginScene = new Scene(loader.load());
        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.setScene(loginScene);
        stage.show();

    }

    public void keyPressedHandler(){
        txtUsername.setOnKeyPressed(event->{
            if("Enter".equals(event.getCode().toString())){
                txtAge.requestFocus();
            }
        });

        txtAge.setOnKeyPressed(event -> {
            if("Enter".equals(event.getCode().toString())){
                txtPhoneNo.requestFocus();
            }
        });

        txtPhoneNo.setOnKeyPressed(event->{
            if("Enter".equals(event.getCode().toString())){
                txtEmail.requestFocus();
            }
        });

        txtEmail.setOnKeyPressed(event->{
            if("Enter".equals(event.getCode().toString())){
                txtHeight.requestFocus();
            }
        });

        txtHeight.setOnKeyPressed(event->{
            if("Enter".equals(event.getCode().toString())){
                txtWeight.requestFocus();
            }
        });

        txtPassword.setOnKeyPressed(event->{
            if("Enter".equals(event.getCode().toString())){
                txtPasswordConfirmed.requestFocus();
            }
        });
    }

}
