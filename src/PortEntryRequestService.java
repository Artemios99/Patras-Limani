import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PortEntryRequestService {

    public boolean createRequest(PortEntryRequests request) {

        String sql = "INSERT INTO port_entry_requests " +
                "(ship_id, captain_id, arrival_date, status) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, request.getShipId());
            pstmt.setInt(2, request.getCaptainId());
            pstmt.setString(3, request.getArrivalDate());
            pstmt.setString(4, request.getStatus());

            pstmt.executeUpdate();

            System.out.println("Port entry request created successfully!");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}