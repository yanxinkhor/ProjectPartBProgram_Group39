package com.example.projectpartbprogram_group39.Controllers.Service;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.TraineeMapper;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.AESEncryption;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.List;

public class signupController {

    private static DaoInterface<Trainee> traineeDao = new DaoImplement<>("userInfo.txt", new TraineeMapper());
    private static final String ENCRYPTION_KEY = "1234567890123456";
    private static final AESEncryption aesEncryption = new AESEncryption(ENCRYPTION_KEY);

    public boolean validateSignupInfo(String username, String ageStr, String phoneNo, String email, String gender,
                                      String heightStr, String weightStr, String password, String passConfirmed) throws IOException {

        if (username.isEmpty() || ageStr.isEmpty() || phoneNo.isEmpty() || email.isEmpty() || gender.isEmpty() ||
                heightStr.isEmpty() || weightStr.isEmpty() || password.isEmpty() || passConfirmed.isEmpty()) {
            showAlert.alert(Alert.AlertType.ERROR, "Please fill in all the credentials");
            return false;
        }

        if (!password.equals(passConfirmed)) {
            showAlert.alert(Alert.AlertType.ERROR, "The passwords do not match!");
            return false;
        }

        if(username.equals("Admin") && password.equals("admin1234")){
            showAlert.alert(Alert.AlertType.ERROR,"You cannot use a Admin account to sign up");
            return false;
        }

        try {
            int age = Integer.parseInt(ageStr);
            double height = Double.parseDouble(heightStr);
            double weight = Double.parseDouble(weightStr);

            String encryptedPassword = aesEncryption.encrypt(password);

            List<Trainee> existingTrainees = traineeDao.getAll();
            for (Trainee existingTrainee : existingTrainees) {
                if (existingTrainee.getUsername().equalsIgnoreCase(username)) {
                    showAlert.alert(Alert.AlertType.ERROR, "User already exists. Please choose another username.");
                    return false;
                }
            }

            Trainee trainee = new Trainee(username, age, gender, phoneNo, email, height, weight, encryptedPassword);
            traineeDao.add(trainee);
            return true;

        } catch (NumberFormatException event) {
            showAlert.alert(Alert.AlertType.ERROR, "Please enter valid credentials");
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
