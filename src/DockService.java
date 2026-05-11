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
                "SELECT * FROM docks WHERE current_ship_id = ? AND status IN ('assigned','docked')";

        String updateSql =
                "UPDATE docks " +
                        "SET status = 'assigned', current_ship_id = ? " +
                        "WHERE number = ? AND status = 'available'";

        try (Connection conn = DatabaseManager.connect()) {

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, shipId);

                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Ship is already assigned or docked!");
                    return false;
                }
            }

            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setInt(1, shipId);
                updateStmt.setInt(2, dockNumber);

                return updateStmt.executeUpdate() > 0;
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
                docks.add(new Dock(
                        rs.getInt("id"),
                        rs.getInt("number"),
                        rs.getString("status"),
                        getNullableInt(rs, "current_ship_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return docks;
    }

    public ArrayList<Dock> getAssignedDocks() {

        ArrayList<Dock> docks = new ArrayList<>();

        String sql = "SELECT * FROM docks WHERE status = 'assigned' ORDER BY number";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                docks.add(new Dock(
                        rs.getInt("id"),
                        rs.getInt("number"),
                        rs.getString("status"),
                        getNullableInt(rs, "current_ship_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return docks;
    }

    public ArrayList<Dock> getDockedDocks() {

        ArrayList<Dock> docks = new ArrayList<>();

        String sql = "SELECT * FROM docks WHERE status = 'docked' ORDER BY number";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                docks.add(new Dock(
                        rs.getInt("id"),
                        rs.getInt("number"),
                        rs.getString("status"),
                        getNullableInt(rs, "current_ship_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return docks;
    }

    public ArrayList<Dock> getOccupiedDocks() {
        // Για παλιές σελίδες που το χρησιμοποιούν:
        // επιστρέφει και assigned και docked.
        ArrayList<Dock> docks = new ArrayList<>();

        String sql = "SELECT * FROM docks WHERE status IN ('assigned','docked') ORDER BY number";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                docks.add(new Dock(
                        rs.getInt("id"),
                        rs.getInt("number"),
                        rs.getString("status"),
                        getNullableInt(rs, "current_ship_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return docks;
    }

    public boolean markAsDocked(int dockNumber) {

        String sql =
                "UPDATE docks SET status = 'docked' " +
                        "WHERE number = ? AND status = 'assigned'";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, dockNumber);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean releaseDock(int dockNumber) {

        String sql =
                "UPDATE docks " +
                        "SET status = 'available', current_ship_id = NULL " +
                        "WHERE number = ? AND status = 'docked'";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, dockNumber);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isShipDocked(int shipId) {

        String sql =
                "SELECT * FROM docks " +
                        "WHERE current_ship_id = ? AND status = 'docked'";

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

    private Integer getNullableInt(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);

        if (rs.wasNull()) {
            return null;
        }

        return value;
    }
}