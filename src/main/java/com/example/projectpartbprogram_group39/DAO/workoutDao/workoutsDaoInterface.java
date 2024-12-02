package com.example.projectpartbprogram_group39.DAO.workoutDao;

import com.example.projectpartbprogram_group39.Models.Workouts;

import java.util.List;

public interface workoutsDaoInterface {
    void addWorkout(Workouts workout);  // Add a new workout
    List<Workouts> getAllWorkouts();
    void editWorkouts(Workouts workout);
    void updateWorkout(Workouts workout);
    void deleteWorkout(Workouts workout);
}
