package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.goalDao.goalDaoImp;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class editGoalController implements Initializable {

    @FXML
    private TextField updatedType, updatedTarget;

    @FXML
    private DatePicker updatedDate;

    @FXML
    private ComboBox<String> updatedPriority, updatedUnit;

    private final String[] intensity = {"High", "Medium", "Low"};
    private final String[] units = {"km", "m", "kcal", "steps","g","l","hour","minute","seconds"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updatedPriority.getItems().addAll(intensity);
        updatedUnit.getItems().addAll(units);
    }

    private goalDaoImp goalDao = new goalDaoImp();
    private fitnessGoal goal;

    public void setEditable(fitnessGoal goal){
        this.goal = goal;

        updatedType.setText(goal.getGoalType());
        updatedTarget.setText(String.valueOf(goal.getGoalValue()));
        updatedUnit.setValue(goal.getUnit());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(goal.getStartDate());
            java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            updatedDate.setValue(localDate);
        }catch(ParseException e){
            e.printStackTrace();
        }
        updatedPriority.setValue(goal.getPriority());
    }

    public void reservedChanged(ActionEvent e) throws IOException {

        if(goal != null){
            if (updatedType.getText().isEmpty() || updatedTarget.getText().isEmpty() || updatedUnit.getValue() == null
                    || updatedDate.getValue() == null || updatedPriority.getValue() == null) {
                showAlert.alert(Alert.AlertType.ERROR, "Please fill in all fields.");
                return;
            }

            if(goalDao.goalExists(updatedType.getText())){
                showAlert.alert(Alert.AlertType.ERROR, "The goal already exists");
                return;
            }

            fitnessGoal original = new fitnessGoal(goal.getGoalType(),goal.getGoalValue(),goal.getUnit(),goal.getTimeFrame(),goal.getStartDate(),goal.getPriority());
            goal.setGoalType(updatedType.getText());

            try{
                goal.setGoalValue(Double.parseDouble(updatedTarget.getText()));
            }catch(NumberFormatException event){
                showAlert.alert(Alert.AlertType.ERROR, "Please enter a valid target value.");
                updatedTarget.setText("");
                return;
            }

            goal.setUnit(updatedUnit.getValue());
            goal.setStartDate(String.valueOf(updatedDate.getValue()));
            goal.setPriority(updatedPriority.getValue());

            goalDao.updateGoal(original,goal);

            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public void clearAll(){
        updatedType.clear();
        updatedTarget.clear();
        updatedUnit.getSelectionModel().clearSelection();
        updatedDate.setValue(null);
        updatedPriority.getSelectionModel().clearSelection();
    }
}
