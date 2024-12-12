package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.Controllers.Form.progressControllerForm;
import com.example.projectpartbprogram_group39.Models.Trainee;
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
    private Trainee trainee;
    private String gender;
    @FXML
    public Text displayUsername;
    @FXML
    public Text displayEmail;
    @FXML
    private Pane contentPane;


    private final Image femaleProfile = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girl_profile.png"));
    private final Image maleProfile = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guy_profile.png"));

    public void displayUserDetails(Trainee trainee) {
        this.trainee = trainee;
        if (trainee != null) {
            displayUsername.setText(trainee.getUsername());
            displayEmail.setText(trainee.getEmail());
            gender = trainee.getGender();
        }
        displayWelcomeMessage();
        checkGender();
    }

    public void checkGender() {
        if (gender.equals("Male")) {
            profile_img.setImage(maleProfile);
        }else if (gender.equals("Female")) {
            profile_img.setImage(femaleProfile);
        }

    }
    public void displayWelcomeMessage(){
        if(welcomeText != null){
            welcomeText.setText("Welcome " + trainee.getUsername() +"!");
        }
    }

    @FXML
    public void goToDashboard(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/dashboard-view.fxml"));
        Parent dashboardView = loader.load();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(dashboardView);
        welcomeText.setVisible(true);
    }

    @FXML
    public void goToExercises(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/exercises-view.fxml"));
        Parent exercisesView = loader.load();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(exercisesView);
        welcomeText.setVisible(false);
    }

    @FXML
    public void goToStatistics(ActionEvent e) throws IOException {
        double height = trainee.getHeight();
        double weight = trainee.getWeight();
        int age = trainee.getAge();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/progress-view.fxml"));
        Parent statisticsView = loader.load();

        progressControllerForm progressCon = loader.getController();
        progressCon.traineeInfo(height,weight,age);

        contentPane.getChildren().clear();
        contentPane.getChildren().add(statisticsView);
        welcomeText.setVisible(false);
    }

    @FXML
    public void goToNutrition(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/nutrition-view.fxml"));
        Parent nutritionView = loader.load();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(nutritionView);
        welcomeText.setVisible(false);
    }

    @FXML
    public void goToCoach(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/coach-view.fxml"));
        Parent coachView = loader.load();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(coachView);
        welcomeText.setVisible(false);
    }

    @FXML
    public void goToProfile(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/profile-view.fxml"));
        Parent profileView = loader.load();

        contentPane.getChildren().clear();
        contentPane.getChildren().add(profileView);
        welcomeText.setVisible(false);
    }

    @FXML
    public void goToSettings(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/settings-view.fxml"));
        Parent settingsView = loader.load();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(settingsView);
        welcomeText.setVisible(false);
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
    }
}