package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.CoachMapper;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.TraineeMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Coach;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.AESEncryption;
import com.example.projectpartbprogram_group39.Utils.TraineeSession;

import java.io.IOException;
import java.util.List;

public class ProfileController {
    private DaoInterface<Trainee> profileDao = new DaoImplement<>("userInfo.txt",new TraineeMapper());
    private DaoInterface<Coach> coachDao = new DaoImplement<>("coaches.txt",new CoachMapper());
    private static final String ENCRYPTION_KEY = "1234567890123456";
    private static final AESEncryption aesEncryption = new AESEncryption(ENCRYPTION_KEY);

    public Trainee getCurrentTrainee() {
        return TraineeSession.getInstance().getCurrentTrainee();
    }

    public String decryptPassword(String encryptedPassword) {
        try {
            return aesEncryption.decrypt(encryptedPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean validateProfileFields(String username, String email, String ageStr, String gender,
                                         String phoneNo, String weightStr, String heightStr) {
        return !username.isEmpty() && !email.isEmpty() && !ageStr.isEmpty() && !gender.isEmpty() &&
                !phoneNo.isEmpty() && !weightStr.isEmpty() && !heightStr.isEmpty();
    }

    public void updateProfile(String username, String ageStr, String gender, String phoneNo,
                              String email, String weightStr, String heightStr, String password) throws Exception {
        int age = Integer.parseInt(ageStr);
        double height = Double.parseDouble(heightStr);
        double weight = Double.parseDouble(weightStr);

        String encryptedPassword = aesEncryption.encrypt(password);

        Trainee updatedTrainee = new Trainee(username, age, gender, phoneNo, email, height, weight, encryptedPassword);

        profileDao.update(getCurrentTrainee(), updatedTrainee);
        TraineeSession.getInstance().setCurrentTrainee(updatedTrainee);
    }

    public List<Coach> getCoachesList() throws IOException {
        return coachDao.getAll();
    }

}