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
        String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "user_type TEXT NOT NULL CHECK (user_type IN ('Captain','ShipOwner','DockWorker','PortAuthorityManager'))," +
        "name TEXT NOT NULL," +
        "surname TEXT NOT NULL," +
        "phone TEXT," +
        "email TEXT UNIQUE," +
        "date_of_birth TEXT," +
        "username TEXT UNIQUE NOT NULL," +
        "password TEXT NOT NULL" +
        ");";
                 
        String shipsTable = "CREATE TABLE IF NOT EXISTS ships (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "ship_code TEXT UNIQUE NOT NULL," +
        "name TEXT NOT NULL," +
        "type TEXT NOT NULL CHECK (type IN ('Cargo','Tanker','Passenger'))," +
        "capacity INTEGER," +
        "owner_id INTEGER," +
        "captain_id INTEGER" +
        ");";

        String docksTable = "CREATE TABLE IF NOT EXISTS docks (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "number INTEGER UNIQUE NOT NULL," +
        "status TEXT NOT NULL CHECK(status IN ('available','assigned','docked'))," +
        "current_ship_id INTEGER" +
        ");";

        String portEntryRequestsTable = "CREATE TABLE IF NOT EXISTS port_entry_requests (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "ship_id INTEGER NOT NULL," +
        "captain_id INTEGER NOT NULL," +
        "arrival_date TEXT," +
        "status TEXT NOT NULL CHECK (status IN ('pending','yes','no'))" +
        ");";

        String dockingRequestsTable = "CREATE TABLE IF NOT EXISTS docking_requests (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "ship_id INTEGER NOT NULL," +
        "captain_id INTEGER NOT NULL," +
        "requested_date TEXT," +
        "status TEXT NOT NULL CHECK (status IN ('pending','yes','no'))" +
        ");";

        String paymentsTable = "CREATE TABLE IF NOT EXISTS payments (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "ship_id INTEGER NOT NULL," +
        "owner_id INTEGER NOT NULL," +
        "amount REAL NOT NULL," +
        "description TEXT," +
        "status TEXT NOT NULL CHECK (status IN ('pending','paid'))" +
        ");";

    try (Connection conn = connect();
     Statement stmt = conn.createStatement()) {

    stmt.execute(usersTable);
    stmt.execute(shipsTable);
    stmt.execute(docksTable);
    stmt.execute(portEntryRequestsTable);
    stmt.execute(dockingRequestsTable);
    stmt.execute(paymentsTable);

    for (int i = 1; i <= 30; i++) {

    stmt.execute(
            "INSERT OR IGNORE INTO docks " +
            "(number, status, current_ship_id) " +
            "VALUES (" + i + ", 'available', NULL)"
    );
}

    System.out.println("Tables ready!");

} catch (Exception e) {
    e.printStackTrace();
}
}
}
