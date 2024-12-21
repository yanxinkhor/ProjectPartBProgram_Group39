package com.example.projectpartbprogram_group39.Models;


public class fitnessGoal {
    private String goalType;
    private double goalValue;
    private String unit;
    private String timeFrame;
    private String startDate;
    private String priority;

    public fitnessGoal(String goalType, double goalValue,String unit, String timeFrame, String startDate, String priority) {
        this.goalType = goalType;
        this.goalValue = goalValue;
        this.unit = unit;
        this.timeFrame = timeFrame;
        this.startDate = startDate;
        this.priority = priority;
    }

    public String getGoalType() {
        return goalType;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public double getGoalValue() {
        return goalValue;
    }

    public void setGoalValue(double goalValue) {
        this.goalValue = goalValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return goalType + "/" + goalValue + "/" + unit + "/" + timeFrame + "/"
                + startDate + "/" + priority;

    }

    public static fitnessGoal splitGoalString(String line){
        String[] parts = line.split("/");

        if (parts.length == 6) {

            String goalType = parts[0];
            double goalValue = Double.parseDouble(parts[1]);
            String goalUnit = parts[2];
            String timeFrame = parts[3];
            String startDate = parts[4];
            String priority = parts[5];

            return new fitnessGoal(goalType, goalValue, goalUnit, timeFrame, startDate, priority);
        } else {
            throw new IllegalArgumentException("Invalid input format. Expected 6 parts but got: " + parts.length);
        }
    }

}
