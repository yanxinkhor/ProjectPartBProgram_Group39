package com.example.projectpartbprogram_group39.Controllers.Form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class settingsController implements Initializable {
    @FXML
    public Pane opacityPane;

    public void connectDevicePU(ActionEvent e) {
        opacityPane.setVisible(true);
        openPopup("/com/example/projectpartbprogram_group39/View/connectDevice-view.fxml");

    }

    public void deleteAccountPU(ActionEvent e) {
        opacityPane.setVisible(true);
        openPopup("/com/example/projectpartbprogram_group39/View/deleteAccount-view.fxml");


    }

    public void sendFeedbackPU(ActionEvent e) {
        opacityPane.setVisible(true);
        openPopup("/com/example/projectpartbprogram_group39/View/feedback-view.fxml");
    }

    private void openPopup(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            loader.load();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);

            popupStage.setScene(new Scene(loader.getRoot()));
            popupStage.setResizable(false);
            popupStage.setOnCloseRequest(event -> opacityPane.setVisible(false));
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        opacityPane.setVisible(false);
    }
}
