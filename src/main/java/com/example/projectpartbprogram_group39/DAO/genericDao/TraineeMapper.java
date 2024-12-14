package com.example.projectpartbprogram_group39.DAO.genericDao;

import com.example.projectpartbprogram_group39.Models.Trainee;

public class TraineeMapper implements EntityMapper<Trainee>{
    @Override
    public Trainee fromString(String string) {
        return Trainee.splitString(string);
    }

    @Override
    public String toString(Trainee object) {
        return object.toString();
    }

    @Override
    public boolean equals(Trainee obj1, Trainee obj2) {
        return obj1.getUsername().equals(obj2.getUsername()) && obj1.getEmail().equals(obj2.getEmail());
    }
}
