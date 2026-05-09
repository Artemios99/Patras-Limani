import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DockService {

    public boolean addDock(Dock dock) {

        String sql = "INSERT INTO docks (number, status, current_ship_id) " +
                     "VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, dock.getNumber());
            pstmt.setString(2, dock.getStatus());

            if (dock.getCurrentShipId() == null) {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(3, dock.getCurrentShipId());
            }

            pstmt.executeUpdate();

            System.out.println("Dock added successfully!");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

public boolean assignDock(int dockNumber, int shipId) {

    String checkSql =
            "SELECT * FROM docks WHERE current_ship_id = ? AND status = 'occupied'";

    String updateSql =
            "UPDATE docks " +
            "SET status = 'occupied', current_ship_id = ? " +
            "WHERE number = ? AND status = 'available'";

    try (Connection conn = DatabaseManager.connect()) {

        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, shipId);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("Ship is already assigned to a dock!");
                return false;
            }
        }

        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            updateStmt.setInt(1, shipId);
            updateStmt.setInt(2, dockNumber);

            int rowsUpdated = updateStmt.executeUpdate();

            return rowsUpdated > 0;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public ArrayList<Dock> getAllDocks() {

    ArrayList<Dock> docks = new ArrayList<>();

    String sql = "SELECT * FROM docks ORDER BY number";

    try (Connection conn = DatabaseManager.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {

            Dock dock = new Dock(
                    rs.getInt("id"),
                    rs.getInt("number"),
                    rs.getString("status"),
                    rs.getInt("current_ship_id")
            );

            docks.add(dock);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return docks;
}

public ArrayList<Dock> getOccupiedDocks() {

    ArrayList<Dock> docks = new ArrayList<>();

    String sql = "SELECT * FROM docks WHERE status = 'occupied'";

    try (Connection conn = DatabaseManager.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Dock dock = new Dock(
                    rs.getInt("id"),
                    rs.getInt("number"),
                    rs.getString("status"),
                    rs.getInt("current_ship_id")
            );

            docks.add(dock);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return docks;
}

public boolean isShipDocked(int shipId) {

    String sql = "SELECT * FROM docks WHERE current_ship_id = ? AND status = 'occupied'";

    try (Connection conn = DatabaseManager.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, shipId);

        ResultSet rs = pstmt.executeQuery();

        return rs.next();

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}