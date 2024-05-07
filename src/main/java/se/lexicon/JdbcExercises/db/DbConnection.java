package se.lexicon.JdbcExercises.db;

public class DbConnection {
    private static final String JDBC_USER = "root";
    private static final String JDBC_PWD = "1234";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/world";


    public static java.sql.Connection getConnection() {
        java.sql.Connection connection = null;
        try {
            connection = java.sql.DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PWD);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            //todo throw a custom exception
        }
        return connection;
    }
}
