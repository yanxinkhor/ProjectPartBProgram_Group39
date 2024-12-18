package com.example.projectpartbprogram_group39.Controllers.Form;


import com.example.projectpartbprogram_group39.Controllers.Service.nutritionController;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.MealMapper;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.MealSuggestMapper;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.mealPlanMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.*;
import com.example.projectpartbprogram_group39.Utils.TraineeSession;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
        displayFoodUI();
        displayPlanUI();
        displayTotNutrition();
    }

    public void setMealSuggestionFields(mealSuggestion suggest) {
        suggestMealName.setText(suggest.getFoodName());
        calCountField.setText(String.valueOf(suggest.getCalories()));
        proteinField.setText(String.valueOf(suggest.getProtein()));
        fatField.setText(String.valueOf(suggest.getFat()));
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