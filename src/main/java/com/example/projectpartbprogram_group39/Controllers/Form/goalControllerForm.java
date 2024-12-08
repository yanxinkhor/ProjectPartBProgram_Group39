package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.DAO.goalDao.goalDaoImp;
import com.example.projectpartbprogram_group39.DAO.goalDao.goalsDaoInterface;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class goalControllerForm implements Initializable {
    @FXML
    TextField goalTypeField,targetField;

    @FXML
    ComboBox<String> goalPriorityField,timeFrame, targetUnit;

    @FXML
    DatePicker goalStartDateField;

    @FXML
    Button addButton, clearButton;

    private final String[] priority = {"High", "Medium", "Low"};
    private final String[] frames = {"daily","weekly"};
    private final String[] unit = {"km", "m", "kcal", "steps","g","l","hour","minute","seconds"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goalPriorityField.getItems().addAll(priority);
        timeFrame.getItems().addAll(frames);
        targetUnit.getItems().addAll(unit);
    }

    public void addGoal(ActionEvent e){
        String goalType = goalTypeField.getText();
        String targetFldStr = targetField.getText();
        String unit = targetUnit.getValue();
        String priorityStr = goalPriorityField.getValue();
        String timeFrameStr = timeFrame.getValue();
        LocalDate startDate = goalStartDateField.getValue();

        if (goalType.isEmpty() || targetFldStr.isEmpty() || unit == null || priorityStr == null || timeFrameStr == null || startDate == null) {
            showAlert.alert(Alert.AlertType.WARNING, "Please fill in all fields.");
            return;
        }

        try{
            int targetValue = Integer.parseInt(targetFldStr);
            String startDateStr = startDate.toString();

            fitnessGoal newGoal = new fitnessGoal(goalType, targetValue, unit ,timeFrameStr, startDateStr, priorityStr);
            goalsDaoInterface goalDao = new goalDaoImp();
            goalDao.addGoal(newGoal);

            showAlert.alert(Alert.AlertType.INFORMATION, "Goal added successfully!");
            clear(e);

        } catch (NumberFormatException ex) {
            showAlert.alert(Alert.AlertType.ERROR, "Invalid Input, value must be a number.");
        }
    }

    public void clear(ActionEvent e){
        goalTypeField.setText("");
        targetField.setText("");
        targetUnit.getSelectionModel().clearSelection();
        goalPriorityField.getSelectionModel().clearSelection();
        timeFrame.getSelectionModel().clearSelection();
        goalStartDateField.setValue(null);


    }



}
