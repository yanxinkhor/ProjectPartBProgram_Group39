package com.example.projectpartbprogram_group39.DAO.goalDao;

import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.scene.control.Alert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class goalDaoImp implements goalsDaoInterface {
    @Override
    public void addGoal(fitnessGoal goal) {
        String period = goal.getTimeFrame().toLowerCase();
        String fileName = period.equals("daily") ? "dailyGoal.txt" : "weeklyGoal.txt";
        File goalfile = new File(fileName);

        if (!goalfile.exists()) {
            System.out.println("file doesn't exist");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(goalfile, true))) {

            writer.write(goal.toString());
            writer.newLine();
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateGoal(fitnessGoal oldGoal, fitnessGoal newGoal) {
        String period = oldGoal.getTimeFrame().toLowerCase();
        String fileName = period.equals("daily") ? "dailyGoal.txt" : "weeklyGoal.txt";

        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("File does not exist");
            return;
        }

        List<String> lines;
        List<String> updatedLines = new ArrayList<>();
        boolean isUpdated = false;

        try {
            lines = Files.readAllLines(file.toPath());

            for (String line : lines) {
                fitnessGoal currentGoal;
                try {
                    currentGoal = fitnessGoal.splitGoalString(line);
                } catch (IllegalArgumentException e) {
                    updatedLines.add(line);
                    continue;
                }

                if (currentGoal.getGoalType().trim().equals(oldGoal.getGoalType().trim()) &&
                        currentGoal.getTimeFrame().trim().equals(oldGoal.getTimeFrame().trim()) &&
                        currentGoal.getStartDate().trim().equals(oldGoal.getStartDate().trim())) {

                    updatedLines.add(newGoal.toString());
                    isUpdated = true;
                } else {
                    updatedLines.add(line);
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            }

            if (isUpdated) {
                showAlert.alert(Alert.AlertType.INFORMATION, "Goal updated successfully.");
            } else {
                showAlert.alert(Alert.AlertType.WARNING, "Goal not found.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGoal(fitnessGoal goal) throws IOException {
        String period = goal.getTimeFrame().toLowerCase();
        String fileName = period.equals("daily") ? "dailyGoal.txt" : "weeklyGoal.txt";

        File file = new File(fileName);

        if(!file.exists()){
            System.out.println("file does not exists");
            return;
        }

        List<String> lines;
        List<String> updatedLines = new ArrayList<>();
        boolean isDeleted = false;

            lines = Files.readAllLines(file.toPath());
            for(String line : lines){
                fitnessGoal currentGoal;
                try{
                    currentGoal = fitnessGoal.splitGoalString(line);
                }catch(IllegalArgumentException e){
                    updatedLines.add(line);
                    continue;
                }

                if(currentGoal.getGoalType().equals(goal.getGoalType()) && currentGoal.getStartDate().equals(goal.getStartDate())
                && currentGoal.getTimeFrame().equals(goal.getTimeFrame())){
                    isDeleted = true;
                }else {
                    updatedLines.add(line);
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(String updateLine : updatedLines){
                writer.write(updateLine);
                writer.newLine();
                writer.flush();
            }

            if(isDeleted){
                showAlert.alert(Alert.AlertType.INFORMATION, "goal deleted successfully");
            }else{
                showAlert.alert(Alert.AlertType.ERROR, "Goal not found");
            }

    }

    @Override
    public boolean goalExists(String goalType) throws IOException {
        String[] fileNames = {"dailyGoal.txt", "weeklyGoal.txt"};

        for (String fileName : fileNames) {
            File goalFile = new File(fileName);

            if (!goalFile.exists()) {
                continue;
            }

            List<String> lines = Files.readAllLines(goalFile.toPath());
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    fitnessGoal goal = fitnessGoal.splitGoalString(line);

                    if (goal.getGoalType().equalsIgnoreCase(goalType.trim())) {
                        return true;
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    @Override
    public List<fitnessGoal> getGoalList(String period) throws IOException {
        List<fitnessGoal> fitnessGoals = new ArrayList<>();

        String fileName = period.equalsIgnoreCase("daily") ? "dailyGoal.txt" : "weeklyGoal.txt";
        File goalfile = new File(fileName);

        if (!goalfile.exists()) {
            return fitnessGoals;
        }

        List<String> lines = Files.readAllLines(Paths.get(fileName));

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }

            try {
                fitnessGoal goal = fitnessGoal.splitGoalString(line);
                goal.setTimeFrame(period);
                fitnessGoals.add(goal);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return fitnessGoals;
    }

}
