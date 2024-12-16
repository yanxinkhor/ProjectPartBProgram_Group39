package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.DAO.goalDao.goalDaoImp;
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
import javafx.scene.shape.Arc;

import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class progressControllerForm implements Initializable {
    @FXML
    private Label heartRate;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Label valueCompleted, completePercentage, targetGoal, unitLabel;

    @FXML
    private ComboBox<String> progressCombo;

    @FXML
    private Arc arcBMI;

    @FXML
    private Line needleBMI;

    @FXML
    Label weightLbl, heightLbl, yearLbl,bmiValue,classLabel;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<Number,String> barChart;

    @FXML
    private Label averageStepLbl;

    private final String[] progressGoal = {"Calories Burned", "Loss Weight"};
    private double CalBurnedTarget = 500;
    private double lossWeightTarget = 5;
    private Random random = new Random();
    private int currentHearRate;
    private goalDaoImp goalDao = new goalDaoImp();
    Trainee trainee;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progressCombo.getItems().addAll(progressGoal);
        progressCombo.setValue("Calories Burned");
        trainee = TraineeSession.getInstance().getCurrentTrainee();
        if (trainee != null) {
            traineeInfo(trainee.getHeight(), trainee.getWeight(), trainee.getAge());
        }



        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<>("15th", 6));
        series.getData().add(new XYChart.Data<>("16th", 10));
        series.getData().add(new XYChart.Data<>("17th", 11));
        series.getData().add(new XYChart.Data<>("18th", 8));
        series.getData().add(new XYChart.Data<>("19th", 7));
        series.getData().add(new XYChart.Data<>("20th", 8));
        series.getData().add(new XYChart.Data<>("21th", 9));
        lineChart.getData().add(series);

        XYChart.Series<Number,String> series1 = new XYChart.Series<>();
        series1.getData().add(new XYChart.Data<>(13903,"21th"));
        series1.getData().add(new XYChart.Data<>(14345,"20th"));
        series1.getData().add(new XYChart.Data<>(15000,"19th"));
        series1.getData().add(new XYChart.Data<>(11934,"18th"));
        series1.getData().add(new XYChart.Data<>(12432,"17th"));
        series1.getData().add(new XYChart.Data<>(11234,"16th"));
        series1.getData().add(new XYChart.Data<>(12643,"15th"));
        barChart.getData().add(series1);
        int average = (int) ((12643 + 11234 + 12432 + 11934 + 15000 + 14345 + 13903)/7.0);
        averageStepLbl.setText(String.valueOf(average));

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Stretching",0.194),
                        new PieChart.Data("Running",0.165),
                        new PieChart.Data("Sleep Tracking",0.235),
                        new PieChart.Data("Yoga",0.275),
                        new PieChart.Data("Cardio",0.13)
                );

        pieChart.getData().addAll(pieChartData);


        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event ->{
                    double change = random.nextInt(7) - 3;
                    currentHearRate += change;

                    if(currentHearRate <= 90){
                        currentHearRate = 90;
                    }else if(currentHearRate >= 130){
                        currentHearRate = 130;
                    }

                    heartRate.setText(String.valueOf(currentHearRate));
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        progressCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateProgress(newValue);
        });

        updateProgress("Calories Burned");
    }

    public void traineeInfo(double height, double weight, int age){
        heightLbl.setText(String.valueOf(height));
        weightLbl.setText(String.valueOf(weight));
        yearLbl.setText(String.valueOf(age));

        double bmi = calculateBMI(height, weight);
        String bmiClassification = getBMI(bmi);

        bmiValue.setText("BMI: " + String.format("%.2f", bmi));
        classLabel.setText(bmiClassification);
    }

    public void updateProgress(String selectedGoal) {
        double target = 0;
        double currentCompletion = 0;
        boolean goalFound = false;

        try {
            List<fitnessGoal> goals = goalDao.getGoalList("daily");

            for (fitnessGoal goal : goals) {
                if (selectedGoal.equalsIgnoreCase(goal.getGoalType())) {
                    target = goal.getGoalValue();

                    if (selectedGoal.equals("Calories Burned")) {
                        currentCompletion = target * 0.90;
                    } else if (selectedGoal.equals("Loss Weight")) {
                        currentCompletion = target * 0.75;
                    }

                    goalFound = true;
                    break;
                }
            }

            if (!goalFound) {
                if (selectedGoal.equals("Calories Burned")) {
                    target = CalBurnedTarget;
                    currentCompletion = target * 0.90;
                } else if (selectedGoal.equals("Loss Weight")) {
                    target = lossWeightTarget;
                    currentCompletion = target * 0.75;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double progressCompletion = (currentCompletion / target) * 100;
        progressIndicator.setProgress(progressCompletion / 100);

        valueCompleted.setText(String.valueOf(currentCompletion));
        completePercentage.setText(progressCompletion + "%");

        if (selectedGoal.equals("Calories Burned")) {
            targetGoal.setText("Goal: " + target + " kcal");
        } else if (selectedGoal.equals("Loss Weight")) {
            targetGoal.setText("Goal: " + target + " kg");
        }

        if (selectedGoal.equals("Calories Burned")) {
            unitLabel.setText("kcal");
        } else if (selectedGoal.equals("Loss Weight")) {
            unitLabel.setText("kg");
        }
    }

    public double calculateBMI(double height, double weight){
        double heightInM = height/100;

        return weight / (heightInM * heightInM);
    }

    public String getBMI(double bmi) {
        if (bmi < 18.5) {
            return "Under Weight";
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            return "Normal Weight";
        } else if (bmi >= 25 && bmi <= 29.9) {
            return "Overweight";
        } else if (bmi >= 30 && bmi <= 34.9) {
            return "Obese";
        } else {
            return "Extremely Obese";
        }
    }
}
