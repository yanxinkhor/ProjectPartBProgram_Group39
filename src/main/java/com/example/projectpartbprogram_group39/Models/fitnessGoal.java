package com.example.projectpartbprogram_group39.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class fitnessGoal {
    private int goaID;
    private String goalType;
    private int goalValue;
    private String timeFrame;
    private Date startDate;
    private String priority;

    public fitnessGoal(int goaID, String goalType, int goalValue, String timeFrame, Date startDate, String priority) {
        this.goaID = goaID;
        this.goalType = goalType;
        this.goalValue = goalValue;
        this.timeFrame = timeFrame;
        this.startDate = startDate;
        this.priority = priority;
    }

    public int getGoaID() {
        return goaID;
    }

    public void setGoaID(int goaID) {
        this.goaID = goaID;
    }

    public String getGoalType() {
        return goalType;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public int getGoalValue() {
        return goalValue;
    }

    public void setGoalValue(int goalValue) {
        this.goalValue = goalValue;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
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
        return goaID + "/" + goalType + "/" + goalValue + "/" + timeFrame + "/"
                +startDate + "/" + priority;

    }

    public static fitnessGoal splitGoalString(String line){
        String[] parts = line.split("/");

        if (parts.length == 6) {

            int goalID = Integer.parseInt(parts[0]);
            String goalType = parts[1];
            int goalValue = Integer.parseInt(parts[2]);
            String timeFrame = parts[3];

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = null;

            try {
                startDate = dateFormat.parse(parts[4]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String priority = parts[5];

            return new fitnessGoal(goalID, goalType, goalValue, timeFrame, startDate, priority);
        } else {
            throw new IllegalArgumentException("Invalid number of input");
        }
    }

}
