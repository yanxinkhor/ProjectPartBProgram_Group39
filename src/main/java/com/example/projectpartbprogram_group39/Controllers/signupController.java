package com.example.projectpartbprogram_group39.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class signupController {
    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtEmail;

    @FXML
    private RadioButton femaleRadioBtn,maleRadioBtn;

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


    public void signup(ActionEvent e){
        String username = txtUsername.getText();
        int age = Integer.parseInt(txtAge.getText());
        String email = txtEmail.getText();
        String gender = femaleRadioBtn.isSelected()? "Female" : maleRadioBtn.isSelected() ? "Male" : "";
        double height = Double.parseDouble(txtHeight.getText());
        double weight = Double.parseDouble(txtWeight.getText());
        String password = txtPassword.getText();
        String passwordConfirmed = txtPasswordConfirmed.getText();


    }

    public void back(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/FXML/login-view.fxml"));
        Scene loginScene = new Scene(loader.load());
        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.setScene(loginScene);
        stage.show();
    }

}
