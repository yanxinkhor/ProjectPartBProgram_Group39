package com.example.projectpartbprogram_group39.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;



public class loginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginBtn;

    @FXML
    private Button signupBtn;

    public void goToSignup(ActionEvent e) throws IOException{
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/FXML/signup-view.fxml"));
         Scene signupScene = new Scene(loader.load());
         Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
         stage.setScene(signupScene);
         stage.show();

    }

    public void login(ActionEvent e){
        String username = usernameField.getText();
        String password = passwordField.getText();

    }


}