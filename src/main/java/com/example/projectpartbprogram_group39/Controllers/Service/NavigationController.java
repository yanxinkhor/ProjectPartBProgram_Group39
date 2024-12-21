package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.DevicesMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Devices;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.TraineeSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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
import java.util.List;

public class NavigationController {


    private Trainee trainee;
    private final Image femaleProfile = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girl_profile.png"));
    private final Image maleProfile = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guy_profile.png"));
    private DaoInterface<Devices> devicesDao = new DaoImplement<>("devices.txt",new DevicesMapper());

    public void initializeUI(Pane contentPane, Label welcomeText, ImageView profile_img, Text displayUsername, Text displayEmail)  {
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

    public void handleSwitchAction(String sourceBtn, Pane contentPane, Label welcomeText) throws IOException {
        switch (sourceBtn) {
            case "Dashboard":
                switchPage("dashboard-view.fxml", contentPane);
                welcomeText.setVisible(true);
                break;
            case "Exercises":
                switchPage("exercises-view.fxml", contentPane);
                welcomeText.setVisible(false);
                break;
            case "Statistics":
                switchPage("progress-view.fxml", contentPane);
                welcomeText.setVisible(false);
                break;
            case "Nutrition":
                switchPage("nutrition-view.fxml", contentPane);
                welcomeText.setVisible(false);
                break;
            case "Coach":
                switchPage("coach-view.fxml", contentPane);
                welcomeText.setVisible(false);
                break;
            case "Profile":
                switchPage("profile-view.fxml", contentPane);
                welcomeText.setVisible(false);
                break;
            case "Settings":
                switchPage("settings-view.fxml", contentPane);
                welcomeText.setVisible(false);
                break;
        }
    }

    public void logOut(ActionEvent e) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Logout Confirmation");

        if (alert.showAndWait().filter(ButtonType.YES::equals).isPresent()) {
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/example/projectpartbprogram_group39/View/login-view.fxml"))));
            stage.show();
        }
    }

    public void switchPage(String fileName, Pane contentPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/" + fileName));
        Parent View = loader.load();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(View);
    }

    public List<Devices> getAllDeviceList()throws IOException{
        return devicesDao.getAll();
    }
}
