import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PortEntryRequestService {

    public boolean createRequest(PortEntryRequests request) {

    String checkSql =
            "SELECT * FROM port_entry_requests WHERE ship_id = ?";

    String insertSql =
            "INSERT INTO port_entry_requests " +
            "(ship_id, captain_id, arrival_date, status) " +
            "VALUES (?, ?, ?, ?)";

    try (Connection conn = DatabaseManager.connect()) {

        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, request.getShipId());

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("Port entry request already exists for this ship.");
                return false;
            }
        }

        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            insertStmt.setInt(1, request.getShipId());
            insertStmt.setInt(2, request.getCaptainId());
            insertStmt.setString(3, request.getArrivalDate());
            insertStmt.setString(4, request.getStatus());

            insertStmt.executeUpdate();

            System.out.println("Port entry request created successfully!");
            return true;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public ArrayList<PortEntryRequests> getAllRequests() {

    ArrayList<PortEntryRequests> requests = new ArrayList<>();

    String sql = "SELECT * FROM port_entry_requests";

    try (Connection conn = DatabaseManager.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            PortEntryRequests request = new PortEntryRequests(
                    rs.getInt("id"),
                    rs.getInt("ship_id"),
                    rs.getInt("captain_id"),
                    rs.getString("arrival_date"),
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

    String sql = "UPDATE port_entry_requests SET status = ? WHERE id = ?";

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

public ArrayList<PortEntryRequests> getApprovedRequests() {

    ArrayList<PortEntryRequests> requests = new ArrayList<>();

    String sql = "SELECT * FROM port_entry_requests WHERE status = 'yes'";

    try (Connection conn = DatabaseManager.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            PortEntryRequests request = new PortEntryRequests(
                    rs.getInt("id"),
                    rs.getInt("ship_id"),
                    rs.getInt("captain_id"),
                    rs.getString("arrival_date"),
                    rs.getString("status")
            );

            requests.add(request);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return requests;
}

public boolean hasApprovedPortEntry(int shipId) {

    String sql = "SELECT * FROM port_entry_requests WHERE ship_id = ? AND status = 'yes'";

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