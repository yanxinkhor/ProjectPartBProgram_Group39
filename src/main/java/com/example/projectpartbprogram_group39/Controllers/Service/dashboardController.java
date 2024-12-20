package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.FitnessGoalMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class dashboardController {
    private DaoInterface<fitnessGoal> dailyGoalDao = new DaoImplement<>("dailyGoal.txt", new FitnessGoalMapper());
    private DaoInterface<fitnessGoal> weeklyGoalDao = new DaoImplement<>("weeklyGoal.txt", new FitnessGoalMapper());

    public void displayProgress(Text steps, Text calories, Text goalWeight, Label progressLabel, ProgressIndicator progressIndicator, Text noGoal){

        double target = 0;
        double currentCompletion = 0;
        boolean goalFound = false;

        try {
            List<fitnessGoal> goals = dailyGoalDao.getAll();

            for (fitnessGoal goal : goals) {
                if (goal.getGoalType().equals("Calories Burned")) {
                    target = goal.getGoalValue();
                    currentCompletion = target * 0.75;
                    System.out.println(goal.getGoalValue());
                    calories.setText(String.valueOf((int)currentCompletion));
                    goalFound = true;
                }else if (goal.getUnit().equals("steps")) {
                    target = goal.getGoalValue();
                    currentCompletion = target * 0.75;
                    System.out.println(goal.getGoalValue());
                    steps.setText(String.valueOf((int)currentCompletion));
                    goalFound = true;
                }else if (goal.getGoalType().equals("Loss Weight")) {
                    noGoal.setVisible(false);
                    target = goal.getGoalValue();
                    currentCompletion = target * 0.75;
                    goalWeight.setText((int) target + " kg");
                    goalWeight.setStyle("-fx-font-weight: bold;");
                    goalFound = true;
                }

            }

            if (!goalFound) {
                currentCompletion = 0;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try{
            List<fitnessGoal> goals = weeklyGoalDao.getAll();

            for (fitnessGoal goal : goals) {
                if (goal.getGoalType().equals("Loss Weight")) {
                    continue;
                } else if (goal.getGoalType().equals("Loss Weight")) {
                    noGoal.setVisible(false);
                    target = goal.getGoalValue();
                    currentCompletion = target * 0.75;
                    goalWeight.setText((int) target + " kg");
                    goalWeight.setStyle("-fx-font-weight: bold;");
                    goalFound = true;
                } else {
                    noGoal.setVisible(true);
                }
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        double progressCompletion = (currentCompletion / target) * 100;
        progressIndicator.setProgress(progressCompletion / 100);

        int progressPercentage = (int) progressCompletion;

        progressLabel.setText(progressPercentage + "%");

    }

}
