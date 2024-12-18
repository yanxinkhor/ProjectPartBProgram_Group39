package com.example.projectpartbprogram_group39.Models;

public class MealPlan extends Meal{
    private String ingredient;

    public MealPlan(String foodName, double calories, double protein, double fat,String ingredient) {
        super(foodName, calories, protein, fat);
        this.ingredient = ingredient;
    }

    public String getIngredient() {
        return ingredient;
    }

    @Override
    public String toString() {
        return super.toString() + "/" + ingredient;
    }

    public static MealPlan splitString(String line) {
        String[] parts = line.split("/");

        if (parts.length == 5) {
            String foodName = parts[0];
            double calories = Double.parseDouble(parts[1]);
            double protein = Double.parseDouble(parts[2]);
            double fat = Double.parseDouble(parts[3]);
            String ingredient = parts[4];
            return new MealPlan(foodName, calories, protein,fat,ingredient);
        } else {
            throw new IllegalArgumentException("Invalid input format. Expected 5 parts but got: " + parts.length);
        }
    }
}
