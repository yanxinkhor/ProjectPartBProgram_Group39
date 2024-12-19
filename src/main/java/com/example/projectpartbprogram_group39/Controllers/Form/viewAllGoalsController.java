package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Controllers.Service.viewAllGoalController;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.FitnessGoalMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
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

    @FXML
    private TextField updatedType, updatedTarget;

    @FXML
    private DatePicker updatedDate;

    @FXML
    private ComboBox<String> updatedPriority, updatedUnit;

    @FXML
    private AnchorPane editGoalCtn;

    private final String[] intensity = {"High", "Medium", "Low"};
    private final String[] units = {"km", "m", "kcal", "steps","g","kg","l","hour","minute","seconds"};

    private final ObservableList<fitnessGoal> goalsList = FXCollections.observableArrayList();
    private viewAllGoalController controller = new viewAllGoalController();
    private fitnessGoal goal;
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
        updatedPriority.getItems().addAll(intensity);
        updatedUnit.getItems().addAll(units);

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
                                    "-fx-cursor:hand;");
                            editButton.setOnAction(e -> {
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
                                    "-fx-cursor:hand;");
                            deleteButton.setOnAction(e -> {
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

        searchBtn.setOnAction(e -> {
            try {
                handleSearch();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void loadGoals() throws IOException {
        goalsList.clear();
        goalsList.addAll(controller.loadGoals(period));
        goalTable.setItems(goalsList);
    }

    public void editGoals(fitnessGoal selectedGoal) throws IOException {
        if (selectedGoal != null) {
            goal = selectedGoal;
            editGoalCtn.setVisible(true);
            updatedType.setText(selectedGoal.getGoalType());
            updatedTarget.setText(String.valueOf(selectedGoal.getGoalValue()));
            updatedUnit.setValue(selectedGoal.getUnit());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(selectedGoal.getStartDate());
                java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                updatedDate.setValue(localDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            updatedPriority.setValue(selectedGoal.getPriority());
        }
    }

    public void back(ActionEvent e) {
        editGoalCtn.setVisible(false);
    }

    public void reservedChanged(ActionEvent e) throws IOException {
        if (goal != null) {
            if (updatedType.getText().isEmpty() || updatedTarget.getText().isEmpty() || updatedUnit.getValue() == null
                    || updatedDate.getValue() == null || updatedPriority.getValue() == null) {
                showAlert.alert(Alert.AlertType.ERROR, "Please fill in all fields.");
                return;
            }

            fitnessGoal original = new fitnessGoal(goal.getGoalType(), goal.getGoalValue(), goal.getUnit(), goal.getTimeFrame(), goal.getStartDate(), goal.getPriority());

            try {
                double parseUpdatedTarget = Double.parseDouble(updatedTarget.getText());
                if (parseUpdatedTarget <= 0) {
                    throw new IllegalArgumentException("Enter value cannot be zero or negative");
                }

                for (fitnessGoal existingGoal : goalsList) {
                    if (!existingGoal.equals(goal) && existingGoal.getGoalType().equalsIgnoreCase(updatedType.getText())) {
                        showAlert.alert(Alert.AlertType.ERROR, "Goal already exists");
                        return;
                    }
                }

                goal.setGoalType(updatedType.getText());
                goal.setGoalValue(parseUpdatedTarget);
                goal.setUnit(updatedUnit.getValue());
                goal.setStartDate(String.valueOf(updatedDate.getValue()));
                goal.setPriority(updatedPriority.getValue());

                controller.updateGoal(original, goal, period);
                showAlert.alert(Alert.AlertType.INFORMATION, "Goal updated successfully");
                editGoalCtn.setVisible(false);
                loadGoals();

            } catch (NumberFormatException ex) {
                showAlert.alert(Alert.AlertType.ERROR, "Please enter a valid target value.");
                updatedTarget.setText("");
            } catch (IllegalArgumentException ex) {
                showAlert.alert(Alert.AlertType.ERROR, ex.getMessage());
            }
        }
    }

    public void clearAll() {
        updatedType.clear();
        updatedTarget.clear();
        updatedUnit.getSelectionModel().clearSelection();
        updatedDate.setValue(null);
        updatedPriority.getSelectionModel().clearSelection();
    }

    public void deleteAction(fitnessGoal selectedGoal) {
        if (selectedGoal != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this goal?", ButtonType.YES, ButtonType.NO);
            confirmAlert.setTitle("Delete Confirmation");

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        controller.deleteGoal(selectedGoal, period);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    goalsList.remove(selectedGoal);
                    goalTable.refresh();
                } else {
                    showAlert.alert(Alert.AlertType.INFORMATION, "Cancel deletion");
                }
            });
        }
    }

    public void handleSearch() throws IOException {
        String searchText = searchField.getText().toLowerCase();
        ObservableList<fitnessGoal> filteredGoals = FXCollections.observableArrayList();
        for (fitnessGoal goal : goalsList) {
            if (goal.getGoalType().toLowerCase().contains(searchText)) {
                filteredGoals.add(goal);
            }
        }
        goalTable.setItems(filteredGoals);

        if (filteredGoals.isEmpty()) {
            showAlert.alert(Alert.AlertType.ERROR, "No matching goals found.");
            loadGoals();
        }

        searchField.clear();
    }

}