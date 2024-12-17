package com.example.projectpartbprogram_group39.DAO.ClassMapper;

import com.example.projectpartbprogram_group39.Models.Meal;

public class MealMapper implements EntityMapper<Meal>{
    @Override
    public Meal fromString(String string) {
        return Meal.splitString(string);
    }

    @Override
    public String toString(Meal object) {
        return object.toString();
    }

    @Override
    public boolean equals(Meal obj1, Meal obj2) {
       return obj1.getFoodName().equals(obj2.getFoodName());
    }
}
