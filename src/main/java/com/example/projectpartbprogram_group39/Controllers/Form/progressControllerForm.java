package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Controllers.Service.ProgressController;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.FitnessGoalMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import com.example.projectpartbprogram_group39.Utils.TraineeSession;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class progressControllerForm{
    @FXML
    private Label heartRate;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Label valueCompleted, completePercentage, targetGoal, unitLabel;

    @FXML
    private ComboBox<String> progressCombo;

    @FXML
    private Label weightLbl, heightLbl, yearLbl, bmiValue, classLabel;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<Number, String> barChart;

    @FXML
    private Label averageStepLbl;

    private final String[] progressGoal = {"Calories Burned", "Loss Weight"};
    private Random random = new Random();
    private int currentHeartRate;
    private ProgressController progressController;

    @FXML
    public void initialize() {
        progressCombo.getItems().addAll(progressGoal);
        progressCombo.setValue("Calories Burned");

        progressController = new ProgressController();

        Trainee trainee = TraineeSession.getInstance().getCurrentTrainee();
        if (trainee != null) {
            progressController.traineeInfo(trainee.getHeight(), trainee.getWeight(), trainee.getAge(),
                    weightLbl, heightLbl, yearLbl, bmiValue, classLabel);
        }

        setupCharts();
        setupPieChart();
        setupHeartRate();
        setupComboBoxListener();

        updateProgress("Calories Burned");
    }

    private void setupCharts() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("15th", 6));
        series.getData().add(new XYChart.Data<>("16th", 10));
        series.getData().add(new XYChart.Data<>("17th", 11));
        series.getData().add(new XYChart.Data<>("18th", 8));
        series.getData().add(new XYChart.Data<>("19th", 7));
        series.getData().add(new XYChart.Data<>("20th", 8));
        series.getData().add(new XYChart.Data<>("21st", 9));
        lineChart.getData().add(series);

        XYChart.Series<Number, String> series1 = new XYChart.Series<>();
        series1.getData().add(new XYChart.Data<>(13903, "21st"));
        series1.getData().add(new XYChart.Data<>(14345, "20th"));
        series1.getData().add(new XYChart.Data<>(15000, "19th"));
        series1.getData().add(new XYChart.Data<>(11934, "18th"));
        series1.getData().add(new XYChart.Data<>(12432, "17th"));
        series1.getData().add(new XYChart.Data<>(11234, "16th"));
        series1.getData().add(new XYChart.Data<>(12643, "15th"));
        barChart.getData().add(series1);

        int average = (int) ((12643 + 11234 + 12432 + 11934 + 15000 + 14345 + 13903) / 7.0);
        averageStepLbl.setText(String.valueOf(average));
    }

    private void setupPieChart(){
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Stretching",0.194),
                        new PieChart.Data("Running",0.165),
                        new PieChart.Data("Sleep Tracking",0.235),
                        new PieChart.Data("Yoga",0.275),
                        new PieChart.Data("Cardio",0.13)
                );

        pieChart.getData().addAll(pieChartData);

    }

    private void setupHeartRate() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    double change = random.nextInt(7) - 3;
                    currentHeartRate += change;

                    if (currentHeartRate <= 90) {
                        currentHeartRate = 90;
                    } else if (currentHeartRate >= 130) {
                        currentHeartRate = 130;
                    }

                    heartRate.setText(String.valueOf(currentHeartRate));
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void setupComboBoxListener() {
        progressCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateProgress(newValue);
        });
    }

    public void updateProgress(String selectedGoal) {
        progressController.updateProgress(selectedGoal, progressIndicator, valueCompleted, completePercentage,
                targetGoal, unitLabel);
    }


}
