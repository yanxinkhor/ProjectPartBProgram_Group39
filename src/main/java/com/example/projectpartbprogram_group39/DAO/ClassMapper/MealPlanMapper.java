package com.example.projectpartbprogram_group39.DAO.ClassMapper;

import com.example.projectpartbprogram_group39.Models.MealPlan;

public class MealPlanMapper implements EntityMapper<MealPlan>{
    @Override
    public MealPlan fromString(String string) {
        return MealPlan.splitString(string);
    }

    @Override
    public String toString(MealPlan object) {
        return object.toString();
    }

    @Override
    public boolean equals(MealPlan obj1, MealPlan obj2) {
        return obj1.getFoodName().equals(obj2.getFoodName());
    }
}
