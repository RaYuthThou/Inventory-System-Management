package config;
import java.sql.*;

public class DBConnectioni_UserInformation
{
    private static final String URL = "jdbc:mysql://localhost:3306/your_data_base";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    // Connection is a Class of SQL library for Connection to spcefic Database
    private static Connection conn = null;

    public static Connection getConnection() {
        try {

            if (conn == null || conn.isClosed()) {
                // DriverManager is Basic service ( Input URL , USER , PASSWORD) for Java Database Connection
                conn = DriverManager.getConnection(URL, USER, PASSWORD);

            }
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
        return conn;
    }


}
