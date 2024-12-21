package com.example.projectpartbprogram_group39.Models;

public class Workouts {
    private String type;
    private String caloriesBurned;
    private double value;
    private String duration;
    private int frequency;
    private String beginDate;
    private String imgUrl;

    public Workouts(String type) {
        this.type = type;
    }

    public Workouts(String type, String caloriesBurned,double value, String duration, int frequency, String startDate, String imgUrl) {
        this.type = type;
        this.caloriesBurned = caloriesBurned;
        this.value = value;
        this.duration = duration;
        this.frequency = frequency;
        this.beginDate = startDate;
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public String getCaloriesBurned() {
        return caloriesBurned;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDuration() {
        return duration;
    }

    public int getFrequency() {
        return frequency;
    }

    public String getBeginDate() {
        return beginDate;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public String toString() {
        return type + "," + caloriesBurned + "," + value + "," + duration + ","
                + frequency + "," + beginDate + "," + imgUrl;
    }

    public static Workouts splitWorkoutString(String line){
        String[] parts = line.split(",");

        if(parts.length == 7){
            String workoutType = parts[0];
            String caloriesBurned = parts[1];
            double value = Double.parseDouble(parts[2]);
            String duration = parts[3];
            int frequency = Integer.parseInt(parts[4]);
            String beginDate = parts[5];
            String imgUrl = parts[6].trim();

            return new Workouts(workoutType,caloriesBurned,value,duration,frequency,beginDate,imgUrl);

        }else{
            throw new IllegalArgumentException("Invalid input format. Expected 7 parts but got: " + parts.length);
        }
    }
}
