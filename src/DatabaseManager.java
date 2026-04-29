import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    public static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");

            Connection conn = DriverManager.getConnection("jdbc:sqlite:patras.db");
            System.out.println("Connected to database!");
            return conn;

        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found!");
            e.printStackTrace();
            return null;

        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
            return null;
        }
    }
}