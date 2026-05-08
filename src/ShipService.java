import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ShipService {

    public boolean registerShip(Ship ship) {

        String sql = "INSERT INTO ships " +
                "(ship_code, name, type, capacity, owner_id, captain_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ship.getShipCode());
            pstmt.setString(2, ship.getName());
            pstmt.setString(3, ship.getType());
            pstmt.setInt(4, ship.getCapacity());
            pstmt.setInt(5, ship.getOwnerId());
            pstmt.setInt(6, ship.getCaptainId());

            pstmt.executeUpdate();

            System.out.println("Ship registered successfully!");
            return true;

        } catch (SQLException e) {

            if (e.getMessage().contains("UNIQUE")) {

                System.out.println("Ship ID already exists!");

            } else {
                e.printStackTrace();
            }

            return false;
        }
    }

    public ArrayList<Ship> getShipsByCaptainId(int captainId) {

    ArrayList<Ship> ships = new ArrayList<>();

    String sql = "SELECT * FROM ships WHERE captain_id = ?";

    try (Connection conn = DatabaseManager.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, captainId);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Ship ship = new Ship(
                    rs.getInt("id"),
                    rs.getString("ship_code"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getInt("capacity"),
                    rs.getInt("owner_id"),
                    rs.getInt("captain_id")
            );

            ships.add(ship);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return ships;
}

public String getLatestPortEntryStatus(int shipId) {

    String sql = "SELECT status FROM port_entry_requests " +
                 "WHERE ship_id = ? " +
                 "ORDER BY id DESC LIMIT 1";

    try (Connection conn = DatabaseManager.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, shipId);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return rs.getString("status");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return "No Request";
}

public String getLatestDockingStatus(int shipId) {

    String sql = "SELECT status FROM docking_requests " +
                 "WHERE ship_id = ? " +
                 "ORDER BY id DESC LIMIT 1";

    try (Connection conn = DatabaseManager.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, shipId);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return rs.getString("status");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return "No Request";
}

}