package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.DAO.TraineeDaoImp;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.Encryption;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class loginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginBtn;

    private final TraineeDaoImp traineeDao = new TraineeDaoImp();

    public void goToSignup(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/signup-view.fxml"));
        Scene signupScene = new Scene(loader.load());
        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.setScene(signupScene);
        stage.show();

    }

    public void login(ActionEvent e) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String hashedPassword = Encryption.hashPassword(password);


        if (username.isEmpty() || password.isEmpty()) {
            showAlert.alert(Alert.AlertType.ERROR, "Please fill in all information");
            return;
        }

        List<Trainee> trainees = traineeDao.getAllTrainees();


        boolean isValid = false;

        for (Trainee trainee : trainees) {
            if (trainee.getUsername().equals(username) && trainee.getPassword().equals(hashedPassword)) {
                isValid = true;
                System.out.println(trainee.getUsername() + " " + trainee.getPassword());
                break;
            }
        }

        if (isValid) {
            showAlert.alert(Alert.AlertType.INFORMATION, "Login successful!");
            directToMainPAge(e);


        } else {
            showAlert.alert(Alert.AlertType.ERROR, "Invalid username or password.");
            usernameField.clear();
            passwordField.clear();
        }

    }

    public void directToMainPAge(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/dashboard-view.fxml"));
        Scene mainScene = new Scene(loader.load());
        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
        stage.show();

    }


}