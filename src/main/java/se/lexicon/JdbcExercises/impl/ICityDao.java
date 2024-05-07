package se.lexicon.JdbcExercises.impl;

import java.util.List;
import se.lexicon.JdbcExercises.model.City;

public interface ICityDao {
    City findById(int id);
    List<City> findByCode(String code);
    List<City> findByName(String name);
    List<City> findAll();
    City add(City city);
    City update(City city);
    int delete(City city);
}
