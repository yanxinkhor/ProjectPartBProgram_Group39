package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.Controllers.Form.nutritionControllerForm;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.MealMapper;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.MealSuggestMapper;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.MealPlanMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.*;
import com.example.projectpartbprogram_group39.Utils.showAlert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class nutritionController {

    private static DaoInterface<MealPlan> mealPlanDao = new DaoImplement<>("mealPlan.txt", new MealPlanMapper());
    private static DaoInterface<mealSuggestion> mealSuggestDao = new DaoImplement<>("mealSuggest.txt", new MealSuggestMapper());
    private static DaoInterface<Meal> mealDao = new DaoImplement<>("meal.txt", new MealMapper());

    private mealSuggestion suggest;

    public void initSuggestMeal(nutritionControllerForm formController) throws IOException {
        suggest = new mealSuggestion("Avocado wrap", 350, 16, 18);

        List<mealSuggestion> mealSuggest = mealSuggestDao.getAll();
        if (mealSuggest.isEmpty()) {
            mealSuggestDao.add(suggest);
        }
        formController.setMealSuggestionFields();
    }

    public List<mealSuggestion> getAllMealSuggest() throws IOException {
        return mealSuggestDao.getAll();
    }


    public void displayFoodUI(Label foodName1, Label foodName2, Label foodName3, Label foodCal1, Label foodCal2, Label foodCal3) throws IOException {
        List<Meal> mealList = mealDao.getAll();

        Label[] foods = {foodName1, foodName2, foodName3};
        Label[] calories = {foodCal1, foodCal2, foodCal3};

        for (int i = 0; i < Math.min(mealList.size(), 3); i++) {
            Meal meal = mealList.get(i);
            foodUI(foods[i], calories[i], meal);
        }
    }

    private void foodUI(Label food, Label Cal, Meal meal) {
        food.setText(meal.getFoodName());
        Cal.setText(String.valueOf(meal.getCalories()));
    }

    public void displayTotNutrition(Label dailycalories, Label proVal, Label dailyfat) throws IOException {
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

    public void saveMealSuggest(String updatedMealName, String updatedCalories, String updatedProtein, String updatedFat) throws Exception {
        try {
            double calories = Double.parseDouble(updatedCalories);
            double protein = Double.parseDouble(updatedProtein);
            double fat = Double.parseDouble(updatedFat);

            mealSuggestion updatedSuggestion = new mealSuggestion(updatedMealName, calories, protein, fat);

            List<mealSuggestion> mealSuggestions = mealSuggestDao.getAll();
            if (!mealSuggestions.isEmpty()) {
                mealSuggestion existingSuggestion = mealSuggestions.get(0);
                mealSuggestDao.update(existingSuggestion, updatedSuggestion);
            } else {
                mealSuggestDao.add(updatedSuggestion);
            }


        } catch (NumberFormatException ex) {
            throw new Exception("Please enter valid numbers");
        }
    }

    public void addMealPlanCtn(VBox addMealPlanCtn) throws IOException {
        List<MealPlan> existingMealPlans = mealPlanDao.getAll();
        if (!existingMealPlans.isEmpty()) {
            showAlert.alert(Alert.AlertType.ERROR, "Only one meal plan is allowed. Please delete the existing plan before adding a new one.");
            return;
        }
        addMealPlanCtn.setVisible(true);
    }

    public void addMealPlan(String mealName, String calCountStr, String proteinStr, String fatStr, String ingredientList) throws Exception {
        if (mealName.isEmpty() || calCountStr.isEmpty() || proteinStr.isEmpty() || fatStr.isEmpty() || ingredientList.isEmpty()) {
            throw new Exception("Please enter all fields");
        }

        try {
            double calCount = Double.parseDouble(calCountStr);
            double protein = Double.parseDouble(proteinStr);
            double fat = Double.parseDouble(fatStr);

            List<MealPlan> existingMealPlans = mealPlanDao.getAll();
            for (MealPlan existingPlan : existingMealPlans) {
                if (existingPlan.getFoodName().equalsIgnoreCase(mealName)) {
                    showAlert.alert(Alert.AlertType.ERROR, "A meal plan with this name already exists. Please choose a different name.");
                    return;
                }
            }

            MealPlan mealPlan = new MealPlan(mealName, calCount, protein, fat, ingredientList);
            mealPlanDao.add(mealPlan);
            showAlert.alert(Alert.AlertType.INFORMATION, "Meal Plan added successfully");

        } catch (NumberFormatException ex) {
            throw new Exception("Please enter valid numbers");
        }
    }

    public void displayPlanUI(Label mealPlanName, Label mealPlanCalories, Label mealPlanProtein, Label mealPlanFat, Label mealPlanIngridients) throws IOException {
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

    public void deletePlan() throws IOException {
        List<MealPlan> existingMealPlans = mealPlanDao.getAll();
        if (existingMealPlans.isEmpty()) {
            showAlert.alert(Alert.AlertType.ERROR,"No meal can be deleted");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this log?", ButtonType.YES, ButtonType.NO);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    MealPlan mealDelete = existingMealPlans.get(0);
                    mealPlanDao.delete(mealDelete);
                    showAlert.alert(Alert.AlertType.INFORMATION, "Meal deleted successfully");

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }else{
                showAlert.alert(Alert.AlertType.INFORMATION, "delete action cancelled");
            }
        });

    }
}
