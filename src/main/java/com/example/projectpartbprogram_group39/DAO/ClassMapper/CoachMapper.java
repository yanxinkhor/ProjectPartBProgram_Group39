package com.example.projectpartbprogram_group39.DAO.ClassMapper;

import com.example.projectpartbprogram_group39.Models.Coach;

public class CoachMapper implements EntityMapper<Coach>{
    @Override
    public Coach fromString(String string) {
        return Coach.splitString(string);
    }

    @Override
    public String toString(Coach object) {
        return object.toString();
    }

    @Override
    public boolean equals(Coach obj1, Coach obj2) {
        return obj1.getName().equals(obj2.getName());
    }
}
