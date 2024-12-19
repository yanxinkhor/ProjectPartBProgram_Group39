package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.MealMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Meal;

import java.io.IOException;
import java.util.List;

public class viewAllMealsController {

    private static DaoInterface<Meal> mealDao = new DaoImplement<>("meal.txt", new MealMapper());

    public List<Meal> loadAllMeals() throws IOException {
        return mealDao.getAll();
    }

    public void addMeal(Meal meal) throws IOException {
        mealDao.add(meal);
    }

    public void updateMeal(Meal oldMeal, Meal newMeal) throws IOException {
        mealDao.update(oldMeal, newMeal);
    }

    public void deleteMeal(Meal meal) throws IOException {
        mealDao.delete(meal);
    }
}
