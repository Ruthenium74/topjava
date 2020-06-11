package ru.javawebinar.topjava.dao;

public class DAOFactory {
    public static MealDAO getMealDao()
    {
        return new MealDAOInMemory();
    }
}
