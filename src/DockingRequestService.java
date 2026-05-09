import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

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

    public ArrayList<DockingRequest> getApprovedRequests() {

        ArrayList<DockingRequest> requests = new ArrayList<>();

        String sql =
                "SELECT * FROM docking_requests " +
                "WHERE status = 'yes' " +
                "AND ship_id NOT IN (" +
                "SELECT current_ship_id FROM docks " +
                "WHERE status = 'occupied' AND current_ship_id IS NOT NULL" +
                ")";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                DockingRequest request = new DockingRequest(
                        rs.getInt("id"),
                        rs.getInt("ship_id"),
                        rs.getInt("captain_id"),
                        rs.getString("requested_date"),
                        rs.getString("status")
                );

                requests.add(request);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }

    public ArrayList<DockingRequest> getAllRequests() {

        ArrayList<DockingRequest> requests = new ArrayList<>();

        String sql = "SELECT * FROM docking_requests";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                DockingRequest request = new DockingRequest(
                        rs.getInt("id"),
                        rs.getInt("ship_id"),
                        rs.getInt("captain_id"),
                        rs.getString("requested_date"),
                        rs.getString("status")
                );

                requests.add(request);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }

    public boolean updateRequestStatus(int requestId, String status) {

        String sql = "UPDATE docking_requests SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, requestId);

            int rowsUpdated = pstmt.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DockingRequest getRequestById(int requestId) {

        String sql = "SELECT * FROM docking_requests WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, requestId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new DockingRequest(
                        rs.getInt("id"),
                        rs.getInt("ship_id"),
                        rs.getInt("captain_id"),
                        rs.getString("requested_date"),
                        rs.getString("status")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}