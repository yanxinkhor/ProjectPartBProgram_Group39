package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.DAO.goalDao.goalDaoImp;
import com.example.projectpartbprogram_group39.DAO.goalDao.goalsDaoInterface;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class goalControllerForm implements Initializable {
    @FXML
    TextField goalTypeField,targetField;

    @FXML
    ComboBox<String> targetUnit,goalPriorityField,timeFrame;

    @FXML
    DatePicker goalStartDateField;

    @FXML
    Button addButton, clearButton;

    private String[] units = {"km","kg","kcal","m","steps"};
    private String[] priority = {"High", "Medium", "Low"};
    private String[] frames = {"daily","weekly"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        targetUnit.getItems().addAll(units);
        goalPriorityField.getItems().addAll(priority);
        timeFrame.getItems().addAll(frames);
    }

    public void addGoal(ActionEvent e){
        String goalType = goalTypeField.getText();
        String targetFldStr = targetField.getText();
        String unit = targetUnit.getValue();
        String priorityStr = goalPriorityField.getValue();
        String timeFrameStr = timeFrame.getValue();
        LocalDate startDate = goalStartDateField.getValue();

        if (goalType.isEmpty() || targetFldStr.isEmpty() || unit == null || priorityStr == null || timeFrameStr == null ||startDate == null) {
            showAlert.alert(Alert.AlertType.WARNING, "Please fill in all fields.");
            return;
        }

        try{
            int targetValue = Integer.parseInt(targetFldStr);
            int nextId = getNextGoalID();

            fitnessGoal newGoal = new fitnessGoal(nextId, goalType, targetValue, timeFrameStr, Date.valueOf(startDate), priorityStr);
            goalsDaoInterface goalDao = new goalDaoImp();
            goalDao.addGoal(newGoal);

            showAlert.alert(Alert.AlertType.INFORMATION, "Goal added successfully!");
            clear(e);

        } catch (NumberFormatException ex) {
            showAlert.alert(Alert.AlertType.ERROR, "Invalid Input, value must be a number.");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void clear(ActionEvent e){
        goalTypeField.setText("");
        targetField.setText("");
        targetUnit.getSelectionModel().clearSelection();
        goalPriorityField.getSelectionModel().clearSelection();
        goalStartDateField.setValue(null);

    }

    private int getNextGoalID() throws IOException {
        File goalFile = new File("userGoals.txt");

        if (!goalFile.exists()) {
            return 1;
        }

        int maxId = 0;

        List<String> lines = Files.readAllLines(Paths.get("userGoals.txt"));

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }
            try {
                fitnessGoal goal = fitnessGoal.splitGoalString(line);
                if (goal.getGoaID() > maxId) {
                    maxId = goal.getGoaID();
                }
            } catch (IllegalArgumentException ex) {
                System.err.println("Skipping invalid line: " + line);
            }
        }

        return maxId + 1;
    }


}
