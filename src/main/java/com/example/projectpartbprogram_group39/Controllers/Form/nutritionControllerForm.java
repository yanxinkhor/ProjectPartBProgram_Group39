package com.example.projectpartbprogram_group39.Controllers.Form;


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
    private Label mealPlanName,mealPlanCalories,mealPlanProtein,mealPlanFat,mealPlanIngridients;

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

    private static DaoInterface<MealPlan> mealPlanDao = new DaoImplement<>("mealPlan.txt",new mealPlanMapper());
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
            displayPlanUI();
            displayTotNutrition();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        suggest = new mealSuggestion("Avocado wrap",350,16,18);


        try {
            List<mealSuggestion> mealSuggest = mealSuggestDao.getAll();
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

    public void addMealPlanCtn(ActionEvent e) throws IOException {
        List<MealPlan> existingMealPlans = mealPlanDao.getAll();
        if (!existingMealPlans.isEmpty()) {
            showAlert.alert(Alert.AlertType.ERROR, "Only one meal plan is allowed. Please delete the existing plan before adding a new one.");
            return;
        }

        addMealPlanCtn.setVisible(true);

    }

    public void back(ActionEvent e){
        addMealPlanCtn.setVisible(false);
    }

    public void addMealPLan(ActionEvent e){
        String mealName = MealNameCtnField.getText();
        String calCountStr = calCountCtnField.getText();
        String proteinStr = proCtnField.getText();
        String fatStr = fatCtnField.getText();
        String ingredientList = listArea.getText();

        if(mealName.isEmpty() || calCountStr.isEmpty() || proteinStr.isEmpty() || fatStr.isEmpty() || ingredientList.isEmpty()){
            showAlert.alert(Alert.AlertType.ERROR,"Plase enter all the fields");
            return;
        }

        try{

            double calCount = Double.parseDouble(calCountStr);
            double protein = Double.parseDouble(proteinStr);
            double fat = Double.parseDouble(fatStr);

            mealPlan = new MealPlan(mealName,calCount,protein,fat,ingredientList);
            mealPlanDao.add(mealPlan);
            showAlert.alert(Alert.AlertType.INFORMATION,"Meal PLan added successful");
            clear(e);
            addMealPlanCtn.setVisible(false);
            displayPlanUI();

        }catch(NumberFormatException ex){
            showAlert.alert(Alert.AlertType.ERROR,"Please enter a valid number");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void displayPlanUI() throws IOException {
        List<MealPlan> planList = mealPlanDao.getAll();
        if (planList != null && !planList.isEmpty()) {

            MealPlan mealPlan = planList.get(0);

            mealPlanName.setText(mealPlan.getFoodName());
            mealPlanCalories.setText(String.valueOf(mealPlan.getCalories()));
            mealPlanProtein.setText(String.valueOf(mealPlan.getProtein()));
            mealPlanFat.setText(String.valueOf(mealPlan.getFat()));
            mealPlanIngridients.setText(mealPlan.getIngredient());
        } else {

            mealPlanName.setText("");
            mealPlanCalories.setText("");
            mealPlanProtein.setText("");
            mealPlanFat.setText("");
            mealPlanIngridients.setText("");
        }
    }

    public void clear(ActionEvent e){
        MealNameCtnField.clear();
        calCountCtnField.clear();
        proCtnField.clear();
        fatCtnField.clear();
    }

    public void deletePlan(ActionEvent e) {
        try {
            List<MealPlan> existingMealPlans = mealPlanDao.getAll();


            if (existingMealPlans.isEmpty()) {
                showAlert.alert(Alert.AlertType.ERROR, "No meal plan exists to delete.");
                return;
            }


            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this goal?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        MealPlan mealDelete = existingMealPlans.get(0);
                        mealPlanDao.delete(mealDelete);
                        displayPlanUI();
                        displayTotNutrition();

                        showAlert.alert(Alert.AlertType.INFORMATION, "Meal plan deleted successfully.");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                } else {
                    showAlert.alert(Alert.AlertType.INFORMATION, "Cancel deletion");
                }
            });

        } catch (IOException ex) {

            showAlert.alert(Alert.AlertType.ERROR, "An error occurred while deleting the meal plan.");
            ex.printStackTrace();
        }

    }

}

