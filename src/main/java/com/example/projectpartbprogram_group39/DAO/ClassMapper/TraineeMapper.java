package com.example.projectpartbprogram_group39.DAO.ClassMapper;

import com.example.projectpartbprogram_group39.Models.Trainee;

public class TraineeMapper implements EntityMapper<Trainee> {
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
        if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.getUsername().equals(obj2.getUsername()) && obj1.getEmail().equals(obj2.getEmail());
    }
}
