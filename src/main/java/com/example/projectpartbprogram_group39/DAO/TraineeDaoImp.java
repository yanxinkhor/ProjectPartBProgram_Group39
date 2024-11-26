package com.example.projectpartbprogram_group39.DAO;

import com.example.projectpartbprogram_group39.Models.Trainee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TraineeDaoImp implements daoInterface {
    @Override
    public void StoreTrainee(Trainee trainee) {

        File file = new File("userInfo.txt");

        if (!file.exists()) {
            System.out.println("File does not exist. Creating a new file...");
        } else {
            System.out.println("File already exists.");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(trainee.toString());
            writer.newLine();
            writer.flush();

        } catch (IOException ex) {
            System.err.println("Error writing to file: " + ex.getMessage());
        }

    }

    @Override
    public boolean userExists(String username, String email) throws IOException {
        File file = new File("userInfo.txt");

        if (!file.exists()) {
            System.out.println("userInfo.txt file does not exist.");
            return false;
        }

        List<String> lines = Files.readAllLines(Paths.get("userInfo.txt"));
        for (String line : lines) {
            try {
                Trainee trainee = Trainee.splitString(line);
                if (trainee.getUsername().equals(username) && trainee.getEmail().equals(email)) {
                    return true;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Skipping malformed line: " + line);
            }
        }
        return false;
    }

    @Override
    public List<Trainee> getAllTrainees() {
        return List.of();
    }
}