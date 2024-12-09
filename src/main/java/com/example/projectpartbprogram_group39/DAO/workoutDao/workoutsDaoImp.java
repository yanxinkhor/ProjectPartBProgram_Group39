package com.example.projectpartbprogram_group39.DAO.workoutDao;
import com.example.projectpartbprogram_group39.Models.Workouts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class workoutsDaoImp implements workoutsDaoInterface {
    @Override
    public void addWorkout(Workouts workout) {
        File file = new File("workoutLog.txt");

        if(!file.exists()){
            System.out.println("file does not exist");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {

            writer.write(workout.toString());
            writer.newLine();
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Workouts> getAllWorkouts() throws IOException {
        List<Workouts> workoutList = new ArrayList<>();

        File file = new File("workoutLog.txt");
        if(!file.exists()){
            return workoutList;
        }

        List<String> lines = Files.readAllLines(Paths.get("workoutLog.txt"));

        for(String line:lines){
            Workouts workouts = Workouts.splitWorkoutString(line);
            workoutList.add(workouts);
        }
        return workoutList;
    }

    @Override
    public void editWorkouts(Workouts workout) {
        File file = new File("workoutLog.txt");
        if(!file.exists()){
            System.out.println("file does not exists");
        }
    }

    @Override
    public boolean logExists(String workoutType) throws IOException {
        File file = new File("workoutLog.txt");
        if(!file.exists()){
            return false;
        }
        List<String> lines = Files.readAllLines(Paths.get("workoutLog.txt"));
        for (String line : lines) {

            Workouts workouts = Workouts.splitWorkoutString(line);
            if (workouts.getType().equals(workoutType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteWorkout(Workouts workout) {

    }
}
