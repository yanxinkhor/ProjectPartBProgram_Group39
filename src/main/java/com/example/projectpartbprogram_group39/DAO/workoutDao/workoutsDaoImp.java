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
    public void editWorkouts(Workouts oldLog, Workouts newLog) {
        File file = new File("workoutLog.txt");
        if(!file.exists()){
            System.out.println("file does not exists");
        }

        List<String> lines;
        List<String> updatedLines = new ArrayList<>();

        try{
            lines = Files.readAllLines(file.toPath());
            for(String line : lines){
                Workouts currentWorkout;

                currentWorkout = Workouts.splitWorkoutString(line);

                if(currentWorkout.getType().equals(oldLog.getType()) && currentWorkout.getCaloriesBurned().equals(oldLog.getCaloriesBurned())
                        && currentWorkout.getBeginDate().equals(oldLog.getBeginDate())){
                    updatedLines.add(newLog.toString());
                }else{
                    updatedLines.add(line);
                }
            }

            try(BufferedWriter writer = new BufferedWriter(new FileWriter("workoutLog.txt"))){
                for(String updatedLine : updatedLines){
                    writer.write(updatedLine);
                    writer.newLine();
                }
            }


        }catch(IOException e){

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
    public void deleteWorkout(Workouts workout) throws IOException {
        File file = new File("workoutLog.txt");

        List<String> lines;
        List<String> updatedLines = new ArrayList<>();

        lines = Files.readAllLines(file.toPath());
        for(String line : lines){

        }
    }
}
