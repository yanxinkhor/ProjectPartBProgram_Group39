package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.FitnessGoalMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.WorkoutMapper;
import com.example.projectpartbprogram_group39.Models.Workouts;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class exercisesController {
    DaoInterface<fitnessGoal> dailyGoal = new DaoImplement<>("dailyGoal.txt",new FitnessGoalMapper());
    DaoInterface<fitnessGoal> weeklyGoal = new DaoImplement<>("weeklyGoal.txt",new FitnessGoalMapper());
    DaoInterface<Workouts> workoutDao = new DaoImplement<>("workoutLog.txt", new WorkoutMapper());

    public List<fitnessGoal> getDailyGoalList() throws IOException {
        return dailyGoal.getAll();
    }

    public List<fitnessGoal> getWeeklyGoalList() throws IOException{
        return weeklyGoal.getAll();
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
