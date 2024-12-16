package com.example.projectpartbprogram_group39.Utils;

import com.example.projectpartbprogram_group39.Models.Trainee;

public class TraineeSession {

    private static TraineeSession instance;
    private Trainee currentTrainee;

    private TraineeSession() {

    }

    public static TraineeSession getInstance() {
        if (instance == null) {
            instance = new TraineeSession();
        }
        return instance;
    }

    public Trainee getCurrentTrainee() {
        return currentTrainee;
    }

    public void setCurrentTrainee(Trainee trainee) {
        this.currentTrainee = trainee;
    }
}