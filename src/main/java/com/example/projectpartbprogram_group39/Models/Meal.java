package com.example.projectpartbprogram_group39.Models;

public class Meal {
    private String foodName;
    private double calories;
    private double protein;
    private double fat;


    public Meal(String foodName, double calories, double protein, double fat) {
        this.foodName = foodName;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }


    public double getFat() {
        return fat;
    }

    @Override
    public String toString() {
        return foodName + '/' + calories + "/" + protein + "/" + fat;
    }

    public static Meal splitString(String line) {
        String[] parts = line.split("/");

        if (parts.length == 4) {
            String foodName = parts[0];
            double calories = Double.parseDouble(parts[1]);
            double protein = Double.parseDouble(parts[2]);
            double fat = Double.parseDouble(parts[3]);
            return new Meal(foodName, calories, protein,fat);
        } else {
            throw new IllegalArgumentException("Invalid input format. Expected 4 parts but got: " + parts.length);
        }
    }
}
