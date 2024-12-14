package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.DAO.genericDao.WorkoutMapper;
import com.example.projectpartbprogram_group39.DAO.goalDao.goalDaoImp;
import com.example.projectpartbprogram_group39.DAO.workoutDao.workoutsDaoImp;
import com.example.projectpartbprogram_group39.Models.Workouts;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class exercisesController {
    private goalDaoImp goalDao = new goalDaoImp();
    DaoInterface<Workouts> workoutDao = new DaoImplement<>("workoutLog.txt", new WorkoutMapper());

    public List<fitnessGoal> getGoalsByPeriod(String period) throws IOException {
        return goalDao.getGoalList(period);
    }

    public void addWorkoutLog(String workoutType, String caloriesLogStr, String durationStr, String timeStr,
                              String frequencyStr, LocalDate startDate, String urlImg) {

        try {
            Workouts tempWorkout = new Workouts(workoutType);
            if (workoutDao.exists(tempWorkout)) {
                showAlert.alert(Alert.AlertType.ERROR, "Log already exists, please enter a new log");
                return;
            }

            String caloriesLog = caloriesLogStr + " kcal";
            int duration = Integer.parseInt(durationStr);
            int frequency = Integer.parseInt(frequencyStr);
            String startDateStr = startDate.toString();

            Workouts newLogs = new Workouts(workoutType, caloriesLog, duration, timeStr, frequency, startDateStr, urlImg);
            workoutDao.add(newLogs);
            showAlert.alert(Alert.AlertType.INFORMATION, "Log added successfully!");

        } catch (NumberFormatException ex) {
            showAlert.alert(Alert.AlertType.ERROR, "Invalid Input, value must be a number.");
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public List<Workouts> getAllWorkouts() throws IOException {
        return workoutDao.getAll();
    }
}
