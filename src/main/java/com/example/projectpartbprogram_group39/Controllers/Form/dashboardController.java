package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.TraineeSession;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {


    private String gender;
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

    private final Image femaleProfile = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/girl_profile.png"));
    private final Image maleProfile = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/guy_profile.png"));

    private Random random = new Random();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        int average = (int) ((12643 + 11234 + 12432 + 11934 + 15000 + 14345 + 13903) / 7.0);
        steps.setText(String.valueOf(average));
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
    }
}
