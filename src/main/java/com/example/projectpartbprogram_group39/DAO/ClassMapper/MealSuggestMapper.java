package com.example.projectpartbprogram_group39.DAO.ClassMapper;

import com.example.projectpartbprogram_group39.Models.mealSuggestion;

public class MealSuggestMapper implements EntityMapper<mealSuggestion>{
    @Override
    public mealSuggestion fromString(String string) {
        return mealSuggestion.splitString(string);
    }

    @Override
    public String toString(mealSuggestion object) {
        return object.toString();
    }

    @Override
    public boolean equals(mealSuggestion obj1, mealSuggestion obj2) {
        return obj1.getFoodName().equals(obj2.getFoodName());
    }
}
