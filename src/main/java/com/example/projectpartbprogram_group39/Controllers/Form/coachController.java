package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.CoachMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Coach;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class coachController implements Initializable {

    @FXML
    private Button joinBtn1, joinBtn2, joinBtn3, joinBtn4, joinBtn5, joinBtn6;

    @FXML
    private Button viewDetail1, viewDetail2, viewDetail3, viewDetail4, viewDetail5, viewDetail6;

    @FXML
    private Button leaveBtn1, leaveBtn2, leaveBtn3, leaveBtn4, leaveBtn5, leaveBtn6;

    private static final DaoInterface<Coach> coachDao = new DaoImplement<>("coaches.txt", new CoachMapper());

    Image coach1 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_4.png"));
    Image coach2 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_2.png"));
    Image coach3 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_3.png"));
    Image coach4 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_1.png"));
    Image coach5 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girlCoach_2.png"));
    Image coach6 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girlCoach_1.png"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCoaches();
        updateButtonStates();
    }

    private void initializeCoaches() {
        Coach initCoach1 = new Coach("Ethan Carter", false);
        Coach initCoach2 = new Coach("Lucas Bennett", false);
        Coach initCoach3 = new Coach("Max Donovan", false);
        Coach initCoach4 = new Coach("Nathan Pierce", false);
        Coach initCoach5 = new Coach("Olivia Harper", false);
        Coach initCoach6 = new Coach("Mia Collins", false);

        try {
            List<Coach> coaches = coachDao.getAll();
            if (coaches.isEmpty()) {
                coachDao.add(initCoach1);
                coachDao.add(initCoach2);
                coachDao.add(initCoach3);
                coachDao.add(initCoach4);
                coachDao.add(initCoach5);
                coachDao.add(initCoach6);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateButtonStates() {
        try {
            List<Coach> coaches = coachDao.getAll();

            for (Coach coach : coaches) {
                switch (coach.getName()) {
                    case "Ethan Carter":
                        updateButtons(coach, joinBtn1, leaveBtn1);
                        break;
                    case "Lucas Bennett":
                        updateButtons(coach, joinBtn2, leaveBtn2);
                        break;
                    case "Max Donovan":
                        updateButtons(coach, joinBtn3, leaveBtn3);
                        break;
                    case "Nathan Pierce":
                        updateButtons(coach, joinBtn4, leaveBtn4);
                        break;
                    case "Olivia Harper":
                        updateButtons(coach, joinBtn5, leaveBtn5);
                        break;
                    case "Mia Collins":
                        updateButtons(coach, joinBtn6, leaveBtn6);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateButtons(Coach coach, Button joinButton, Button leaveButton) {
        if (coach.getIsJoined()) {
            joinButton.setText("JOINED");
            joinButton.setStyle("-fx-background-color:#777777;" + "-fx-cursor: default;");
            leaveButton.setVisible(true);
            joinButton.setTranslateX(0);
        } else {
            joinButton.setText("JOIN CLASS");
            joinButton.setStyle("-fx-background-color:#4a642f;" + "-fx-cursor:hand;");
            leaveButton.setVisible(false);
            joinButton.setTranslateX(50);
        }
    }

    public void viewDetail(ActionEvent e) throws IOException {
        Button sourceBtn = (Button) e.getSource();

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

        Coach updateStatus = null;
        String coachName = "";

        if (srcBtn == joinBtn1) {
            updateStatus = new Coach("Ethan Carter", true);
            coachName = "Ethan Carter";
        } else if (srcBtn == joinBtn2) {
            updateStatus = new Coach("Lucas Bennett", true);
            coachName = "Lucas Bennett";
        } else if (srcBtn == joinBtn3) {
            updateStatus = new Coach("Max Donovan", true);
            coachName = "Max Donovan";
        } else if (srcBtn == joinBtn4) {
            updateStatus = new Coach("Nathan Pierce", true);
            coachName = "Nathan Pierce";
        } else if (srcBtn == joinBtn5) {
            updateStatus = new Coach("Olivia Harper", true);
            coachName = "Olivia Harper";
        } else if (srcBtn == joinBtn6) {
            updateStatus = new Coach("Mia Collins", true);
            coachName = "Mia Collins";
        }

        if (updateStatus != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Join Class Confirmation");
            alert.setHeaderText("Are you sure you want to join " + coachName + "'s class?");
            alert.setContentText("This action will mark you as joined to the class.");

            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    List<Coach> coaches = coachDao.getAll();
                    for (Coach coach : coaches) {
                        if (coach.getName().equals(updateStatus.getName())) {
                            coach.setIsJoined(true);
                            coachDao.update(coach, updateStatus);
                            break;
                        }
                    }
                    updateButtonStates();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void leaveClass(ActionEvent e) {
        Button srcBtn = (Button) e.getSource();

        Coach updateStatus = null;
        String coachName = "";

        if (srcBtn == leaveBtn1) {
            updateStatus = new Coach("Ethan Carter", false);
            coachName = "Ethan Carter";
        } else if (srcBtn == leaveBtn2) {
            updateStatus = new Coach("Lucas Bennett", false);
            coachName = "Lucas Bennett";
        } else if (srcBtn == leaveBtn3) {
            updateStatus = new Coach("Max Donovan", false);
            coachName = "Max Donovan";
        } else if (srcBtn == leaveBtn4) {
            updateStatus = new Coach("Nathan Pierce", false);
            coachName = "Nathan Pierce";
        } else if (srcBtn == leaveBtn5) {
            updateStatus = new Coach("Olivia Harper", false);
            coachName = "Olivia Harper";
        } else if (srcBtn == leaveBtn6) {
            updateStatus = new Coach("Mia Collins", false);
            coachName = "Mia Collins";
        }

        if (updateStatus != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Leave Class Confirmation");
            alert.setHeaderText("Are you sure you want to leave " + coachName + "'s class?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    List<Coach> coaches = coachDao.getAll();
                    for (Coach coach : coaches) {
                        if (coach.getName().equals(updateStatus.getName())) {
                            coach.setIsJoined(false);
                            coachDao.update(coach, updateStatus);
                            break;
                        }
                    }
                    updateButtonStates();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
