package com.example.projectpartbprogram_group39.DAO;

import com.example.projectpartbprogram_group39.Models.Trainee;

import java.io.IOException;
import java.util.List;

public interface daoInterface {
    void StoreTrainee(Trainee trainee);
    boolean userExists(String username, String email)throws IOException; ;
    List<Trainee> getAllTrainees();
}