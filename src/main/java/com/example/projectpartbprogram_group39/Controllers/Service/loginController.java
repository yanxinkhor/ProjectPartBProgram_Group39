package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.TraineeDao.TraineeDaoImp;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.DAO.genericDao.TraineeMapper;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.Encryption;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.List;

public class loginController {

    static DaoInterface<Trainee> traineeDao = new DaoImplement<>("userInfo.txt", new TraineeMapper());
    private static final String ADMIN_USERNAME = "Admin";
    private static final String ADMIN_PASSWORD = "admin1234";

    public boolean validateUser(String username, String password) throws IOException {
        String hashedPassword = Encryption.hashPassword(password);

        if (username.isEmpty() || password.isEmpty()) {
            showAlert.alert(Alert.AlertType.ERROR, "Please fill in all information");
            return false;
        }

        if(username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)){
            return true;
        }

        List<Trainee> trainees = traineeDao.getAll();

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
        List<Trainee> trainees = traineeDao.getAll();

        if(username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)){
            return new Trainee(ADMIN_USERNAME,25,"male","012345678","admin@gmail.com",175.5,50.5,ADMIN_PASSWORD);
        }

        for (Trainee trainee : trainees) {
            if (trainee.getUsername().equals(username) && trainee.getPassword().equals(hashedPassword)) {
                return trainee;
            }
        }
        return null;
    }
}
