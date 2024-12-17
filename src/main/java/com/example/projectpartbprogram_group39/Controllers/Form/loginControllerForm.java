package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Controllers.Service.NavigationController;
import com.example.projectpartbprogram_group39.Controllers.Service.loginController;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.TraineeSession;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class loginControllerForm {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ImageView invisibleImg;

    @FXML
    private TextField passwordTxtField;

    private final Image passVisible = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/password_visible.png"));
    private final Image passInvisible = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/password_invisible.png"));
    private final loginController loginService = new loginController();
    private final Map<String, Boolean> visibleMapping = new HashMap<>();

    public void goToSignup(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/signup-view.fxml"));
        Scene signupScene = new Scene(loader.load());
        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.setScene(signupScene);
        stage.show();

    }

    public void login(ActionEvent e) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (loginService.validateUser(username, password)) {
            if(username.equals("Admin")){
                showAlert.alert(Alert.AlertType.INFORMATION,"Login successful! Welcome Admin");
                directToMainPage(e, username, password);
            }else {
                showAlert.alert(Alert.AlertType.INFORMATION, "Login successful! Welcome user: " + username);
                directToMainPage(e, username, password);
            }
        }

    }


    public void displayPassword(){
        String fieldkey = "passwordTxtField";
        boolean isVisible = visibleMapping.getOrDefault(fieldkey,false);

        if(isVisible){
            passwordField.setText(passwordTxtField.getText());
            passwordField.setVisible(true);
            passwordTxtField.setVisible(false);
            invisibleImg.setImage(passInvisible);

        }else{
            passwordTxtField.setText(passwordField.getText());
            passwordTxtField.setVisible(true);
            passwordField.setVisible(false);
            invisibleImg .setImage(passVisible);

        }

        visibleMapping.put(fieldkey, !isVisible);
    }


    public void directToMainPage(ActionEvent e, String username, String password) throws Exception {
        Trainee loggedInTrainee = loginController.getLoggedInTrainee(username, password);

        if (loggedInTrainee != null) {
            TraineeSession.getInstance().setCurrentTrainee(loggedInTrainee);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/navigation-view.fxml"));
            BorderPane root = loader.load();
            Scene mainScene = new Scene(root);
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.setScene(mainScene);
            stage.show();
        }
    }
}