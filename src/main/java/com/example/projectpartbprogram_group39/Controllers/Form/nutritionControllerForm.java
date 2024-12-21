package com.example.projectpartbprogram_group39.Controllers.Form;


import com.example.projectpartbprogram_group39.Controllers.Service.nutritionController;
import com.example.projectpartbprogram_group39.Models.*;
import com.example.projectpartbprogram_group39.Utils.TraineeSession;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class nutritionControllerForm {

    @FXML
    private Button adminEdit, adminSave;

    @FXML
    private Label foodName1, foodName2, foodName3, foodCal1, foodCal2, foodCal3;

    @FXML
    private Label dailycalories, proVal, dailyfat;

    @FXML
    private TextField suggestMealName, calCountField, proteinField, fatField;

    @FXML
    private Label mealPlanName, mealPlanCalories, mealPlanProtein, mealPlanFat, mealPlanIngridients;

    @FXML
    private VBox addMealPlanCtn;

    @FXML
    private TextField MealNameCtnField, calCountCtnField, proCtnField, fatCtnField;

    @FXML
    private TextArea listArea;

    private nutritionController nutritionControllerLogic = new nutritionController();

    @FXML
    public void initialize() throws IOException {
        nutritionControllerLogic.initSuggestMeal(this);
        checkAdmin();
        displayFoodUI();
        displayPlanUI();
        displayTotNutrition();
    }

    private void checkAdmin() {

        Trainee trainee = TraineeSession.getInstance().getCurrentTrainee();

        if ("Admin".equals(trainee.getUsername()) && "admin1234".equals(trainee.getPassword())) {
            adminSave.setVisible(true);
            adminEdit.setVisible(true);
        } else {

            adminEdit.setVisible(false);
            adminSave.setVisible(false);
        }
    }

    public void setMealSuggestionFields() throws IOException {
        List<mealSuggestion> mealSuggestions = nutritionControllerLogic.getAllMealSuggest();
        if (!mealSuggestions.isEmpty()) {
            mealSuggestion suggestion = mealSuggestions.get(0); // Assuming you only have one suggestion
            suggestMealName.setText( suggestion.getFoodName());
            calCountField.setText(String.valueOf( suggestion.getCalories()));
            proteinField.setText(String.valueOf( suggestion.getProtein()));
            fatField.setText(String.valueOf( suggestion.getFat()));
        }
    }

    @FXML
    private void addMeal(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/view-meals.fxml"));
        Scene scene = new Scene(loader.load());
        Stage newWindow = new Stage();
        newWindow.setScene(scene);
        newWindow.setResizable(false);
        newWindow.showAndWait();
    }

    @FXML
    private void displayFoodUI() throws IOException {
        nutritionControllerLogic.displayFoodUI(foodName1, foodName2, foodName3, foodCal1, foodCal2, foodCal3);
    }

    @FXML
    private void displayTotNutrition() throws IOException {
        nutritionControllerLogic.displayTotNutrition(dailycalories, proVal, dailyfat);
    }

    @FXML
    public void editMealSuggest(ActionEvent e) {
        suggestMealName.setEditable(true);
        calCountField.setEditable(true);
        proteinField.setEditable(true);
        fatField.setEditable(true);
    }

    @FXML
    public void saveMealSuggest(ActionEvent e) {
        try {
            nutritionControllerLogic.saveMealSuggest(suggestMealName.getText(), calCountField.getText(), proteinField.getText(), fatField.getText());
            showAlert.alert(Alert.AlertType.INFORMATION, "Updated suggestion successfully");
        } catch (Exception ex) {
            showAlert.alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
        }
    }

    @FXML
    public void addMealPlanCtn(ActionEvent e) throws IOException {
        nutritionControllerLogic.addMealPlanCtn(addMealPlanCtn);
    }

    @FXML
    public void back(ActionEvent e) {
        addMealPlanCtn.setVisible(false);
    }

    @FXML
    public void addMealPLan(ActionEvent e) {
        try {
            nutritionControllerLogic.addMealPlan(MealNameCtnField.getText(), calCountCtnField.getText(), proCtnField.getText(), fatCtnField.getText(), listArea.getText());
            clear(e);
            addMealPlanCtn.setVisible(false);
            displayPlanUI();
        } catch (Exception ex) {
            showAlert.alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
        }
    }

    @FXML
    private void displayPlanUI() throws IOException {
        nutritionControllerLogic.displayPlanUI(mealPlanName, mealPlanCalories, mealPlanProtein, mealPlanFat, mealPlanIngridients);
    }

    @FXML
    public void clear(ActionEvent e) {
        MealNameCtnField.clear();
        calCountCtnField.clear();
        proCtnField.clear();
        fatCtnField.clear();
    }

    @FXML
    public void deletePlan(ActionEvent e) {
        try {
            nutritionControllerLogic.deletePlan();
            displayPlanUI();
            displayTotNutrition();
        } catch (Exception ex) {
            showAlert.alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
        }
    }
}