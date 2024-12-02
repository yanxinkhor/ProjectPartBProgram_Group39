package com.example.projectpartbprogram_group39.Controllers.Form;

import javafx.animation.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class exercisesController implements Initializable {

    @FXML
    private Button addGoalBtn, editBtn, deleteBtn;

    @FXML
    private Button editBtnPlan1, deleteBtnPlan1, editBtnPlan2, deleteBtnPlan2, editBtnPlan3, deleteBtnPlan3;

    @FXML
    private Button addPlanBtn, clearBtn;

    @FXML
    private Label dailyworkoutType, value, unit, dailyworkoutType2, value2, unit2, dailyworkoutType3, value3, unit3;

    @FXML
    private Label weeklyWorkType1, weeklyValue1, weeklyUnit1, weeklyWorkType2, weeklyValue2,
            weeklyUnit2, weeklyWorkType3, weeklyValue3, weeklyUnit3;

    @FXML
    private Label planType1,frequency1,timesPerweekLbl1,startDateLbl1,priorityLbl1,noPlanMsg1,
            planType2,frequency2,timesPerweekLbl2,startDateLbl2,priorityLbl2,noPlanMsg2,
            planType3,frequency3,timesPerweekLbl3,startDateLbl3,priorityLbl3,noPlanMsg3;

    @FXML
    private ImageView workoutPlanImg,workoutImg1,workoutImg2,workoutImg3;

    @FXML
    private ComboBox<String> timeValue, priorityCombo;

    @FXML
    private TextField workoutTypeField, durationField, workoutFrequencyField;

    @FXML
    private TextField startDate1,priority1,startDate2,priority2,startDate3,priority3;

    @FXML
    private DatePicker startDatePicker;

    private final String[] time = {"Minutes", "Hours"};
    private final String[] priority = {"Low", "Medium", "High"};

    private final Image defaultImg = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/workoutDefault.jpg"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeValue.getItems().addAll(time);
        priorityCombo.getItems().addAll(priority);

        handleTransition(addGoalBtn, "Add Goal", 130, 35);
        handleTransition(editBtn, "Edit", 100, 35);
        handleTransition(deleteBtn, "Delete", 100, 35);
    }

    public void goalActionButton(ActionEvent e) throws IOException {
        Button sourceBtn = (Button) e.getSource();

        if(sourceBtn == addGoalBtn){
            openAddGoalPage();

        }else if(sourceBtn == editBtn){

        }
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
        priorityCombo.getSelectionModel().clearSelection();
        startDatePicker.setValue(null);

    }

    private void openAddGoalPage() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/goal-view.fxml"));
        Scene Goalscene = new Scene(loader.load());
        Stage goalWindow = new Stage();
        goalWindow.setScene(Goalscene);

        goalWindow.show();
    }


}
