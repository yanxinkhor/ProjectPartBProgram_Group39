package com.example.projectpartbprogram_group39.DAO.workoutDao;

import com.example.projectpartbprogram_group39.Models.Workouts;

import java.io.IOException;
import java.util.List;

public interface workoutsDaoInterface {
    void addWorkout(Workouts workout);  // Add a new workout
    List<Workouts> getAllWorkouts() throws IOException;
    void editWorkouts(Workouts oldLog, Workouts newLog);
    boolean logExists(String workoutType ) throws IOException;
    void deleteWorkout(Workouts workout) throws IOException;
}
