package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Controllers.Service.ProfileController;
import com.example.projectpartbprogram_group39.Models.Coach;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.TraineeSession;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class dashboardControllerForm implements Initializable {

    @FXML
    public Text usernameT;
    @FXML
    public Text weightT;
    @FXML
    public Text heightT;
    @FXML
    public Text ageT;
    @FXML
    public Text goalWeight;
    @FXML
    public Text heartRate;
    private int currentHearRate;
    @FXML
    public ImageView profile_img;
    @FXML
    private LineChart<String, Number> dashLineChart;
    @FXML
    public Text steps;
    @FXML
    public Text calories;
    @FXML
    public Text noGoal;
    @FXML
    public Label noClassText;
    @FXML
    public HBox noClassBox;
    @FXML
    public ImageView coach1IMG;
    @FXML
    public ImageView coach2IMG;
    @FXML
    public Label progressLabel;
    @FXML
    public ProgressIndicator progressIndicator;

    private com.example.projectpartbprogram_group39.Controllers.Service.dashboardController progressDashboard;

    private ProfileController profileController = new ProfileController();

    private final Image femaleProfile = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girl_profile.png"));
    private final Image maleProfile = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guy_profile.png"));

    Image coach1 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_4.png"));
    Image coach2 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_2.png"));
    Image coach3 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_3.png"));
    Image coach4 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guyCoach_1.png"));
    Image coach5 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girlCoach_2.png"));
    Image coach6 = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girlCoach_1.png"));

    private Random random = new Random();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        progressDashboard = new com.example.projectpartbprogram_group39.Controllers.Service.dashboardController();
         progressDashboard.displayProgress(steps, calories, goalWeight, progressLabel, progressIndicator, noGoal);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    double change = random.nextInt(7) - 3;
                    currentHearRate += change;

                    if (currentHearRate < 90) {
                        currentHearRate = 90;
                    } else if (currentHearRate > 130) {
                        currentHearRate = 130;
                    }

                    heartRate.setText(String.valueOf(currentHearRate));
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("15th", 6));
        series.getData().add(new XYChart.Data<>("16th", 10));
        series.getData().add(new XYChart.Data<>("17th", 11));
        series.getData().add(new XYChart.Data<>("18th", 8));
        series.getData().add(new XYChart.Data<>("19th", 7));
        series.getData().add(new XYChart.Data<>("20th", 8));
        series.getData().add(new XYChart.Data<>("21th", 9));
        dashLineChart.getData().add(series);
        dashLineChart.setCreateSymbols(false);

        Trainee trainee = TraineeSession.getInstance().getCurrentTrainee();
        if (trainee != null) {
            usernameT.setText(trainee.getUsername());
            weightT.setText(trainee.getWeight() + "kg");
            heightT.setText(trainee.getHeight() + "cm");
            ageT.setText(String.valueOf(trainee.getAge()));

            if (trainee.getGender().equals("Male")) {
                profile_img.setImage(maleProfile);
            } else {
                profile_img.setImage(femaleProfile);
            }

        }

        try {
            displayClass();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void displayClass() throws IOException {
        List<Coach> lists = profileController.getCoachesList();

        if (lists.isEmpty()) {
            noClassText.setVisible(true);
            return;
        }

        int joinedCount = 0;

        for (Coach list : lists) {
            if (list.getIsJoined()) {
                noClassText.setVisible(false);

                if (joinedCount == 0) {
                    switch (list.getName()) {
                        case "Ethan Carter":
                            coach1IMG.setImage(coach1);
                            break;
                        case "Lucas Bennett":
                            coach1IMG.setImage(coach2);
                            break;
                        case "Max Donovan":
                            coach1IMG.setImage(coach3);
                            break;
                        case "Nathan Pierce":
                            coach1IMG.setImage(coach4);
                            break;
                        case "Olivia Harper":
                            coach1IMG.setImage(coach5);
                            break;
                        case "Mia Collins":
                            coach1IMG.setImage(coach6);
                            break;
                    }
                    joinedCount++;
                } else if (joinedCount == 1) {
                    switch (list.getName()) {
                        case "Ethan Carter":
                            coach2IMG.setImage(coach1);
                            break;
                        case "Lucas Bennett":
                            coach2IMG.setImage(coach2);
                            break;
                        case "Max Donovan":
                            coach2IMG.setImage(coach3);
                            break;
                        case "Nathan Pierce":
                            coach2IMG.setImage(coach4);
                            break;
                        case "Olivia Harper":
                            coach2IMG.setImage(coach5);
                            break;
                        case "Mia Collins":
                            coach2IMG.setImage(coach6);
                            break;
                    }
                    break;
                }

            }

        }
    }

}
