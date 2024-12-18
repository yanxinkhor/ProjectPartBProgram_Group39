package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.Controllers.Form.profileController;
import com.example.projectpartbprogram_group39.Controllers.Form.progressControllerForm;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.AESEncryption;
import com.example.projectpartbprogram_group39.Utils.TraineeSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class NavigationController implements Initializable {

    @FXML
    public ImageView profile_img;

    @FXML
    private Label welcomeText;

    @FXML
    public Text displayUsername;

    @FXML
    public Text displayEmail;

    @FXML
    private Pane contentPane;

    private Trainee trainee;
    private final Image femaleProfile = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girl_profile.png"));
    private final Image maleProfile = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guy_profile.png"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/dashboard-view.fxml"));
        Parent dashboardView = null;
        try {
            dashboardView = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentPane.getChildren().clear();
        contentPane.getChildren().add(dashboardView);

        trainee = TraineeSession.getInstance().getCurrentTrainee();
        if (trainee != null) {
            displayUsername.setText(trainee.getUsername());
            displayEmail.setText(trainee.getEmail());
            welcomeText.setText("Welcome " + trainee.getUsername() + "!");

            if (trainee.getGender().equals("Female")) {
                profile_img.setImage(femaleProfile);
            } else if (trainee.getGender().equals("Male")) {
                profile_img.setImage(maleProfile);
            }
        }
    }

    public void SwitchAction(ActionEvent e) throws IOException {
        String sourceBtn = ((Button) e.getSource()).getText();

        switch (sourceBtn) {
            case "Dashboard":
                switchPage("dashboard-view.fxml");
                welcomeText.setVisible(true);
                break;
            case "Exercises":
                switchPage("exercises-view.fxml");
                welcomeText.setVisible(false);
                break;
            case "Statistics":
                switchPage("progress-view.fxml");
                welcomeText.setVisible(false);
                break;
            case "Nutrition":
                switchPage("nutrition-view.fxml");
                welcomeText.setVisible(false);
                break;
            case "Coach":
                switchPage("coach-view.fxml");
                welcomeText.setVisible(false);
                break;
            case "Profile":
                switchPage("profile-view.fxml");
                welcomeText.setVisible(false);
                break;
            case "Settings":
                switchPage("settings-view.fxml");
                welcomeText.setVisible(false);
                break;
        }
    }


    @FXML
    public void logOut(ActionEvent e) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Logout Confirmation");

        if (alert.showAndWait().filter(ButtonType.YES::equals).isPresent()) {
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/example/projectpartbprogram_group39/View/login-view.fxml"))));
            stage.show();
        }
    }

    public void switchPage(String fileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/" + fileName));
        Parent View = loader.load();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(View);
        welcomeText.setVisible(true);
    }


}