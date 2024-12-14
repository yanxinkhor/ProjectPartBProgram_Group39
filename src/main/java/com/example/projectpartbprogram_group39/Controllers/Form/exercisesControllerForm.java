package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Controllers.Service.exercisesController;
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
    private Button viewAllLogBtn;

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

    private String url;
    private final String[] time = {"Minutes", "Hours", "Seconds"};
    private final exercisesController exerciseService = new exercisesController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeValue.getItems().addAll(time);
        handleTransition(addGoalBtn, "Add Goal", 130, 35);
        refreshPage();
    }

    public void ActionButton(ActionEvent e) throws IOException {
        Button sourceBtn = (Button) e.getSource();

        if (sourceBtn == addGoalBtn) {
            openPage("goal-view.fxml","viewAllGoalsController","",false);
            refreshPage();
        } else if (sourceBtn == seeAllBtn1) {
            openPage("displayGoals-view.fxml","viewAllGoalsController","daily",false);

        } else if (sourceBtn == seeAllBtn2) {
            openPage("displayGoals-view.fxml","viewAllGoalsController","weekly",false);

        }else if(sourceBtn == viewAllLogBtn){
            openPage("displayLog-view.fxml","displayLogControllerForm","",true);
        }
    }

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
        }
    }

    public void addLogs(ActionEvent e){
        String workoutType = workoutTypeField.getText();
        String caloriesLogStr = calBurnedFld.getText();
        String durationStr = durationField.getText();
        String timeStr = timeValue.getValue();
        String frequencyStr = workoutFrequencyField.getText();
        LocalDate startDate = dateFld.getValue();
        String urlImg = url;

        if (urlImg == null || urlImg.isEmpty()) {
            showAlert.alert(Alert.AlertType.WARNING, "You must import an image before saving the log.");
            return;
        }

        if (workoutType.isEmpty() || caloriesLogStr.isEmpty() || durationStr.isEmpty() || timeStr == null
                || frequencyStr.isEmpty() || dateFld == null || startDate == null) {
            showAlert.alert(Alert.AlertType.ERROR, "Please enter all of the fields");
            return;
        }

        exerciseService.addWorkoutLog(workoutType, caloriesLogStr, durationStr, timeStr, frequencyStr, startDate, urlImg);
        displayLogUI();
        clear(e);
    }

    private void displayGoalUI(String period) {

        try {
            List<fitnessGoal> goalLists = exerciseService.getGoalsByPeriod(period);

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
            List<Workouts> workoutLists = exerciseService.getAllWorkouts();

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
                noPlanMsgs[i].setText("No Workout Log");
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

            workoutImg.setImage(new Image(workout.getImgUrl()));


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

    private void openPage(String fileName, String controllerClassName, String period, boolean isLogPage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/" + fileName));
        Scene scene = new Scene(loader.load());

        Object controller = loader.getController();

        if (controller instanceof viewAllGoalsController) {
            viewAllGoalsController goalController = (viewAllGoalsController) controller;
            goalController.setPeriod(period);
            goalController.loadGoals();
        } else if (controller instanceof displayLogControllerForm) {
            displayLogControllerForm logController = (displayLogControllerForm) controller;
            logController.loadLogs();
        }

        Stage newWindow = new Stage();
        newWindow.setScene(scene);
        newWindow.setResizable(false);
        newWindow.showAndWait();
    }

}
