package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.FitnessGoalMapper;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.MealMapper;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.MealPlanMapper;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.MealSuggestMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Meal;
import com.example.projectpartbprogram_group39.Models.MealPlan;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import com.example.projectpartbprogram_group39.Models.mealSuggestion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class dashboardController {
    private DaoInterface<fitnessGoal> dailyGoalDao = new DaoImplement<>("dailyGoal.txt", new FitnessGoalMapper());
    private DaoInterface<fitnessGoal> weeklyGoalDao = new DaoImplement<>("weeklyGoal.txt", new FitnessGoalMapper());
    private static DaoInterface<MealPlan> mealPlanDao = new DaoImplement<>("mealPlan.txt", new MealPlanMapper());
    private static DaoInterface<mealSuggestion> mealSuggestDao = new DaoImplement<>("mealSuggest.txt", new MealSuggestMapper());

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
                if (goal.getGoalType().equals("Loss Weight") && goal.getUnit().equals("kg")) {
                    noGoal.setVisible(false);
                    target = goal.getGoalValue();
                    currentCompletion = target * 0.75;
                    goalWeight.setText((int) target + " kg");
                    goalWeight.setStyle("-fx-font-weight: bold;");
                    goalFound = true;
                }

                if(!goalFound){
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

    public void displayMeal(Label mealSuggest, Label mealPlan, Label nosuggestLabel, Label noplanLabel, ImageView meal1img, ImageView meal2img){
        String plan;
        String suggest;

        try{
            List<MealPlan> plans = mealPlanDao.getAll();
            if (plans.isEmpty()) {
                meal2img.setVisible(false);
                noplanLabel.setVisible(true);
            } else {
                for (MealPlan meal : plans) {
                    noplanLabel.setVisible(false);
                    meal2img.setVisible(true);
                    plan = meal.getFoodName();
                    mealPlan.setText(plan);
                    System.out.println(plan);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try{
            List<mealSuggestion> suggestions = mealSuggestDao.getAll();
            if (suggestions.isEmpty()) {
                meal1img.setVisible(false);
                nosuggestLabel.setVisible(true);
            } else {
                for (mealSuggestion meal : suggestions) {
                    nosuggestLabel.setVisible(false);
                    meal1img.setVisible(true);
                    suggest = meal.getFoodName();
                    mealSuggest.setText(suggest);
                    System.out.println(suggest);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
