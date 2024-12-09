package com.example.projectpartbprogram_group39.Controllers.Service;
import com.example.projectpartbprogram_group39.DAO.TraineeDao.TraineeDaoImp;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.Encryption;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.scene.control.Alert;

import java.io.IOException;

public class signupController {

    private final TraineeDaoImp traineeDao = new TraineeDaoImp();

    public boolean validateSignupInfo(String username, String ageString, String phoneNo, String email, String gender,
                                      String heightString, String weightString, String password, String passwordConfirmed) throws IOException {

        if (username.isEmpty() || ageString.isEmpty() || phoneNo.isEmpty() || email.isEmpty() || gender.isEmpty() ||
                heightString.isEmpty() || weightString.isEmpty() || password.isEmpty() || passwordConfirmed.isEmpty()) {
            showAlert.alert(Alert.AlertType.ERROR, "Please fill in all the credentials");
            return false;
        }

        if (!password.equals(passwordConfirmed)) {
            showAlert.alert(Alert.AlertType.ERROR, "The passwords do not match!");
            return false;
        }

        try {
            int age = Integer.parseInt(ageString);
            double height = Double.parseDouble(heightString);
            double weight = Double.parseDouble(weightString);

            String encryptedPassword = Encryption.hashPassword(password);

            if (traineeDao.userExists(username, email)) {
                showAlert.alert(Alert.AlertType.ERROR, "User already exists!");
                return false;
            } else {
                Trainee trainee = new Trainee(username, age, gender, phoneNo, email, height, weight, encryptedPassword);
                traineeDao.StoreTrainee(trainee);
                return true;
            }

        } catch (NumberFormatException event) {
            showAlert.alert(Alert.AlertType.ERROR, "Please enter valid credentials");
            return false;
        }

    }


}
