package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.WorkoutMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Workouts;
import com.example.projectpartbprogram_group39.Models.fitnessGoal;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class displayLogControllerForm implements Initializable {

    @FXML
    private Button searchBtn;

    @FXML TextField searchBar;

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

    private String[] timeContainer = {"Hours", "Minutes", "Seconds"};
    private final ObservableList<Workouts> workoutList = FXCollections.observableArrayList();
    DaoInterface<Workouts> workoutDao = new DaoImplement<>("workoutLog.txt", new WorkoutMapper());

    Workouts selectedLog;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updatedTime.getItems().addAll(timeContainer);
        controlEditable(false);

        workoutTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        calBurnedCol.setCellValueFactory(new PropertyValueFactory<>("caloriesBurned"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        frequencyCol.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        workoutDateCol.setCellValueFactory(new PropertyValueFactory<>("beginDate"));

        workoutsTable.setOnMouseClicked(event -> {
            selectedLog();
            controlEditable(true);
        });
    }

    public void loadLogs() throws IOException {

        List<Workouts> workouts = workoutDao.getAll();
        workoutList.clear();
        workoutList.addAll(workouts);
        workoutsTable.setItems(workoutList);

    }

    public void selectedLog() {
        selectedLog = workoutsTable.getSelectionModel().getSelectedItem();
        int num = workoutsTable.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        if (selectedLog == null) {
            return;
        }

        updatedType.setText(selectedLog.getType());

        String calBurnedComplete = selectedLog.getCaloriesBurned();
        String calBurnedValue = calBurnedComplete.substring(0, calBurnedComplete.length() - 4);
        updatedCalBurned.setText(calBurnedValue);

        updatedDuration.setText(String.valueOf(selectedLog.getValue()));
        updatedTime.setValue(selectedLog.getDuration());
        updatedFreq.setText(String.valueOf(selectedLog.getFrequency()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(selectedLog.getBeginDate());
            java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            updatedDate.setValue(localDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

            String url = selectedLog.getImgUrl();
            Image image = new Image(url, 222, 173, false, true);
            workoutImg.setImage(image);

    }

    public void importNewImg(ActionEvent e) {
        FileChooser file = new FileChooser();
        file.setTitle("Choose Image file");
        file.getExtensionFilters().add(new
                FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = file.showOpenDialog(((Button) e.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            Image newImportedImg = new Image(selectedFile.toURI().toString(), 222, 173, false, true);
            workoutImg.setImage(newImportedImg);

        }
    }

    public void editLogs(ActionEvent e) {
        Workouts selectedLog = workoutsTable.getSelectionModel().getSelectedItem();

        if (selectedLog == null) {
            showAlert.alert(Alert.AlertType.ERROR, "No workout log selected. Please select a log to edit.");
            return;
        }

        String updatedType = this.updatedType.getText();
        String updatedCalBurnedStr = this.updatedCalBurned.getText();
        String updatedDurationStr = this.updatedDuration.getText();
        String updatedTime = this.updatedTime.getValue();
        String updatedFreqStr = this.updatedFreq.getText();
        LocalDate updatedDate = this.updatedDate.getValue();
        String url = workoutImg.getImage().getUrl();

        if (url == null || url.isEmpty()) {
            showAlert.alert(Alert.AlertType.WARNING, "Please uploaded an images.");
            return;
        }

        if (updatedType.isEmpty() || updatedCalBurnedStr.isEmpty() || updatedDurationStr.isEmpty() ||
                updatedTime == null || updatedFreqStr.isEmpty() || updatedDate == null) {
            showAlert.alert(Alert.AlertType.ERROR, "All fields must be filled in.");
            return;
        }

        try {
            String formattedCalBurned = updatedCalBurnedStr + " kcal";
            double newDuration = Double.parseDouble(updatedDurationStr);
            int newFreq = Integer.parseInt(updatedFreqStr);
            String newUpdatedDate = updatedDate.toString();

            Workouts updatedLog = new Workouts(updatedType, formattedCalBurned, newDuration, updatedTime, newFreq, newUpdatedDate, url);
            showAlert.alert(Alert.AlertType.INFORMATION, "Log updated successfully");
            workoutDao.update(selectedLog,updatedLog);
            controlEditable(false);
            clear(e);

        } catch (NumberFormatException ex) {
            showAlert.alert(Alert.AlertType.ERROR, "Please enter a valid value");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        try {
            loadLogs();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteLog(ActionEvent e) {
        selectedLog = workoutsTable.getSelectionModel().getSelectedItem();

        if (selectedLog != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this log?", ButtonType.YES, ButtonType.NO);
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        workoutDao.delete(selectedLog);
                        workoutList.remove(selectedLog);
                        showAlert.alert(Alert.AlertType.INFORMATION, "Workout log deleted successfully.");
                        controlEditable(false);
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

    public void clear(ActionEvent e) {
        updatedType.clear();
        updatedCalBurned.clear();
        updatedDuration.clear();
        updatedTime.getSelectionModel().clearSelection();
        updatedFreq.clear();
        updatedDate.setValue(null);
    }

    public void controlEditable(boolean bool) {
        updatedType.setEditable(bool);
        updatedCalBurned.setEditable(bool);
        updatedDuration.setEditable(bool);
        updatedTime.setEditable(bool);
        updatedFreq.setEditable(bool);
        updatedDate.setEditable(bool);
    }

    public void handleSearch() throws IOException {
        String searchText = searchBar.getText().toLowerCase();
        if (searchText.isEmpty()) {
            loadLogs();
        }else{
            ObservableList<Workouts> filteredWorkouts = FXCollections.observableArrayList();
            for (Workouts workout : workoutList){
                if (workout.getType().toLowerCase().contains(searchText)) {
                    filteredWorkouts.add(workout);
                }
                workoutsTable.setItems(filteredWorkouts);
            }
            searchBar.clear();

        }
    }
}
