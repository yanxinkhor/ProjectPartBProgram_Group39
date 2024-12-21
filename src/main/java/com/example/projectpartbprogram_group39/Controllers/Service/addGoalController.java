package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.FitnessGoalMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.time.LocalDate;

public class addGoalController {

    private DaoInterface<fitnessGoal> dailyGoalDao;
    private DaoInterface<fitnessGoal> weeklyGoalDao;

    public addGoalController() {
        this.dailyGoalDao = new DaoImplement<>("dailyGoal.txt",new FitnessGoalMapper());
        this.weeklyGoalDao = new DaoImplement<>("weeklyGoal.txt",new FitnessGoalMapper());
    }

    public void addGoal(String goalType, String targetValueStr, String unit, String priority,
                        String timeFrame, LocalDate startDate) throws IOException {

        if (goalType == null || goalType.isEmpty() || targetValueStr.isEmpty() || unit == null || priority == null ||
                timeFrame == null || startDate == null) {
            showAlert.alert(Alert.AlertType.WARNING, "Please fill in all fields.");
            return;
        }

        try {
            int targetValue = Integer.parseInt(targetValueStr);

            if(targetValue <= 0){
                throw new IllegalArgumentException("Target value cannot be zero or negative.");
            }

            String startDateStr = startDate.toString();

            fitnessGoal newGoal = new fitnessGoal(goalType, targetValue, unit, timeFrame, startDateStr, priority);
            if (newGoal.getTimeFrame().equalsIgnoreCase("daily")) {
                if (dailyGoalDao.exists(newGoal)) {
                    showAlert.alert(Alert.AlertType.ERROR, "Goal already exists");

                }else {
                    dailyGoalDao.add(newGoal);
                    showAlert.alert(Alert.AlertType.INFORMATION, "Daily goal added successfully!");
                }

            } else {
                if (weeklyGoalDao.exists(newGoal)) {
                    showAlert.alert(Alert.AlertType.ERROR, "Goal already exists");
                }else{
                weeklyGoalDao.add(newGoal);
                showAlert.alert(Alert.AlertType.INFORMATION, "Weekly goal added successfully!");
                }
            }

        } catch (NumberFormatException ex) {
            showAlert.alert(Alert.AlertType.ERROR, "Invalid input, value must be a number.");
        }catch(IllegalArgumentException ex){
            showAlert.alert(Alert.AlertType.ERROR, ex.getMessage());
        }
    }
}
