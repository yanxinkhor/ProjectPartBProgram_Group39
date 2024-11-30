package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Controllers.Service.NavigationController;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;


public class loginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginBtn, visibilityBtn;

    @FXML
    ImageView invisibleImg;

    @FXML
    private TextField passwordTxtField;


    private final Image passVisible = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/password_visible.png"));
    private final Image passInvisible = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/password_invisible.png"));
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
                break;
            }
        }

        if (isValid) {
            showAlert.alert(Alert.AlertType.INFORMATION, "Login successful!");
            directToMainPage(e);


        } else {
            showAlert.alert(Alert.AlertType.ERROR, "Invalid username or password.");
            usernameField.clear();
            passwordField.clear();
        }

    }

    boolean isVisible = false;
    public void displayPassword(){

        if(isVisible){
            passwordField.setText(passwordTxtField.getText());
            passwordField.setVisible(true);
            passwordTxtField.setVisible(false);

            invisibleImg.setImage(passInvisible);
            isVisible = false;
        }else{
            passwordTxtField.setText(passwordField.getText());
            passwordTxtField.setVisible(true);
            passwordField.setVisible(false);

            invisibleImg .setImage(passVisible);
            isVisible = true;

        }
    }


    public void directToMainPage(ActionEvent e) throws IOException {
        Trainee loggedInTrainee = null;
        String username = usernameField.getText();
        String password = passwordField.getText();
        String hashedPassword = Encryption.hashPassword(password);

        List<Trainee> trainees = traineeDao.getAllTrainees();
        for (Trainee trainee : trainees) {
            if (trainee.getUsername().equals(username) && trainee.getPassword().equals(hashedPassword)) {
                loggedInTrainee = trainee;  // Get the logged-in trainee object
                break;
            }
        }

        if (loggedInTrainee != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/navigation-view.fxml"));
            BorderPane root = loader.load();

            NavigationController navigationController = loader.getController();
            navigationController.displayUserDetails(loggedInTrainee);

            Scene mainScene = new Scene(root);
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.setScene(mainScene);
            stage.show();

        }
    }

}