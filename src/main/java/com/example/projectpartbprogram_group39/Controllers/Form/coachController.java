package com.example.projectpartbprogram_group39.Controllers.Form;


import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class coachController {

    @FXML
    private Button joinBtn1;

    @FXML
    private Button joinBtn2;

    @FXML
    private Button joinBtn3;

    @FXML
    private Button joinBtn4;

    @FXML
    private Button joinBtn5;

    @FXML
    private Button joinBtn6;

    @FXML
    private Button viewDetail1, viewDetail2, viewDetail3, viewDetail4, viewDetail5, viewDetail6;

    @FXML
    private Button leaveBtn1, leaveBtn2, leaveBtn3, leaveBtn4, leaveBtn5, leaveBtn6;


    Image coach1 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_4.png"));
    Image coach2 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_2.png"));
    Image coach3 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_3.png"));
    Image coach4 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_1.png"));
    Image coach5 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girlCoach_2.png"));
    Image coach6 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girlCoach_1.png"));


    public void viewDetail(ActionEvent e) throws IOException {
        Button sourceBtn = (Button) e.getSource();  // Get the button that was clicked

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/coachDetail-view.fxml"));
        Scene scene = new Scene(loader.load());


        coachDetailController controller = loader.getController();


        if (sourceBtn == viewDetail1) {
            controller.coachesDetail(coach1, "Ethan Carter", "Weight Lifting Expert", "45 minutes",
                    "- Monday", "- Thursday", "- Sunday", "8:00am - 8:45am", "5:00pm - 5:45pm", "10:30am - 11:20am");
        } else if (sourceBtn == viewDetail2) {
            controller.coachesDetail(coach2, "Lucas Bennett", "Fitness Expert", "60 minutes",
                    "- Tuesday", "- Friday ", "- Saturday", "7:00am - 7:45am",
                    "6:00pm - 6:45pm", "9:00am - 9:45am");
        } else if (sourceBtn == viewDetail3) {
            controller.coachesDetail(coach3, "Max Donovan", "Cardio Expert", "50 minutes", "- Wednesday",
                    "- Thursday", "- Sunday", "9:00am - 9:45am", "4:00pm - 4:45pm",
                    "1:00pm - 1:45pm");
        } else if (sourceBtn == viewDetail4) {
            controller.coachesDetail(coach4, "Nathan Pierce", "Boxing Specialist", "40 minutes",
                    "- Monday", "- Wednesday", "- Saturday", "6:00pm - 6:45pm",
                    "8:00am - 8:45am", "10:30am - 11:15am");
        } else if (sourceBtn == viewDetail5) {
            controller.coachesDetail(coach5, "Olivia Harper", "Pilates Specialist", "55 minutes",
                    "- Tuesday", "- Friday", "- Sunday", "5:30pm - 6:15pm",
                    "7:00pm - 7:45pm", "11:00am - 11:45am");
        } else if (sourceBtn == viewDetail6) {
            controller.coachesDetail(coach6, "Mia Collins", "Yoga Specialist", "30 minutes",
                    "- Monday", "- Wednesday", "- Friday", "7:00am - 7:30am",
                    "6:00pm - 6:30pm", "8:00pm - 8:30pm");
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setY(120);
        stage.setX(370);
        stage.setResizable(false);
        stage.showAndWait();
    }

    public void joinClass(ActionEvent e) {
        Button srcBtn = (Button) e.getSource();
        String text = ((Button) e.getSource()).getText();

        if (!text.equals("JOINED")) {
            if (srcBtn == joinBtn1) {
                joinBtn(joinBtn1, leaveBtn1);

            } else if (srcBtn == joinBtn2) {
                joinBtn(joinBtn2, leaveBtn2);

            } else if (srcBtn == joinBtn3) {
                joinBtn(joinBtn3, leaveBtn3);

            } else if (srcBtn == joinBtn4) {

                joinBtn(joinBtn4, leaveBtn4);

            } else if (srcBtn == joinBtn5) {
                joinBtn(joinBtn5, leaveBtn5);

            } else if (srcBtn == joinBtn6) {
                joinBtn(joinBtn6, leaveBtn6);
            }
        }


    }

    public void joinBtn(Button joinButton, Button leaveButton) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to join the class", ButtonType.YES, ButtonType.NO);
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                joinButton.setText("JOINED");
                joinButton.setStyle("-fx-background-color:#777777;" + "-fx-cursor: default;");
                leaveButton.setVisible(true);
                joinButton.setTranslateX(0);

            } else {
                showAlert.alert(Alert.AlertType.INFORMATION, "cancel join action");
            }
        });
    }

    public void leaveClass(ActionEvent e) {
        Button srcBtn = (Button) e.getSource();

        if (srcBtn == leaveBtn1) {
            leaveBtn(joinBtn1, leaveBtn1);

        } else if (srcBtn == leaveBtn2) {
            leaveBtn(joinBtn2, leaveBtn2);

        } else if (srcBtn == leaveBtn3) {
            leaveBtn(joinBtn3, leaveBtn3);

        } else if (srcBtn == leaveBtn4) {
            leaveBtn(joinBtn4, leaveBtn4);

        } else if (srcBtn == leaveBtn5) {
            leaveBtn(joinBtn5, leaveBtn5);

        } else if (srcBtn == leaveBtn6) {
            leaveBtn(joinBtn6, leaveBtn6);
        }
    }

    public void leaveBtn(Button joinButton, Button leaveButton) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to leave the class?", ButtonType.YES, ButtonType.NO);
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                leaveButton.setVisible(false);
                joinButton.setTranslateX(50);
                joinButton.setText("JOIN CLASS");
                joinButton.setStyle("-fx-background-color:#4a642f;" + "-fx-cursor:hand;");

            } else {
                showAlert.alert(Alert.AlertType.INFORMATION, "cancel leave action");
            }
        });
    }
}



