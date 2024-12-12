package com.example.projectpartbprogram_group39.Controllers.Form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class nutritionControllerForm {

    @FXML
    private Label mealSuggestionName;
    @FXML
    private Label mealSuggestionCalories;
    @FXML
    private Label mealSuggestionFat;
    @FXML
    private Label mealSuggestionProtein;
    @FXML
    private ImageView mealSuggestionImage;

    @FXML
    private Label mealPlanName;
    @FXML
    private Label mealPlanCalories;
    @FXML
    private Label mealPlanProtein;
    @FXML
    private Label mealPlanFat;
    @FXML
    private Label mealPlanIngridients;
    @FXML
    private ImageView mealPlanImage;

    @FXML
    private Label dailycalories;
    @FXML
    private Label dailyprotein;
    @FXML
    private Label dailyfat;

    private int totalcalories = 0;
    private int totalprotein = 0;
    private int totalfat = 0;

    //daily nutrition goals
    @FXML
    private Label dailycalories1;
    @FXML
    private Label dailyprotein1;
    @FXML
    private Label dailyfat1;

    private int totalcalories1 = 0;
    private int totalprotein1 = 0;
    private int totalfat1 = 0;

    @FXML
    private VBox addmealVBOX;
    @FXML
    private TextField entermealname;
    @FXML
    private TextField entercalories;
    @FXML
    private TextField enterfat;
    @FXML
    private TextField enterprotein;
    @FXML
    private TextField enteringridients;
    @FXML
    private Button addimagebtn;
    @FXML
    private ImageView displayimage;

    @FXML
    private void showAddMealForm(){
        addmealVBOX.setVisible(true);
    }


    @FXML
    private VBox dailygoalsVBOX;
    @FXML
    private TextField enterCaloriesGoal;
    @FXML
    private TextField enterFatGoal;
    @FXML
    private TextField enterProteinGoal;

    @FXML
    private void showGoalsForm(){
        dailygoalsVBOX.setVisible(true);
    }

    @FXML
    private VBox dailynutritionVBOX;
    @FXML
    private TextField enterDailyCalories;
    @FXML
    private TextField enterDailyProtein;
    @FXML
    private TextField enterDailyFat;
    @FXML
    private void showDailyNutForm(){
        dailynutritionVBOX.setVisible(true);
    }

    @FXML
    private void closevbox(ActionEvent event) {
        dailygoalsVBOX.setVisible(false);
        addmealVBOX.setVisible(false);
        dailynutritionVBOX.setVisible(false);
        clearInputFields();
    }

    @FXML
    private void savedailynutrition(){
        try{
            String dailynutcalories = enterDailyCalories.getText().trim();
            String dailynutprotein = enterDailyProtein.getText().trim();
            String dailynutfat = enterDailyFat.getText().trim();

            if(dailynutcalories.isEmpty() || dailynutprotein.isEmpty() || dailynutfat.isEmpty()){
                showAlert(Alert.AlertType.ERROR, "Please fill all the fields", "Please fill all the fields");
                return;
            }

            int dailynutritioncalories = Integer.parseInt(dailynutcalories);
            int dailynutritionprotein = Integer.parseInt(dailynutprotein);
            int dailynutritionfat = Integer.parseInt(dailynutfat);

            totalcalories += dailynutritioncalories;
            totalprotein += dailynutritionprotein;
            totalfat += dailynutritionfat;

            dailycalories.setText(String.valueOf(totalcalories));
            dailyprotein.setText(String.valueOf(totalprotein));
            dailyfat.setText(String.valueOf(totalfat));

            clearDailyNutrition();

            dailynutritionVBOX.setVisible(false);
        }
        catch (NumberFormatException e){
            showAlert(Alert.AlertType.ERROR, "Input Error", "Calories, Protein, and Fat must be numeric values.");
        }
    }

    private void clearDailyNutrition(){
        enterDailyCalories.clear();
        enterDailyProtein.clear();
        enterDailyFat.clear();
    }

    @FXML
    private void savedailygoals(){
        try{
            String caloriesgoal = enterCaloriesGoal.getText().trim();
            String proteingoal = enterProteinGoal.getText().trim();
            String fatgoal = enterFatGoal.getText().trim();

            if(caloriesgoal.isEmpty() || proteingoal.isEmpty() || fatgoal.isEmpty()){
                showAlert(Alert.AlertType.ERROR, "Please fill all the fields", "Please fill all the fields");
                return;
            }

            int dailycaloriesgoal = Integer.parseInt(caloriesgoal);
            int dailyproteingoal = Integer.parseInt(proteingoal);
            int dailyfatgoal = Integer.parseInt(fatgoal);

            totalcalories1 += dailycaloriesgoal;
            totalprotein1 += dailyproteingoal;
            totalfat1 += dailyfatgoal;

            dailycalories1.setText(String.valueOf(totalcalories1));
            dailyprotein1.setText(String.valueOf(totalprotein1));
            dailyfat1.setText(String.valueOf(totalfat1));

            clearGoalsInput();

            dailygoalsVBOX.setVisible(false);
        }
        catch (NumberFormatException e){
            showAlert(Alert.AlertType.ERROR, "Input Error", "Calories, Protein, and Fat must be numeric values.");
        }
    }

    private void clearGoalsInput(){
        enterCaloriesGoal.clear();
        enterProteinGoal.clear();
        enterFatGoal.clear();
    }

    @FXML
    private void AddImagetoMealPlan(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*.png", "*.jpg" ));
        File file = fileChooser.showOpenDialog(mealPlanImage.getScene().getWindow());

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            displayimage.setImage(image);
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Image File Not Found", "Image File Not Found");
        }
    }




    @FXML
    private void AddMealPlan(ActionEvent event){
        try {
            // Retrieve inputs
            String name = entermealname.getText().trim();
            String calories = entercalories.getText().trim();
            String protein = enterprotein.getText().trim();
            String fat = enterfat.getText().trim();
            String ingridients = enteringridients.getText().trim();

            // Validate inputs
            if (name.isEmpty() || calories.isEmpty() || protein.isEmpty() || fat.isEmpty() || ingridients.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill in all the fields.");
                return;
            }

            int caloriesValue, proteinValue, fatValue;

            // Validate numeric fields
            try {
                caloriesValue = Integer.parseInt(calories);
                proteinValue = Integer.parseInt(protein);
                fatValue = Integer.parseInt(fat);
            }
            catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Calories, Protein, and Fat must be numeric values.");
                return;
            }

            // Update the Meal Plan details in the UI
            mealPlanName.setText(name);
            mealPlanCalories.setText(caloriesValue + " kcal");
            mealPlanProtein.setText(proteinValue + " grams");
            mealPlanFat.setText(fatValue + " grams");
            mealPlanIngridients.setText(ingridients);

            Image image = displayimage.getImage();
            if (image != null) {
                mealPlanImage.setImage(image);
            }
            else{
                showAlert(Alert.AlertType.ERROR, "Image File Not Found", "Image File Not Found");
            }

            // Clear input fields after updating
            clearInputFields();

            totalcalories += caloriesValue;
            totalprotein += proteinValue;
            totalfat += fatValue;

            dailycalories.setText(String.valueOf(totalcalories));
            dailyfat.setText(String.valueOf(totalfat));
            dailyprotein.setText(String.valueOf(totalprotein));

            // Optional success alert
            showAlert(Alert.AlertType.INFORMATION, "Success", "Meal plan updated successfully!");

            addmealVBOX.setVisible(false);

        }
        catch (Exception e) {
            // General exception handling for unexpected errors
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    // Utility method to clear input fields
    private void clearInputFields() {
        entermealname.clear();
        entercalories.clear();
        enterprotein.clear();
        enterfat.clear();
        enteringridients.clear();
        displayimage.setImage(null);
    }

    // Button to transfer data
    @FXML
    private Button AddtoMealPlanBtn1;



    @FXML
    private void handleAddToMealPlan() {
        // Transfer data from Meal Suggestion to Meal Plan
        String mealName = mealSuggestionName.getText();
        String mealCalories = mealSuggestionCalories.getText();
        String mealFats = mealSuggestionFat.getText();
        String mealProteins = mealSuggestionProtein.getText();
        Image mealImage = mealSuggestionImage.getImage();


        // Set the Meal Plan section values
        mealPlanName.setText(mealName);
        mealPlanCalories.setText(mealCalories);
        mealPlanProtein.setText(mealProteins);
        mealPlanFat.setText(mealFats);
        mealPlanImage.setImage(mealImage);

        mealPlanIngridients.setText("Avocado, Tortilla Wrap, Lettuce, Olive Oil");

        totalcalories += parseNumericValue(mealCalories);
        totalfat += parseNumericValue(mealFats);
        totalprotein += parseNumericValue(mealProteins);

        dailycalories.setText(String.valueOf(totalcalories));
        dailyfat.setText(String.valueOf(totalfat));
        dailyprotein.setText(String.valueOf(totalprotein));
    }

    private int parseNumericValue(String text) {
        String numericPart = text.replaceAll("[^0-9]", "");
        return numericPart.isEmpty() ? 0 : Integer.parseInt(numericPart);
    }


}

