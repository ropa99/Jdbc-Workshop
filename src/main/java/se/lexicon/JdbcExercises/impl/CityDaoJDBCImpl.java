package se.lexicon.JdbcExercises.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import se.lexicon.JdbcExercises.db.DbConnection;

public class CityDaoJDBCImpl implements ICityDao {

    @Override
    public se.lexicon.JdbcExercises.model.City findById(int id) {
        se.lexicon.JdbcExercises.model.City cityById = null;
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM city WHERE id = ?")
        ) {
            preparedStatement.setInt(1, id);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                if (resultSet.next()) {
                    int cityId = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String countryCode = resultSet.getString(3);
                    String district = resultSet.getString(4);
                    int population = resultSet.getInt(5);
                    cityById = new se.lexicon.JdbcExercises.model.City(cityId, name, countryCode, district, population);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data for findById() with id: " + id + " " + e.getMessage());
        }
        return cityById;
    }

    @Override
    public List<se.lexicon.JdbcExercises.model.City> findByCode(String code) {
        List<se.lexicon.JdbcExercises.model.City> cityByCodeList = new ArrayList<>();
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM city WHERE CountryCode = ?")
        ) {
            preparedStatement.setString(1, code);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                while (resultSet.next()) {
                    int cityId = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String countryCode = resultSet.getString(3);
                    String district = resultSet.getString(4);
                    int population = resultSet.getInt(5);
                    cityByCodeList.add(new se.lexicon.JdbcExercises.model.City(cityId, name, countryCode, district, population));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data for findByCode() with country code: " + code + " " + e.getMessage());
        }
        return cityByCodeList;
    }

    @Override
    public List<se.lexicon.JdbcExercises.model.City> findByName(String name) {
        List<se.lexicon.JdbcExercises.model.City> cityByNameList = new ArrayList<>();
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM city WHERE name = ?")
        ) {
            preparedStatement.setString(1, name);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                while (resultSet.next()) {
                    int cityId = resultSet.getInt(1);
                    String cityName = resultSet.getString(2);
                    String countryCode = resultSet.getString(3);
                    String district = resultSet.getString(4);
                    int population = resultSet.getInt(5);
                    cityByNameList.add(new se.lexicon.JdbcExercises.model.City(cityId, cityName, countryCode, district, population));
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data for findByName() with name: " + name + " " + e.getMessage());
        }
        return cityByNameList;
    }

    @Override
    public List<se.lexicon.JdbcExercises.model.City> findAll() {
        List<se.lexicon.JdbcExercises.model.City> cityFindAllList = new ArrayList<>();
        try (
                Connection connection = DbConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM city")
        ) {
            while (resultSet.next()) {
                int cityId = resultSet.getInt(1);
                String cityName = resultSet.getString(2);
                String countryCode = resultSet.getString(3);
                String district = resultSet.getString(4);
                int population = resultSet.getInt(5);
                cityFindAllList.add(new se.lexicon.JdbcExercises.model.City(cityId, cityName, countryCode, district, population));
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch data for findAll() " + e.getMessage());
        }
        return cityFindAllList;
    }

    @Override
    public se.lexicon.JdbcExercises.model.City add(se.lexicon.JdbcExercises.model.City city) {
        String insertCityQuery = "INSERT INTO city (Name, CountryCode, District, Population) VALUES (?, ?, ?, ?)";
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertCityQuery, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryCode());
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setInt(4, city.getPopulation());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Insert operation for city table successfully.");
            } else {
                connection.rollback();
                System.out.println("Insert operation for city table failed.");
            }
            try (
                    ResultSet generatedKey = preparedStatement.getGeneratedKeys()
            ) {
                if (generatedKey.next()) {
                    city.setId(generatedKey.getInt(1));
                } else {
                    connection.rollback();
                    System.out.println("Insert operation for city table failed.");
                }
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return city;
    }

    @Override
    public se.lexicon.JdbcExercises.model.City update(se.lexicon.JdbcExercises.model.City city) {
        String updateCityQuery = "UPDATE city SET Name = ?, CountryCode = ?, District = ?, Population = ? WHERE ID = ?";
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateCityQuery)
        ) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryCode());
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setInt(4, city.getPopulation());
            preparedStatement.setInt(5, city.getId());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Update operation for city table successfully.");
            } else {
                connection.rollback();
                System.out.println("Update operation for city table failed.");
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return city;
    }

    @Override
    public int delete(se.lexicon.JdbcExercises.model.City city) {
        int rowsDeleted = -1;
        String deleteCityQuery = "DELETE FROM city WHERE ID = ?";
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteCityQuery)
        ) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, city.getId());

            rowsDeleted = preparedStatement.executeUpdate();
            System.out.println(rowsDeleted);

            if (rowsDeleted > 0) {
                System.out.println("Delete operation for city table successfully.");
            } else {
                connection.rollback();
                System.out.println("Delete operation for city table failed.");
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return rowsDeleted;
    }
}
