package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.FitnessGoalMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class viewAllGoalController {
    private static DaoInterface<fitnessGoal> dailyGoalDao = new DaoImplement<>("dailyGoal.txt", new FitnessGoalMapper());
    private static DaoInterface<fitnessGoal> weeklyGoalDao = new DaoImplement<>("weeklyGoal.txt", new FitnessGoalMapper());

    public ObservableList<fitnessGoal> loadGoals(String period) throws IOException {
        List<fitnessGoal> goals = (period.equalsIgnoreCase("daily") ? dailyGoalDao : weeklyGoalDao).getAll();
        ObservableList<fitnessGoal> goalsList = FXCollections.observableArrayList();
        goalsList.addAll(goals);
        return goalsList;
    }

    public void updateGoal(fitnessGoal original, fitnessGoal updatedGoal, String period) throws IOException {
        (period.equalsIgnoreCase("daily") ? dailyGoalDao : weeklyGoalDao).update(original, updatedGoal);
    }

    public void deleteGoal(fitnessGoal goal, String period) throws IOException {
        (period.equalsIgnoreCase("daily") ? dailyGoalDao : weeklyGoalDao).delete(goal);
    }
}
