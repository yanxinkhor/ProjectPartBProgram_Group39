package com.example.projectpartbprogram_group39.Models;

import java.util.Date;

public class Workouts {
    private int workoutID;
    private String type;
    private final Date beginDate;
    private String duration;
    private int value;
    private int frequency;
    private double caloriesBurned;


    public Workouts(int workoutID, String type, Date startDate, String duration, int value, int frequency, double caloriesBurned) {
        this.workoutID = workoutID;
        this.type = type;
        this.beginDate = startDate;
        this.duration = duration;
        this.value = value;
        this.frequency = frequency;
        this.caloriesBurned = caloriesBurned;

    }

    public int getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(int workoutID) {
        this.workoutID = workoutID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    @Override
    public String toString() {
        return "Workouts{" +
                "workoutID=" + workoutID +
                ", type='" + type + '\'' +
                ", beginDate=" + beginDate +
                ", duration='" + duration + '\'' +
                ", value=" + value +
                ", frequency=" + frequency +
                ", caloriesBurned=" + caloriesBurned +
                '}';
    }
}
