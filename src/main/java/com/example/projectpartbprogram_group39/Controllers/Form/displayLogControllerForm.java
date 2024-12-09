package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.DAO.workoutDao.workoutsDaoImp;
import com.example.projectpartbprogram_group39.Models.Workouts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class displayLogControllerForm implements Initializable {
    @FXML
    private TableView<Workouts> workoutsTable;

    @FXML
    private TableColumn<Workouts, String> workoutTypeCol;

    @FXML
    private TableColumn<Workouts, String> calBurnedCol;

    @FXML
    private TableColumn<Workouts, String> durationCol;

    @FXML
    private TableColumn<Workouts, String> timeCol;

    @FXML
    private TableColumn<Workouts, String> frequencyCol;

    @FXML
    private TableColumn<Workouts, String> workoutDateCol;

    @FXML
    private TextField updatedType, updatedCalBurned, updatedDuration, updatedFreq;

    @FXML
    private ComboBox<String> updatedTime;

    @FXML
    private DatePicker updatedDate;

    @FXML
    private ImageView workoutImg;

    private final ObservableList<Workouts> workoutList = FXCollections.observableArrayList();
    private final Image defaultImg = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/workoutDefault.jpg"));
    private workoutsDaoImp workoutsDao = new workoutsDaoImp();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        workoutTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        calBurnedCol.setCellValueFactory(new PropertyValueFactory<>("caloriesBurned"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        frequencyCol.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        workoutDateCol.setCellValueFactory(new PropertyValueFactory<>("beginDate"));

        workoutsTable.setOnMouseClicked(event -> {
            selectedLog();
        });
    }

    public void loadLogs() throws IOException {

        List<Workouts> workouts = workoutsDao.getAllWorkouts();
        workoutList.clear();
        workoutList.addAll(workouts);
        workoutsTable.setItems(workoutList);

    }

    public void selectedLog(){
        String defaultUrl = "/com/example/projectpartbprogram_group39/Images/workoutDefault.jpg";
        Workouts chosenLog = workoutsTable.getSelectionModel().getSelectedItem();
        int num = workoutsTable.getSelectionModel().getSelectedIndex();

        if((num-1) < -1){
            return;
        }

        if (chosenLog == null) {
            return;
        }

        updatedType.setText(chosenLog.getType());

        String calBurnedComplete = chosenLog.getCaloriesBurned();
        String calBurnedValue = calBurnedComplete.substring(0,calBurnedComplete.length()-5);
        updatedCalBurned.setText(calBurnedValue);

        updatedDuration.setText(String.valueOf(chosenLog.getValue()));
        updatedTime.setValue(chosenLog.getDuration());
        updatedFreq.setText(String.valueOf(chosenLog.getFrequency()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(chosenLog.getBeginDate());
            java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            updatedDate.setValue(localDate);
        }catch(ParseException e){
            e.printStackTrace();
        }

        if(chosenLog.getImgUrl().equals(defaultUrl)){
            workoutImg.setImage(defaultImg);
        }else {
            String url = chosenLog.getImgUrl();
            Image image = new Image(url, 222, 173, false, true);
            workoutImg.setImage(image);
        }
    }

    public void editLogs(){

    }
}
