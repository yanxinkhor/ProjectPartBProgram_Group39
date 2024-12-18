package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.TraineeMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.TraineeSession;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class settingsController implements Initializable {
    @FXML
    private Pane opacityPane;

    @FXML
    private VBox deleteCtn;

    private static DaoInterface<Trainee> traineeDao = new DaoImplement<>("userInfo.txt", new TraineeMapper());

    public void connectDevicePU(ActionEvent e) {
        opacityPane.setVisible(true);
        openPopup("/com/example/projectpartbprogram_group39/View/connectDevice-view.fxml");

    }

    public void deleteAccountPU(ActionEvent e) {
        opacityPane.setVisible(true);
        deleteCtn.setVisible(true);

    }

    public void sendFeedbackPU(ActionEvent e) {
        opacityPane.setVisible(true);
    }

    private void openPopup(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            loader.load();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);

            popupStage.setScene(new Scene(loader.getRoot()));
            popupStage.setResizable(false);
            popupStage.setOnCloseRequest(event -> opacityPane.setVisible(false));
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelDelete(ActionEvent e){
        deleteCtn.setVisible(false);
        opacityPane.setVisible(false);
        showAlert.alert(Alert.AlertType.ERROR,"You have cancelled the action");
    }

    public void confirmDelete(ActionEvent e){
        try {

            Trainee currentTrainee = TraineeSession.getInstance().getCurrentTrainee();
            if (currentTrainee != null) {
                traineeDao.delete(currentTrainee);

                showAlert.alert(Alert.AlertType.INFORMATION,  "Your account has been successfully deleted!");

                deleteCtn.setVisible(false);
                opacityPane.setVisible(false);

                redirectToLoginPage();
            } else {
                showAlert.alert(Alert.AlertType.ERROR, "No account is selected to delete.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert.alert(Alert.AlertType.ERROR, "An error occurred while deleting your account. Please try again.");
        }
    }

    public void redirectToLoginPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/login-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        opacityPane.setVisible(false);
    }
}
