import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DockingRequestService {

    public boolean createRequest(DockingRequest request) {

        String sql = "INSERT INTO docking_requests " +
                     "(ship_id, captain_id, requested_date, status) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, request.getShipId());
            pstmt.setInt(2, request.getCaptainId());
            pstmt.setString(3, request.getRequestedDate());
            pstmt.setString(4, request.getStatus());

            pstmt.executeUpdate();

            System.out.println("Docking request created successfully!");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}