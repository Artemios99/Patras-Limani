import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:patras.db";

    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("Connected to database!");
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public static void createTables() {
    String sql = "CREATE TABLE IF NOT EXISTS users ("+
                 "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                 "user_type TEXT NOT NULL,"+
                 "name TEXT NOT NULL,"+
                 "surname TEXT NOT NULL,"+
                 "phone TEXT,"+
                 "email TEXT UNIQUE,"+
                 "date_of_birth TEXT,"+
                 "username TEXT UNIQUE NOT NULL,"+
                 "password TEXT NOT NULL"+
                 ");";
                 
    try (Connection conn = connect();
         Statement stmt = conn.createStatement()) {

        stmt.execute(sql);
        System.out.println("Users table ready!");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
