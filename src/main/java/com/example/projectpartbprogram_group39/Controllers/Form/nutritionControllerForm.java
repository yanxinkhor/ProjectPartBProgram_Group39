package com.example.projectpartbprogram_group39.Controllers.Form;


import com.example.projectpartbprogram_group39.DAO.ClassMapper.MealMapper;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.MealSuggestMapper;
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

public class nutritionControllerForm implements Initializable {

    @FXML
    private Button adminEdit,adminSave,AddMealPlanBtn;

    @FXML
    private Label foodName1,foodName2,foodName3,foodCal1,foodCal2,foodCal3;

    @FXML
    private Label dailycalories,proVal,dailyfat;

    @FXML
    private TextField suggestMealName,calCountField,proteinField,fatField;

    @FXML
    private Label mealPlanName,mealPlanCalories,mealPlanProtein,mealPlanFat;

    @FXML
     private VBox addMealPlanCtn;

    @FXML
    private TextField MealNameCtnField,calCountCtnField,proCtnField,fatCtnField;

    @FXML
    private TextArea listArea;

    @FXML
    private Button addBtn,ClearBtn,BackBtn;

    mealSuggestion suggest;
    MealPlan mealPlan;

    private static DaoInterface<mealSuggestion> mealSuggestDao = new DaoImplement<>("mealSuggest.txt",new MealSuggestMapper());
    private static DaoInterface<Meal> mealDao = new DaoImplement<>("meal.txt",new MealMapper());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Trainee trainee = TraineeSession.getInstance().getCurrentTrainee();

        if(trainee.getUsername().equals("Admin") && trainee.getPassword().equals("admin1234")){
            adminEdit.setVisible(true);
            adminSave.setVisible(true);
        }
        try {
            displayFoodUI();
            displayTotNutrition();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        suggest = new mealSuggestion("Avocado wrap",350,16,18);


        try {
            List<mealSuggestion> mealSuggest = mealSuggestDao.getAll();
            System.out.println(mealSuggest);
            if (mealSuggest.isEmpty()) {
               mealSuggestDao.add(suggest);
            }

            setMealSuggestionFields(suggest);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setMealSuggestionFields(mealSuggestion suggest) {
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
        List<Meal> mealList = mealDao.getAll();

        Label[] foods = {foodName1,foodName2,foodName3};
        Label[] calories = {foodCal1,foodCal2,foodCal3};

        for (int i = 0; i < Math.min(mealList.size(), 3); i++){
            Meal meal = mealList.get(i);
            foodUI(foods[i],calories[i],meal);
        }


    }

    private void foodUI(Label food, Label Cal, Meal meal){
        food.setText(meal.getFoodName());
        Cal.setText(String.valueOf(meal.getCalories()));
    }

    @FXML
    private void displayTotNutrition() throws IOException {
        List<Meal> mealList = mealDao.getAll();

        double totalCalories = 0;
        double totalProtein = 0;
        double totalFat = 0;

        for (Meal meal : mealList) {
            totalCalories += meal.getCalories();
            totalProtein += meal.getProtein();
            totalFat += meal.getFat();
        }

        dailycalories.setText(String.valueOf(totalCalories));
        proVal.setText(String.valueOf(totalProtein));
        dailyfat.setText(String.valueOf(totalFat));

    }

    public void editMealSuggest(ActionEvent e){
        suggestMealName.setEditable(true);
        calCountField.setEditable(true);
        proteinField.setEditable(true);
        fatField.setEditable(true);
    }

    public void saveMealSuggest(ActionEvent e) {
        try {
            String updatedMealName = suggestMealName.getText();
            double updatedCalories = Double.parseDouble(calCountField.getText());
            double updatedProtein = Double.parseDouble(proteinField.getText());
            double updatedFat = Double.parseDouble(fatField.getText());

            mealSuggestion updatedSuggestion = new mealSuggestion(updatedMealName, updatedCalories, updatedProtein, updatedFat);
            showAlert.alert(Alert.AlertType.INFORMATION,"updated suggestion successfully");
            mealSuggestDao.update(suggest, updatedSuggestion);
        }catch(NumberFormatException ex){
            showAlert.alert(Alert.AlertType.ERROR,"Please enter a valid number");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addMealPlan(ActionEvent e){
        addMealPlanCtn.setVisible(true);
    }

    public void back(ActionEvent e){
        addMealPlanCtn.setVisible(false);
    }

}

