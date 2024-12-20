package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.FitnessGoalMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

import java.io.IOException;
import java.util.List;

public class ProgressController {
    private DaoInterface<fitnessGoal> dailyGoalDao = new DaoImplement<>("dailyGoal.txt", new FitnessGoalMapper());
    private double CalBurnedTarget = 500;
    private double lossWeightTarget = 5;

    public void traineeInfo(double height, double weight, int age, Label weightLbl, Label heightLbl, Label yearLbl,
                            Label bmiValue, Label classLabel) {
        heightLbl.setText(String.valueOf(height));
        weightLbl.setText(String.valueOf(weight));
        yearLbl.setText(String.valueOf(age));

        double bmi = calculateBMI(height, weight);
        String bmiClassification = getBMI(bmi);

        bmiValue.setText("BMI: " + String.format("%.2f", bmi));
        classLabel.setText(bmiClassification);
    }

    public double calculateBMI(double height, double weight) {
        double heightInM = height / 100;
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

    public void updateProgress(String selectedGoal, ProgressIndicator progressIndicator, Label valueCompleted,
                               Label completePercentage, Label targetGoal, Label unitLabel) {
        double target = 0;
        double currentCompletion = 0;
        boolean goalFound = false;

        try {
            List<fitnessGoal> goals = dailyGoalDao.getAll();

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
            unitLabel.setText("kcal");
        } else if (selectedGoal.equals("Loss Weight")) {
            targetGoal.setText("Goal: " + target + " kg");
            unitLabel.setText("kg");
        }
    }

}
