package com.example.projectpartbprogram_group39.DAO.ClassMapper;

import com.example.projectpartbprogram_group39.Models.Workouts;

public class WorkoutMapper implements EntityMapper<Workouts> {
    @Override
    public Workouts fromString(String string) {
        return Workouts.splitWorkoutString(string);
    }

    @Override
    public String toString(Workouts object) {
        return object.toString();
    }

    @Override
    public boolean equals(Workouts obj1, Workouts obj2) {
        return obj1.getType().equals(obj2.getType()) &&
                obj1.getCaloriesBurned().equals(obj2.getCaloriesBurned()) &&
                obj1.getBeginDate().equals(obj2.getBeginDate());
    }
}
