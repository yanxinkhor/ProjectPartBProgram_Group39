package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.MealMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Meal;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.IOException;

import java.util.List;

public class viewMealControllerForm {

    @FXML
    private TableView<Meal> foodTable;

    @FXML
    private TableColumn<Meal,String> foodCol;

    @FXML
    private TableColumn<Meal,String> calCol;

    @FXML
    private TableColumn<Meal,String> ProteinCol;

    @FXML
    private TableColumn<Meal,String> fatCol;

    @FXML
    private TextField foodField,calField,proteinField,fatField;

    private final ObservableList<Meal> mealList = FXCollections.observableArrayList();
    private static DaoInterface<Meal> mealDao = new DaoImplement<>("meal.txt",new MealMapper());


    public void initialize() throws IOException {
        foodCol.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        calCol.setCellValueFactory(new PropertyValueFactory<>("calories"));
        ProteinCol.setCellValueFactory(new PropertyValueFactory<>("protein"));
        fatCol.setCellValueFactory(new PropertyValueFactory<>("fat"));

        foodTable.setOnMouseClicked(event -> {
            selectedFood();
        });

        loadFood();
    }

    public void loadFood() throws IOException{
        List<Meal> meals = mealDao.getAll();
        mealList.clear();
        mealList.addAll(meals);
        foodTable.setItems(mealList);

    }

    @FXML
    private void selectedFood(){
        Meal selectedMeal = foodTable.getSelectionModel().getSelectedItem();
        int num = foodTable.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        if (selectedMeal == null) {
            return;
        }

        foodField.setText(selectedMeal.getFoodName());
        calField.setText(String.valueOf(selectedMeal.getCalories()));
        proteinField.setText(String.valueOf(selectedMeal.getProtein()));
        fatField.setText(String.valueOf(selectedMeal.getFat()));
    }


    @FXML
    private void addFood(ActionEvent e){
        String food = foodField.getText();
        String caloryStr = calField.getText();
        String proteinStr = proteinField.getText();
        String fatStr = fatField.getText();

        if(food.isEmpty() || caloryStr.isEmpty() || proteinStr.isEmpty() || fatStr.isEmpty()){
            showAlert.alert(Alert.AlertType.ERROR, "Please enter all the field");
            return;
        }


        try{
            double calory = Double.parseDouble(caloryStr);
            double protein = Double.parseDouble(proteinStr);
            double fat = Double.parseDouble(fatStr);

            if(calory < 0 || protein < 0 || fat < 0){
                throw new IllegalArgumentException("The nutrition cannot less than zero");
            }

            Meal meal = new Meal(food,calory,protein,fat);
            mealDao.add(meal);
            showAlert.alert(Alert.AlertType.INFORMATION,"Food added successful");
            loadFood();
            clear(e);

        }catch(NumberFormatException ex){
            showAlert.alert(Alert.AlertType.ERROR,"Please enter a valid value");

        } catch(IllegalArgumentException ex){
            showAlert.alert(Alert.AlertType.ERROR,ex.getMessage());
        }catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    @FXML
    private void updateFood(ActionEvent e){
        Meal selectedMeal = foodTable.getSelectionModel().getSelectedItem();

        if(selectedMeal == null){
            showAlert.alert(Alert.AlertType.ERROR,"Please select a meal to update");
            return;
        }

        String updateFood = foodField.getText();
        String updateCalStr = calField.getText();
        String updateProStr = proteinField.getText();
        String updateFatStr = fatField.getText();

        if(updateFood.isEmpty() || updateCalStr.isEmpty() || updateProStr.isEmpty() || updateFatStr.isEmpty()){
            showAlert.alert(Alert.AlertType.ERROR,"The field cannot be empty");
        }
        try{
            double updateCal = Double.parseDouble(updateCalStr);
            double updatePro = Double.parseDouble(updateProStr);
            double updateFat = Double.parseDouble(updateFatStr);

            if(updateCal < 0 || updatePro < 0 || updateFat < 0){
                throw new IllegalArgumentException("The nutrition cannot less than zero");
            }


            Meal newMeal = new Meal(updateFood,updateCal,updatePro,updateFat);
            mealDao.update(selectedMeal,newMeal);
            showAlert.alert(Alert.AlertType.INFORMATION,"Food updated successful");
            loadFood();


        }catch(NumberFormatException ex){
            showAlert.alert(Alert.AlertType.ERROR,"Please enter a valid number");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }catch(IllegalArgumentException ex){
            showAlert.alert(Alert.AlertType.ERROR,ex.getMessage());
        }
    }

    @FXML
    private void deleteFood(ActionEvent e){
        Meal selectedMeal = foodTable.getSelectionModel().getSelectedItem();

        if (selectedMeal != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this food?", ButtonType.YES, ButtonType.NO);
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        mealDao.delete(selectedMeal);
                        mealList.remove(selectedMeal);
                        showAlert.alert(Alert.AlertType.INFORMATION, "Food deleted successfully.");
                        clear(e);

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        } else {
            showAlert.alert(Alert.AlertType.ERROR, "Please select a log to delete");
        }
    }

    @FXML
    private void clear(ActionEvent e){
        foodField.clear();
        calField.clear();
        proteinField.clear();
        fatField.clear();
    }

}
