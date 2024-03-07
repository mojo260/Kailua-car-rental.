package kailua_biler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {
    private String database = "jdbc:mysql://localhost:3306/my_car";
    private String username = "root";
    private String password = "12Qwe++!";
    private Connection connection = null;

    public MySqlConnection() {
        createConnection();
    }

    Connection createConnection() {
        if (connection != null)
            return connection;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(database, username, password);
            System.out.println("Conenction was made");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("EXCEPTION: " + e.getStackTrace());
        }
    }
}