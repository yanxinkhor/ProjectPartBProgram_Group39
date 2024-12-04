package com.example.projectpartbprogram_group39.DAO.TraineeDao;

import com.example.projectpartbprogram_group39.Models.Trainee;
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

public class TraineeDaoImp implements TraineeDaoInterface {
    @Override
    public void StoreTrainee(Trainee trainee) {
        File file = new File("userInfo.txt");

        if (!file.exists()) {
            System.out.println("File does not exist.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(trainee.toString());
            writer.newLine();
            writer.flush();

        } catch (IOException ex) {
            showAlert.alert(Alert.AlertType.ERROR, "Error has occured");
        }

    }

    @Override
    public boolean userExists(String username, String email) throws IOException {
        File file = new File("userInfo.txt");

        if (!file.exists()) {
            return false;
        }

        List<String> lines = Files.readAllLines(Paths.get("userInfo.txt"));
        for (String line : lines) {

            Trainee trainee = Trainee.splitString(line);
            if (trainee.getUsername().equals(username) && trainee.getEmail().equals(email)) {
                return true;
            }

        }
        return false;
    }

    @Override
    public List<Trainee> getAllTrainees() throws IOException {
        List<Trainee> trainees = new ArrayList<>();
        File file = new File("userInfo.txt");

        if (!file.exists()) {
            return trainees;
        }

        List<String> lines = Files.readAllLines(Paths.get("userInfo.txt"));

        for (String line : lines) {
            Trainee trainee = Trainee.splitString(line);
            trainees.add(trainee);
        }

        return trainees;
    }
}