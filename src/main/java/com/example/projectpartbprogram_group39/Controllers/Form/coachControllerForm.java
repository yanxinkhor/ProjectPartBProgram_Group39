package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Controllers.Service.coachController;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.CoachMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Coach;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class coachControllerForm implements Initializable {

    @FXML
    private Button joinBtn1, joinBtn2, joinBtn3, joinBtn4, joinBtn5, joinBtn6;

    @FXML
    private Button viewDetail1, viewDetail2, viewDetail3, viewDetail4, viewDetail5, viewDetail6;

    @FXML
    private Button leaveBtn1, leaveBtn2, leaveBtn3, leaveBtn4, leaveBtn5, leaveBtn6;

    @FXML
    private AnchorPane detailContainer;

    @FXML
    private Label coachExpert, coachesName,availableTime,day1,day2,day3,time1,time2,time3;


    @FXML
    private ImageView coachImg;

    @FXML
    private Label detailName,detailExpert,experienceYear,coachPhoneNo;

    @FXML
    private Button insBtn,mailBtn,twiBtn;

    @FXML
    private ImageView detailImg;

    @FXML
    private VBox socialDetailCtn;

    @FXML
    private Pane opacityPane;

    coachController coachService = new coachController();

    Image coach1 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_4.png"));
    Image coach2 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_2.png"));
    Image coach3 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_3.png"));
    Image coach4 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_1.png"));
    Image coach5 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girlCoach_2.png"));
    Image coach6 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girlCoach_1.png"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            coachService.InitialCoaches();
            updateButtonStates();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateButtonStates() {
        try {
            List<Coach> coaches = coachService.getAllCoaches();

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

    private void updateButtons(Coach coach, Button joinButton, Button leaveButton) throws IOException {
        boolean isJoined = coachService.isCoachJoined(coach.getName());
        if (isJoined) {
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

    public void viewDetail(ActionEvent e) {
        detailContainer.setVisible(true);
        opacityPane.setVisible(true);
        Button sourceBtn = (Button) e.getSource();

        if (sourceBtn == viewDetail1) {
            coachesDetail(coach1, "Ethan Carter", "Weight Lifting Expert", "45 minutes",
                    "- Monday", "- Thursday", "- Sunday", "8:00am - 8:45am", "5:00pm - 5:45pm", "10:30am - 11:20am");
            coachSocialDetail(coach1,"Ethan Carter","Weight Lifting Expert","3 years of experience","012-3892712");
        } else if (sourceBtn == viewDetail2) {
            coachesDetail(coach2, "Lucas Bennett", "Fitness Expert", "60 minutes",
                    "- Tuesday", "- Friday ", "- Saturday", "7:00am - 7:45am",
                    "6:00pm - 6:45pm", "9:00am - 9:45am");
            coachSocialDetail(coach2,"Lucas Bennett","Fitness Expert","2 years of experience","012-2346464");
        } else if (sourceBtn == viewDetail3) {
            coachesDetail(coach3, "Max Donovan", "Cardio Expert", "50 minutes", "- Wednesday",
                    "- Thursday", "- Sunday", "9:00am - 9:45am", "4:00pm - 4:45pm",
                    "1:00pm - 1:45pm");
            coachSocialDetail(coach3,"Max Donovan","Cardio Expert","4 years of experience","012-3345436");
        } else if (sourceBtn == viewDetail4) {
            coachesDetail(coach4, "Nathan Pierce", "Boxing Specialist", "40 minutes",
                    "- Monday", "- Wednesday", "- Saturday", "6:00pm - 6:45pm",
                    "8:00am - 8:45am", "10:30am - 11:15am");
            coachSocialDetail(coach4,"Nathan Pierce","Boxing Specialist","6 years of experience","015-4556784");
        } else if (sourceBtn == viewDetail5) {
            coachesDetail(coach5, "Olivia Harper", "Pilates Specialist", "55 minutes",
                    "- Tuesday", "- Friday", "- Sunday", "5:30pm - 6:15pm",
                    "7:00pm - 7:45pm", "11:00am - 11:45am");
            coachSocialDetail(coach5,"Olivia Harper","Pilates Specialist","10 years of experience","011-2239870");
        } else if (sourceBtn == viewDetail6) {
            coachesDetail(coach6, "Mia Collins", "Yoga Specialist", "30 minutes",
                    "- Monday", "- Wednesday", "- Friday", "7:00am - 7:30am",
                    "6:00pm - 6:30pm", "8:00pm - 8:30pm");
            coachSocialDetail(coach6,"Mia Collins","Yoga Specialist","4 years of experience","019-44567657");
        }

    }

    public void coachesDetail(Image coachesImg,String name, String expert, String available,
                              String firstDay, String secDay,String thirdDay,String clock1,String clock2,String clock3 ){
        coachImg.setImage(coachesImg);
        coachesName.setText(name);
        coachExpert.setText(expert);
        availableTime.setText(available);
        day1.setText(firstDay);
        day2.setText(secDay);
        day3.setText(thirdDay);
        time1.setText(clock1);
        time2.setText(clock2);
        time3.setText(clock3);
    }

    public void backBtn(ActionEvent e){
        detailContainer.setVisible(false);
        opacityPane.setVisible(false);
    }

    public void seeDetail(ActionEvent e){
        detailContainer.setVisible(false);
        socialDetailCtn.setVisible(true);
    }

    public void closeSocialDetail(ActionEvent e){
        socialDetailCtn.setVisible(false);
        detailContainer.setVisible(true);
    }


    public void coachSocialDetail(Image image,String name,String expert, String year, String phoneNo){
        detailImg.setImage(image);
        detailName.setText(name);
        detailExpert.setText(expert);
        experienceYear.setText(year);
        coachPhoneNo.setText(phoneNo);
    }

    public void socialMediaLink(ActionEvent e){
        Button srcBtn = (Button)e.getSource();

        if(srcBtn == insBtn){
            openLink("https://www.instagram.com");
        }else if(srcBtn == twiBtn){
            openLink("https://twitter.com");
        }else if(srcBtn == mailBtn){
            openLink("https://mail.google.com/");
        }
    }

    private void openLink(String url) {
        try {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (url.startsWith("mailto:")) {
                desktop.mail(new java.net.URI(url));
            } else {
                desktop.browse(new java.net.URI(url));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert.alert(Alert.AlertType.ERROR,"Unable to open link: " + url);
        }
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
            String button = ((Button)e.getSource()).getText();
            if(!button.equals("JOINED")) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Join Class Confirmation");
                alert.setHeaderText("Are you sure you want to join " + coachName + "'s class?");
                alert.setContentText("This action will mark you as joined to the class.");

                if (alert.showAndWait().get() == ButtonType.OK) {
                    try {
                        coachService.updateCoachStatus(updateStatus);
                        updateButtonStates();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
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
                    coachService.updateCoachStatus(updateStatus);
                    updateButtonStates();
                }catch(IOException ex){
                    ex.printStackTrace();
                }

            }
        }
    }
}
