package com.example.projectpartbprogram_group39.DAO.goalDao;

import com.example.projectpartbprogram_group39.Models.fitnessGoal;

import java.io.IOException;
import java.util.List;

public interface goalsDaoInterface {

    void addGoal(fitnessGoal goal);
    void editGoal(fitnessGoal goal);
    void updateGoal(fitnessGoal goal);
    void deleteGoal(fitnessGoal goal);
    List<fitnessGoal> getGoalList() throws IOException;
}
