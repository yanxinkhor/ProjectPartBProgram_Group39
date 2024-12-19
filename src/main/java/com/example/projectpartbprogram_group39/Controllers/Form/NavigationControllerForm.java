package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Controllers.Service.NavigationController;
import com.example.projectpartbprogram_group39.Models.Trainee;
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


public class NavigationControllerForm implements Initializable {

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

    private final NavigationController navigationControllerLogic = new NavigationController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigationControllerLogic.initializeUI(contentPane, welcomeText, profile_img, displayUsername, displayEmail);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/dashboard-view.fxml"));
        Parent dashboardView;
        try {
            dashboardView = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentPane.getChildren().clear();
        contentPane.getChildren().add(dashboardView);
    }

    @FXML
    public void SwitchAction(ActionEvent e) throws IOException {
        String sourceBtn = ((Button) e.getSource()).getText();
        navigationControllerLogic.handleSwitchAction(sourceBtn, contentPane, welcomeText);
    }

    @FXML
    public void logOut(ActionEvent e) throws IOException {
        navigationControllerLogic.logOut(e);
    }


}