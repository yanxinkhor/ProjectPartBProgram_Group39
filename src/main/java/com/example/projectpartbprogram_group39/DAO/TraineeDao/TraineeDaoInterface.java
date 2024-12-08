package com.example.projectpartbprogram_group39.DAO.TraineeDao;

import com.example.projectpartbprogram_group39.Models.Trainee;

import java.io.IOException;
import java.util.List;

public interface TraineeDaoInterface {
    void StoreTrainee(Trainee trainee);
    boolean userExists(String username, String email)throws IOException;
    List<Trainee> getAllTrainees()throws IOException;
}
