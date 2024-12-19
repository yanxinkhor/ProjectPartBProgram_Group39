package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.CoachMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Coach;

import java.io.IOException;
import java.util.List;

public class coachController {
    private static final DaoInterface<Coach> coachDao = new DaoImplement<>("coaches.txt", new CoachMapper());

    public List<Coach> getAllCoaches() throws IOException {
        return coachDao.getAll();
    }

    public void updateCoachStatus(Coach updatedCoach) throws IOException {
        List<Coach> coaches = coachDao.getAll();
        for (Coach coach : coaches) {
            if (coach.getName().equals(updatedCoach.getName())) {
                coach.setIsJoined(updatedCoach.getIsJoined());
                coachDao.update(coach, updatedCoach);
                break;
            }
        }
    }

    public boolean isCoachJoined(String coachName) throws IOException {
        List<Coach> coaches = coachDao.getAll();
        for (Coach coach : coaches) {
            if (coach.getName().equals(coachName)) {
                return coach.getIsJoined();
            }
        }
        return false;
    }

    public void InitialCoaches() throws IOException, IOException {
        Coach initCoach1 = new Coach("Ethan Carter", false);
        Coach initCoach2 = new Coach("Lucas Bennett", false);
        Coach initCoach3 = new Coach("Max Donovan", false);
        Coach initCoach4 = new Coach("Nathan Pierce", false);
        Coach initCoach5 = new Coach("Olivia Harper", false);
        Coach initCoach6 = new Coach("Mia Collins", false);

        List<Coach> coaches = coachDao.getAll();
        if (coaches.isEmpty()) {
            coachDao.add(initCoach1);
            coachDao.add(initCoach2);
            coachDao.add(initCoach3);
            coachDao.add(initCoach4);
            coachDao.add(initCoach5);
            coachDao.add(initCoach6);
        }
    }
}
