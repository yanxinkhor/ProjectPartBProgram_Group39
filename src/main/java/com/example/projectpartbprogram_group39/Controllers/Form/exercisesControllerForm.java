package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.DAO.goalDao.goalDaoImp;
import com.example.projectpartbprogram_group39.DAO.workoutDao.workoutsDaoImp;
import com.example.projectpartbprogram_group39.Models.Workouts;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class exercisesControllerForm implements Initializable {

    @FXML
    private Button addGoalBtn, seeAllBtn1, seeAllBtn2;

    @FXML
    private Button editBtnPlan1, deleteBtnPlan1, editBtnPlan2, deleteBtnPlan2, editBtnPlan3, deleteBtnPlan3;

    @FXML
    private Button addPlanBtn, clearBtn, viewAllPlanBtn;

    @FXML
    private Label dailyGoalType, value, unit, dailyGoalType2, value2, unit2, dailyGoalType3, value3, unit3;

    @FXML
    private Label weeklyGoalType1, weeklyValue1, weeklyUnit1, weeklyGoalType2, weeklyValue2, weeklyUnit2, weeklyGoalType3, weeklyValue3, weeklyUnit3;

    @FXML
    private Label calBurnedLbl1,startDateLbl1,startDateLbl2,calBurnedLbl2,startDateLbl3,calBurnedLbl3;

    @FXML
    private Label logType1, logType2, logType3, frequency1, frequency2, frequency3, noPlanMsg1, noPlanMsg2, noPlanMsg3;

    @FXML
    private TextField calBurnedLog1, calBurnedLog2, calBurnedLog3, date1, date2, date3, durationFld, durationFld2, durationFld3;

    @FXML
    private ComboBox<String> timeValue;

    @FXML
    private TextField workoutTypeField, durationField, workoutFrequencyField, calBurnedFld;

    @FXML
    private DatePicker dateFld;

    @FXML
    private ImageView workoutPlanImg,workoutImg1,workoutImg2,workoutImg3;

    private final String[] time = {"Minutes", "Hours", "Seconds"};

    private goalDaoImp goalDao = new goalDaoImp();
    private workoutsDaoImp workoutDao = new workoutsDaoImp();


    private final Image defaultImg = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/workoutDefault.jpg"));
    private final Image updateImg = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/updateIcon.png"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeValue.getItems().addAll(time);
        handleTransition(addGoalBtn, "Add Goal", 130, 35);
        refreshPage();
    }

    public void goalActionButton(ActionEvent e) throws IOException {
        Button sourceBtn = (Button) e.getSource();

        if (sourceBtn == addGoalBtn) {
            openAddGoalPage();
            refreshPage();
        } else if (sourceBtn == seeAllBtn1) {
            openViewAllPage("displayGoals-view.fxml", "daily");
        } else if (sourceBtn == seeAllBtn2) {
            openViewAllPage("displayGoals-view.fxml", "weekly");
        }
    }

    public void logActionButton(ActionEvent e) throws IOException {
        Button sourceBtn = (Button) e.getSource();

        if (sourceBtn == viewAllPlanBtn) {
            openViewLogPage();
        }
    }
    private String url ="/com/example/projectpartbprogram_group39/Images/workoutDefault.jpg";
    public void importImage(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(((Button) e.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            try {
                Image importedImage = new Image(selectedFile.toURI().toString(),222,173,false,true);
                workoutPlanImg.setImage(importedImage);
                url = selectedFile.getAbsolutePath();


            } catch (Exception ex) {
                showAlert.alert(Alert.AlertType.ERROR, "Failed to load the image. Please try again.");
            }
        } else {
            workoutPlanImg.setImage(defaultImg);
        }


    }

    public void addLogs(ActionEvent e) throws IOException {
        String workoutType = workoutTypeField.getText();
        String caloriesLogStr = calBurnedFld.getText();
        String durationStr = durationField.getText();
        String timeStr = timeValue.getValue();
        String frequencyStr = workoutFrequencyField.getText();
        LocalDate startDate = dateFld.getValue();
        String urlImg = url;

        if (workoutType.isEmpty() || caloriesLogStr.isEmpty() || durationStr.isEmpty() || timeStr == null
                || frequencyStr.isEmpty() || dateFld == null || startDate == null) {
            showAlert.alert(Alert.AlertType.ERROR, "Please enter all of the fields");
            return;
        }

        if (workoutDao.logExists(workoutType)) {
            showAlert.alert(Alert.AlertType.ERROR, "Log already exists, please enter a new log");
            clear(e);
            return;
        }

        try {
            String caloriesLog = caloriesLogStr + " kcal";
            int duration = Integer.parseInt(durationStr);
            int frequency = Integer.parseInt(frequencyStr);
            String startDateStr = startDate.toString();

            Workouts newLogs = new Workouts(workoutType, caloriesLog, duration, timeStr, frequency, startDateStr,urlImg);
            workoutDao.addWorkout(newLogs);
            showAlert.alert(Alert.AlertType.INFORMATION, "Log added successfully!");
            clear(e);
        } catch (NumberFormatException ex) {
            showAlert.alert(Alert.AlertType.ERROR, "Invalid Input, value must be a number.");
        }
    }

    private void displayGoalUI(String period) {

        try {
            List<fitnessGoal> goalLists = goalDao.getGoalList(period);

            for (int i = 0; i < Math.min(goalLists.size(), 3); i++) {
                fitnessGoal goal = goalLists.get(i);

                if (period.equals("daily")) {

                    switch (i) {
                        case 0:
                            goalUI(dailyGoalType, value, unit, goal);
                            break;
                        case 1:
                            goalUI(dailyGoalType2, value2, unit2, goal);
                            break;
                        case 2:
                            goalUI(dailyGoalType3, value3, unit3, goal);
                            break;

                    }

                } else if (period.equals("weekly")) {
                    switch (i) {
                        case 0:
                            goalUI(weeklyGoalType1, weeklyValue1, weeklyUnit1, goal);
                            break;
                        case 1:
                            goalUI(weeklyGoalType2, weeklyValue2, weeklyUnit2, goal);
                            break;
                        case 2:
                            goalUI(weeklyGoalType3, weeklyValue3, weeklyUnit3, goal);
                            break;
                    }
                }
            }

            if (goalLists.isEmpty()) {
                if (period.equals("daily")) {
                    clearDailyGoalsUI();

                } else if (period.equals("weekly")) {
                    clearWeeklyGoalsUI();

                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void goalUI(Label typeLabel, Label valueLabel, Label unitLabel, fitnessGoal goal) {
        typeLabel.setText(goal.getGoalType());
        valueLabel.setText(String.valueOf(goal.getGoalValue()));
        unitLabel.setText(goal.getUnit());
    }

    private void clearDailyGoalsUI() {
        dailyGoalType.setText("No Goal");
        value.setText("");
        unit.setText("");

        dailyGoalType2.setText("No Goal");
        value2.setText("");
        unit2.setText("");

        dailyGoalType3.setText("No Goal");
        value3.setText("");
        unit3.setText("");
    }

    private void clearWeeklyGoalsUI() {
        weeklyGoalType1.setText("No Goal");
        weeklyValue1.setText("");
        weeklyUnit1.setText("");

        weeklyGoalType2.setText("No Goal");
        weeklyValue2.setText("");
        weeklyUnit2.setText("");

        weeklyGoalType3.setText("No Goal");
        weeklyValue3.setText("");
        weeklyUnit3.setText("");
    }

    public void refreshPage() {
        displayGoalUI("daily");
        displayGoalUI("weekly");
        displayLogUI();
    }

    public void displayLogUI() {
        try {
            List<Workouts> workoutLists = workoutDao.getAllWorkouts();
            if (workoutLists == null || workoutLists.isEmpty()) {
                displayNoPlanMessages();
                return;
            }

            ImageView[] workoutImgs = {workoutImg1, workoutImg2, workoutImg3};
            Label[] logTypes = {logType1, logType2, logType3};
            Label[] frequencies = {frequency1, frequency2, frequency3};
            TextField[] calBurnedLogs = {calBurnedLog1, calBurnedLog2, calBurnedLog3};
            TextField[] dates = {date1, date2, date3};
            TextField[] durations = {durationFld, durationFld2, durationFld3};
            Label[] noPlanMsgs = {noPlanMsg1, noPlanMsg2, noPlanMsg3};
            Label[] calBurnedLbls = {calBurnedLbl1, calBurnedLbl2, calBurnedLbl3};
            Label[] startDateLbls = {startDateLbl1, startDateLbl2, startDateLbl3};

            for (int i = 0; i < Math.min(workoutLists.size(), 3); i++) {
                Workouts workout = workoutLists.get(i);

                noPlanMsgs[i].setText("");
                calBurnedLbls[i].setVisible(true);
                startDateLbls[i].setVisible(true);

                logUI(workoutImgs[i], logTypes[i], frequencies[i], calBurnedLogs[i], dates[i], durations[i], workout);
            }

            for (int i = workoutLists.size(); i < 3; i++) {
                noPlanMsgs[i].setText("No plan available");
                calBurnedLbls[i].setVisible(false);
                startDateLbls[i].setVisible(false);
            }
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    private void logUI(ImageView workoutImg, Label logType, Label frequency, TextField calBurnedLog, TextField date, TextField duration, Workouts workout) {
        logType.setText(workout.getType());
        frequency.setText(workout.getFrequency() + " times per week");
        calBurnedLog.setText(String.valueOf(workout.getCaloriesBurned()));
        date.setText(workout.getBeginDate());
        duration.setText(workout.getValue() + " " + workout.getDuration());

        if(workout.getImgUrl().equals(url)){
            workoutImg.setImage(defaultImg);
        }else{
            workoutImg.setImage(new Image(workout.getImgUrl()));
        }


    }

    private void displayNoPlanMessages() {
        noPlanMsg1.setText("No plan available");
        noPlanMsg2.setText("No plan available");
        noPlanMsg3.setText("No plan available");

        calBurnedLbl1.setVisible(false);
        calBurnedLbl2.setVisible(false);
        calBurnedLbl3.setVisible(false);

        startDateLbl1.setVisible(false);
        startDateLbl2.setVisible(false);
        startDateLbl3.setVisible(false);
    }
    public void handleTransition(Button button, String text, double width, double originWidth) {

        button.setOnMouseEntered(event -> {
            button.setText(text);
            Timeline expand = new Timeline(
                    new KeyFrame(Duration.millis(150),
                            new KeyValue(button.prefWidthProperty(), width)
                    )
            );
            expand.play();
        });

        button.setOnMouseExited(event -> {
            Timeline origin = new Timeline(
                    new KeyFrame(Duration.millis(300),
                            new KeyValue(button.prefWidthProperty(), originWidth)
                    )
            );
            origin.setOnFinished(e -> button.setText(""));
            origin.play();
        });
    }

    public void clear(ActionEvent event) {
        workoutTypeField.clear();
        durationField.clear();
        workoutFrequencyField.clear();
        timeValue.getSelectionModel().clearSelection();
        calBurnedFld.clear();
        dateFld.setValue(null);
        workoutPlanImg.setImage(null);

    }

    private void openAddGoalPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/goal-view.fxml"));
        Scene Goalscene = new Scene(loader.load());
        Stage goalWindow = new Stage();
        goalWindow.setScene(Goalscene);
        goalWindow.setResizable(false);
        goalWindow.showAndWait();
    }

    private void openViewAllPage(String fileName, String period) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/" + fileName));
        Scene Goalscene = new Scene(loader.load());

        viewAllGoalsController controller = loader.getController();
        controller.setPeriod(period);
        controller.loadGoals();

        Stage goalWindow = new Stage();
        goalWindow.setScene(Goalscene);
        goalWindow.showAndWait();
    }

    private void openViewLogPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/displayLog-view.fxml"));
        Scene LogScene = new Scene(loader.load());

        displayLogControllerForm logController = loader.getController();
        logController.loadLogs();
        Stage logWindow = new Stage();
        logWindow.setScene(LogScene);
        logWindow.showAndWait();
    }

}
