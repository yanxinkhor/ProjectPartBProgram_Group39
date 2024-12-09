package com.example.projectpartbprogram_group39.Controllers.Form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class feedbackController implements Initializable {

    @FXML
    private Pane opacityPane;
    @FXML
    private TextArea feedbackText;
    @FXML
    private TextArea reportTextArea;
    @FXML
    private ComboBox<String> comb;
    @FXML
    ImageView rateBtn1;
    @FXML
    ImageView rateBtn2;
    @FXML
    ImageView rateBtn3;
    @FXML
    ImageView rateBtn4;
    @FXML
    ImageView rateBtn5;

    public String[] issues = {"Chat", "Statistics", "Tasks & Reminders", "My Activities", "Coaches"};

    private final javafx.scene.image.Image filledStar = new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/lightStar.png"));
    private final javafx.scene.image.Image emptyStar = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/noStar.png"));


    @FXML
    public void setOpacityPane(Pane opacityPane) {
        this.opacityPane = opacityPane;
    }

    public void goToFeedback(ActionEvent event) {
        openPopup("/com/example/projectpartbprogram_group39/View/generalFeedback-view.fxml", "General Feedback");
    }

    public void goToReport(ActionEvent event) {
        openPopup("/com/example/projectpartbprogram_group39/View/reportIssue-view.fxml", "Report Issue");
    }

    public void thankyouMsg(ActionEvent event) {
        if(!feedbackText.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Thank You for Your Feedback!", ButtonType.OK);
            alert.setTitle("Thank You");
            alert.showAndWait();
            feedbackText.clear();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Feedback cannot be empty", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void reportMsg(ActionEvent event) {
        if(!reportTextArea.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Thank You for Your Report!", ButtonType.OK);
            alert.setTitle("Thank You");
            alert.showAndWait();
            reportTextArea.clear();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Report cannot be empty", ButtonType.OK);
            alert.showAndWait();
        }
    }

    boolean isClicked = false;
    public void rate1(ActionEvent event) {
        if(isClicked){
            rateBtn1.setImage(filledStar);
            isClicked = false;
        }else {
            rateBtn1.setImage(emptyStar);
            isClicked = true;
        }
    }
    public void rate2(ActionEvent event) {
        if(isClicked){
            rateBtn2.setImage(filledStar);
            isClicked = false;
        }else {
            rateBtn2.setImage(emptyStar);
            isClicked = true;
        }
    }
    public void rate3(ActionEvent event) {
        if(isClicked){
            rateBtn3.setImage(filledStar);
            isClicked = false;
        }else {
            rateBtn3.setImage(emptyStar);
            isClicked = true;
        }
    }
    public void rate4(ActionEvent event) {
        if(isClicked){
            rateBtn4.setImage(filledStar);
            isClicked = false;
        }else {
            rateBtn4.setImage(emptyStar);
            isClicked = true;
        }
    }
    public void rate5(ActionEvent event) {
        if(isClicked){
            rateBtn5.setImage(filledStar);
            isClicked = false;
        }else {
            rateBtn5.setImage(emptyStar);
            isClicked = true;
        }
    }

    private void openPopup(String fxmlFileName, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            loader.load();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle(title);

            popupStage.setScene(new Scene(loader.getRoot()));
            popupStage.setResizable(false);

            popupStage.setOnCloseRequest(event -> {
                popupStage.close();
                if (opacityPane != null) {
                    opacityPane.setVisible(false);
                }
            });
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (comb != null) {
            comb.getItems().addAll(issues);
        } else {
            System.err.println("ComboBox 'comb' is not initialized. Check your FXML bindings.");
        }

    }
}
