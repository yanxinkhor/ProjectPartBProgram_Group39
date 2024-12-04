package com.example.projectpartbprogram_group39.Models;

import java.util.Date;

public class Workouts {
    private int workoutID;
    private String type;
    private Date startDate;
    private String duration;
    private int value;
    private int frequency;
    private String priority;

    public Workouts(int workoutID, String type, Date startDate, String duration, int value, int frequency, String priority) {
        this.workoutID = workoutID;
        this.type = type;
        this.startDate = startDate;
        this.duration = duration;
        this.value = value;
        this.frequency = frequency;
        this.priority = priority;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Workouts{" +
                "type='" + type + '\'' +
                ", startDate=" + startDate +
                ", duration='" + duration + '\'' +
                ", value=" + value +
                ", frequency=" + frequency +
                ", priority='" + priority + '\'' +
                '}';
    }
}
