package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Controllers.Service.editGoalController;
import com.example.projectpartbprogram_group39.DAO.goalDao.goalDaoImp;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.util.List;

public class viewAllGoalsController {
    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchField;

    @FXML
    private Label goalLabel;

    @FXML
    private TableView<fitnessGoal> goalTable;

    @FXML
    private TableColumn<fitnessGoal, String> typeCol;

    @FXML
    private TableColumn<fitnessGoal, Double> targetCol;

    @FXML
    private TableColumn<fitnessGoal, String> unitCol;

    @FXML
    private TableColumn<fitnessGoal, String> startDateCol;

    @FXML
    private TableColumn<fitnessGoal, String> priorityCol;

    @FXML
    private TableColumn<fitnessGoal, String> actionCol;

    private final ObservableList<fitnessGoal> goalsList = FXCollections.observableArrayList();
    private final goalDaoImp goalDao = new goalDaoImp();
    private String period;

    public void setPeriod(String period) {
        this.period = period;

        if (period.equalsIgnoreCase("daily")) {
            goalLabel.setText("Daily Goal");
        } else {
            goalLabel.setText("Weekly Goal");
        }

    }

    @FXML
    public void initialize() throws IOException {

        typeCol.setCellValueFactory(new PropertyValueFactory<>("goalType"));
        targetCol.setCellValueFactory(new PropertyValueFactory<>("goalValue"));
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        priorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));
        actionCol.setCellFactory(new Callback<>() {

            @Override
            public TableCell<fitnessGoal, String> call(TableColumn<fitnessGoal, String> param) {
                return new TableCell<>() {


                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(5);

                            Image editImg = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/editImageIcon.jpg"));
                            ImageView editImageView = new ImageView(editImg);
                            editImageView.setFitHeight(20);
                            editImageView.setFitWidth(20);


                            Image deleteImg = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/deleteIcon.png"));
                            ImageView deleteImageView = new ImageView(deleteImg);
                            deleteImageView.setFitHeight(20);
                            deleteImageView.setFitWidth(20);

                            Button editButton = new Button();
                            editButton.setStyle("-fx-pref-width: 35px; " +
                                    "-fx-background-color: white; " +
                                    "-fx-border-color: black; " +
                                    "-fx-border-radius: 50px; " +
                                    "-fx-background-radius: 50px;" +
                                    "-fx-cursor:hand;"
                            );

                            editButton.setOnAction(e ->{
                                fitnessGoal selectedGoal = getTableRow().getItem();
                                if (selectedGoal != null) {
                                    try {
                                        editGoals(selectedGoal);
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                }
                            });

                            editButton.setGraphic(editImageView);

                            Button deleteButton = new Button();
                            deleteButton.setStyle("-fx-pref-width: 35px; " +
                                    "-fx-background-color: white; " +
                                    "-fx-border-color: black; " +
                                    "-fx-border-radius: 50px; " +
                                    "-fx-background-radius: 50px;" +
                                    "-fx-cursor:hand;"
                            );

                            deleteButton.setOnAction(e ->{
                                fitnessGoal chosenGoal = getTableRow().getItem();
                                deleteAction(chosenGoal);
                            });

                            deleteButton.setGraphic(deleteImageView);

                            hbox.getChildren().addAll(editButton, deleteButton);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });

    }

    public void loadGoals() throws IOException {
        if (period != null) {
            List<fitnessGoal> goals = goalDao.getGoalList(period);
            goalsList.clear();
            goalsList.addAll(goals);
            goalTable.setItems(goalsList);
        }
    }

    public void editGoals(fitnessGoal selectedGoal) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/editGoal-view.fxml"));
        Scene editScene = new Scene(loader.load());

        editGoalController controller = loader.getController();
        controller.setEditable(selectedGoal);

        Stage editWindow = new Stage();
        editWindow.setScene(editScene);
        editWindow.showAndWait();

        loadGoals();
    }

    public void deleteAction(fitnessGoal selectedGoal){
        if (selectedGoal != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this goal?",ButtonType.YES,ButtonType.NO);
            confirmAlert.setTitle("Delete Confirmation");

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        goalDao.deleteGoal(selectedGoal);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    goalsList.remove(selectedGoal);
                    goalTable.refresh();
                }else{
                    showAlert.alert(Alert.AlertType.INFORMATION, "Cancel deletion");
                }
            });
        }

    }


}