package com.example.projectpartbprogram_group39.DAO.ClassMapper;

import com.example.projectpartbprogram_group39.Models.fitnessGoal;

public class FitnessGoalMapper implements EntityMapper<fitnessGoal>{
    @Override
    public fitnessGoal fromString(String string) {
        return fitnessGoal.splitGoalString(string);
    }

    @Override
    public String toString(fitnessGoal object) {
        return object.toString();
    }

    @Override
    public boolean equals(fitnessGoal obj1, fitnessGoal obj2) {
        return obj1.getGoalType().equalsIgnoreCase(obj2.getGoalType()) &&
                obj1.getStartDate().equals(obj2.getStartDate()) &&
                obj1.getTimeFrame().equalsIgnoreCase(obj2.getTimeFrame());
    }

}
