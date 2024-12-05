package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.DAO.goalDao.goalDaoImp;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class exercisesControllerForm implements Initializable {

    @FXML
    private Button addGoalBtn,seeAllBtn1,seeAllBtn2;

    @FXML
    private Button editBtnPlan1, deleteBtnPlan1, editBtnPlan2, deleteBtnPlan2, editBtnPlan3, deleteBtnPlan3;

    @FXML
    private Button addPlanBtn, clearBtn;

    @FXML
    private Label dailyGoalType,value,unit,dailyGoalType2,value2,unit2,dailyGoalType3,value3,unit3;

    @FXML
    private Label weeklyGoalType1,weeklyValue1,weeklyUnit1,weeklyGoalType2,weeklyValue2,weeklyUnit2,weeklyGoalType3,weeklyValue3,weeklyUnit3;

    @FXML
    private Label logType1,logType2,logType3,frequency1,frequency2,frequency3,noPlanMsg1,noPlanMsg2,noPlanMsg3;

    @FXML
    private TextField calBurnedLog1,calBurnedLog2,calBurnedLog3,date1,date2,date3,durationFld,durationFld2,durationFld3;

    @FXML
    private ComboBox<String> timeValue;

    @FXML
    private TextField workoutTypeField, durationField, workoutFrequencyField, calBurnedFld;

    @FXML
    private DatePicker dateFld;

    private final String[] time = {"Minutes", "Hours", "Seconds"};

    private goalDaoImp goalDao = new goalDaoImp();


    private final Image defaultImg = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/workoutDefault.jpg"));
    private final Image updateImg = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/updateIcon.png"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeValue.getItems().addAll(time);
        handleTransition(addGoalBtn, "Add Goal", 130, 35);

        displayGoalUI("daily");
        displayGoalUI("weekly");
    }

    public void goalActionButton(ActionEvent e) throws IOException {
        Button sourceBtn = (Button) e.getSource();

        if(sourceBtn == addGoalBtn) {
            openAddFGoalPage("goal-view.fxml");
        }else if(sourceBtn == seeAllBtn1){
            openViewAllPage("allGoals-view.fxml", "daily");
        }else if(sourceBtn == seeAllBtn2){
            openViewAllPage("allGoals-view.fxml","weekly");
        }

    }

    private void displayGoalUI(String period){
        try{
            List<fitnessGoal> goalLists = goalDao.getGoalList(period);

            for(int i = 0; i < Math.min(goalLists.size(), 3); i++){
                fitnessGoal goal = goalLists.get(i);

                if(period.equals("daily")){

                    switch(i){
                        case 0:
                            goalUI(dailyGoalType,value,unit,goal);
                            break;
                        case 1:
                            goalUI(dailyGoalType2,value2,unit2,goal);
                            break;
                        case 2:
                            goalUI(dailyGoalType3,value3,unit3,goal);
                            break;

                    }

                }else if(period.equals("weekly")){
                    switch(i){
                        case 0:
                            goalUI(weeklyGoalType1,weeklyValue1,weeklyUnit1,goal);
                            break;
                        case 1:
                            goalUI(weeklyGoalType2,weeklyValue2,weeklyUnit2,goal);
                            break;
                        case 2:
                            goalUI(weeklyGoalType3,weeklyValue3,weeklyUnit3,goal);
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


        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private void goalUI(Label typeLabel, Label valueLabel, Label unitLabel, fitnessGoal goal){
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

    }

    private void openAddFGoalPage(String fileName) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/" + fileName));
        Scene Goalscene = new Scene(loader.load());
        Stage goalWindow = new Stage();
        goalWindow.setScene(Goalscene);
        goalWindow.showAndWait();
    }

    private void openViewAllPage(String fileName, String period) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/" + fileName));
        Scene Goalscene = new Scene(loader.load());

        viewAllGoalsController controller = loader.getController();
        if (controller != null) {
            controller.setPeriod(period);
            controller.loadGoals();
        } else {
            System.out.println("Controller is null.");
        }

        Stage goalWindow = new Stage();
        goalWindow.setScene(Goalscene);
        goalWindow.showAndWait();
    }

}
