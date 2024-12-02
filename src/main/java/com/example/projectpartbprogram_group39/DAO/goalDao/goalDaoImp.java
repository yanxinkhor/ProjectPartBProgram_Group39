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

public class goalDaoImp implements goalsDaoInterface{
    @Override
    public void addGoal(fitnessGoal goal) {
        File goalfile = new File("userGoals.txt");

        if(!goalfile.exists()){
            System.out.println("file doesn't exist");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(goalfile, true))){

            writer.write(goal.toString());
            writer.newLine();
            writer.flush();

        }catch(IOException e){
            showAlert.alert(Alert.AlertType.ERROR, "Error has occured");
        }
    }

    @Override
    public void editGoal(fitnessGoal goal) {

    }

    @Override
    public void updateGoal(fitnessGoal goal) {

    }

    @Override
    public void deleteGoal(fitnessGoal goal) {

    }

    @Override
    public List<fitnessGoal> getGoalList() throws IOException {
        List<fitnessGoal> fitnessGoals = new ArrayList<>();
        File goalfile = new File("userGoals.txt");

        if(!goalfile.exists()){
            return fitnessGoals;
        }

        List<String> lines = Files.readAllLines(Paths.get("userGoals.txt"));

        for (String line : lines){
            fitnessGoal goal = fitnessGoal.splitGoalString(line);
            fitnessGoals.add(goal);
        }
        return fitnessGoals;
    }
}
