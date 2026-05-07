import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}