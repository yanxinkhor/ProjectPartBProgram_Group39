package com.example.projectpartbprogram_group39.DAO.goalDao;

import com.example.projectpartbprogram_group39.Models.fitnessGoal;

import java.io.IOException;
import java.util.List;

public interface goalsDaoInterface {

    void addGoal(fitnessGoal goal);
    void updateGoal(fitnessGoal oldGoal, fitnessGoal newGoal);
    void deleteGoal(fitnessGoal goal) throws IOException;
    List<fitnessGoal> getGoalList(String period) throws IOException;
}
