import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShipService {

    public boolean registerShip(Ship ship) {
        String sql = "INSERT INTO ships (name, type, capacity, owner_id, captain_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ship.getName());
            pstmt.setString(2, ship.getType());
            pstmt.setInt(3, ship.getCapacity());
            pstmt.setInt(4, ship.getOwnerId());
            pstmt.setInt(5, ship.getCaptainId());

            pstmt.executeUpdate();

            System.out.println("Ship registered successfully!");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}