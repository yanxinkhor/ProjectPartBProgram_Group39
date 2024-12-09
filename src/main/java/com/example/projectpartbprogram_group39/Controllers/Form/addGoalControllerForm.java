package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Controllers.Service.addGoalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class addGoalControllerForm implements Initializable {
    @FXML
    TextField goalTypeField, targetField;

    @FXML
    ComboBox<String> goalPriorityField, timeFrame, targetUnit;

    @FXML
    DatePicker goalStartDateField;

    @FXML
    Button addButton, clearButton;

    private final String[] priority = {"High", "Medium", "Low"};
    private final String[] frames = {"daily", "weekly"};
    private final String[] unit = {"km", "m", "kcal", "steps", "g", "l", "hour", "minute", "seconds"};

    private addGoalController addGoalControllers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goalPriorityField.getItems().addAll(priority);
        timeFrame.getItems().addAll(frames);
        targetUnit.getItems().addAll(unit);

        addGoalControllers = new addGoalController();
        setupFieldNavigation();
    }

    @FXML
    private void addGoal(ActionEvent e) throws IOException {
        String goalType = goalTypeField.getText();
        String targetFldStr = targetField.getText();
        String unit = targetUnit.getValue();
        String priorityStr = goalPriorityField.getValue();
        String timeFrameStr = timeFrame.getValue();
        LocalDate startDate = goalStartDateField.getValue();

        addGoalControllers.addGoal(goalType, targetFldStr, unit, priorityStr, timeFrameStr, startDate);
        clear(e);
    }

    @FXML
    private void clear(ActionEvent e) {
        goalTypeField.setText("");
        targetField.setText("");
        targetUnit.getSelectionModel().clearSelection();
        goalPriorityField.getSelectionModel().clearSelection();
        timeFrame.getSelectionModel().clearSelection();
        goalStartDateField.setValue(null);
    }

    private void setupFieldNavigation() {
        goalTypeField.setOnKeyPressed(event -> {
            if ("ENTER".equals(event.getCode().toString())) {
                targetField.requestFocus();
            }
        });

        targetField.setOnKeyPressed(event -> {
            if ("ENTER".equals(event.getCode().toString())) {
                targetUnit.requestFocus();
            }
        });
    }
}
