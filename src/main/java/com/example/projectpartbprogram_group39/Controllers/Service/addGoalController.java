package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.goalDao.goalDaoImp;
import com.example.projectpartbprogram_group39.DAO.goalDao.goalsDaoInterface;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.time.LocalDate;

public class addGoalController {

    private goalsDaoInterface goalDao;

    public addGoalController() {
        this.goalDao = new goalDaoImp();
    }

    public void addGoal(String goalType, String targetValueStr, String unit, String priority,
                        String timeFrame, LocalDate startDate) throws IOException {

        if (goalType.isEmpty() || targetValueStr.isEmpty() || unit == null || priority == null ||
                timeFrame == null || startDate == null) {
            showAlert.alert(Alert.AlertType.WARNING, "Please fill in all fields.");
            return;
        }

        if (goalDao.goalExists(goalType)) {
            showAlert.alert(Alert.AlertType.ERROR, "Goal already exists, please enter another goal.");
            return;
        }

        try {
            int targetValue = Integer.parseInt(targetValueStr);
            String startDateStr = startDate.toString();

            fitnessGoal newGoal = new fitnessGoal(goalType, targetValue, unit, timeFrame, startDateStr, priority);
            goalDao.addGoal(newGoal);

            showAlert.alert(Alert.AlertType.INFORMATION, "Goal added successfully!");
        } catch (NumberFormatException ex) {
            showAlert.alert(Alert.AlertType.ERROR, "Invalid input, value must be a number.");
        }
    }
}
