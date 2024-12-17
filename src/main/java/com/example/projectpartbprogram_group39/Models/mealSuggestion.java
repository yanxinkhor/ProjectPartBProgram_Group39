package com.example.projectpartbprogram_group39.Models;

public class mealSuggestion extends Meal{

    public mealSuggestion(String foodName, double calories, double protein, double fat) {
        super(foodName, calories, protein, fat);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static mealSuggestion splitString(String line) {
        Meal meal = Meal.splitString(line);
        return new mealSuggestion(meal.getFoodName(), meal.getCalories(), meal.getProtein(), meal.getFat());
    }

}
