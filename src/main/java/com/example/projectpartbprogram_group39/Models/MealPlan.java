package com.example.projectpartbprogram_group39.Models;

public class MealPlan extends Meal{
    public MealPlan(String foodName, double calories, double protein, double fat) {
        super(foodName, calories, protein, fat);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static MealPlan splitString(String line) {
        Meal meal = Meal.splitString(line);
        return new MealPlan(meal.getFoodName(), meal.getCalories(), meal.getProtein(), meal.getFat());
    }
}
