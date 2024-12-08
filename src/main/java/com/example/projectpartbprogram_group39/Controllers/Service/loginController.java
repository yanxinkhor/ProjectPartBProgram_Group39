package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.TraineeDao.TraineeDaoImp;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.Encryption;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.List;

public class loginController {

    private static final TraineeDaoImp traineeDao = new TraineeDaoImp();

    public boolean validateUser(String username, String password) throws IOException {
        String hashedPassword = Encryption.hashPassword(password);

        if (username.isEmpty() || password.isEmpty()) {
            showAlert.alert(Alert.AlertType.ERROR, "Please fill in all information");
            return false;
        }

        List<Trainee> trainees = traineeDao.getAllTrainees();

        for (Trainee trainee : trainees) {
            if (trainee.getUsername().equals(username) && trainee.getPassword().equals(hashedPassword)) {
                return true;
            }
        }

        showAlert.alert(Alert.AlertType.ERROR, "Invalid username or password.");
        return false;
    }

    public static Trainee getLoggedInTrainee(String username, String password) throws IOException {
        String hashedPassword = Encryption.hashPassword(password);
        List<Trainee> trainees = traineeDao.getAllTrainees();

        for (Trainee trainee : trainees) {
            if (trainee.getUsername().equals(username) && trainee.getPassword().equals(hashedPassword)) {
                return trainee;
            }
        }
        return null;
    }
}