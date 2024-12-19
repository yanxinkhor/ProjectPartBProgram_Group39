package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.WorkoutMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Workouts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class displayLogController {
    private final DaoInterface<Workouts> workoutDao = new DaoImplement<>("workoutLog.txt", new WorkoutMapper());
    private final ObservableList<Workouts> workoutList = FXCollections.observableArrayList();

    public ObservableList<Workouts> loadLogs() throws IOException {
        List<Workouts> workouts = workoutDao.getAll();
        workoutList.clear();
        workoutList.addAll(workouts);
        return workoutList;
    }

    public void updateLog(Workouts selectedLog, Workouts updatedLog) throws IOException {
        workoutDao.update(selectedLog, updatedLog);
    }

    public void deleteLog(Workouts selectedLog) throws IOException {
        workoutDao.delete(selectedLog);
    }

    public boolean isLogTypeExists(String updatedType) throws IOException {
        List<Workouts> existingWorkout = workoutDao.getAll();
        for (Workouts existingPlan : existingWorkout) {
            if (existingPlan.getType().equalsIgnoreCase(updatedType)) {
                return true;
            }
        }
        return false;
    }

    public boolean validateFields(String updatedType, String updatedCalBurnedStr, String updatedDurationStr,
                                  String updatedTime, String updatedFreqStr, java.time.LocalDate updatedDate, String imgUrl) {
        return !(updatedType.isEmpty() || updatedCalBurnedStr.isEmpty() || updatedDurationStr.isEmpty() ||
                updatedTime == null || updatedFreqStr.isEmpty() || updatedDate == null || imgUrl == null || imgUrl.isEmpty());
    }

    public String formatCalories(String updatedCalBurnedStr) {
        return updatedCalBurnedStr + " kcal";
    }

    public String parseDateToString(java.time.LocalDate updatedDate) {
        return updatedDate.toString();
    }

}
