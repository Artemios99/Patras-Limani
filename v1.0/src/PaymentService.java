import java.sql.*;
import java.util.ArrayList;

public class PaymentService {

    public boolean createPayment(Payment payment) {
        String sql = "INSERT INTO payments (ship_id, owner_id, amount, description, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, payment.getShipId());
            pstmt.setInt(2, payment.getOwnerId());
            pstmt.setDouble(3, payment.getAmount());
            pstmt.setString(4, payment.getDescription());
            pstmt.setString(5, payment.getStatus());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean payPayment(int paymentId) {
        String sql = "UPDATE payments SET status = 'paid' WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, paymentId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Payment> getPaymentsByOwnerId(int ownerId) {
        ArrayList<Payment> payments = new ArrayList<>();

        String sql = "SELECT * FROM payments WHERE owner_id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ownerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                payments.add(new Payment(
                        rs.getInt("id"),
                        rs.getInt("ship_id"),
                        rs.getInt("owner_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }

    public String getPaymentStatus(int shipId, int ownerId) {
    String sql = "SELECT status FROM payments WHERE ship_id = ? AND owner_id = ? ORDER BY id DESC LIMIT 1";

    try (Connection conn = DatabaseManager.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, shipId);
        pstmt.setInt(2, ownerId);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return rs.getString("status");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return "unpaid";
}

public boolean payOrCreatePayment(int shipId, int ownerId, double amount, String description) {
    String existing = "SELECT id FROM payments WHERE ship_id = ? AND owner_id = ? ORDER BY id DESC LIMIT 1";

    try (Connection conn = DatabaseManager.connect();
         PreparedStatement checkStmt = conn.prepareStatement(existing)) {

        checkStmt.setInt(1, shipId);
        checkStmt.setInt(2, ownerId);

        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            int paymentId = rs.getInt("id");

            String update = "UPDATE payments SET amount = ?, description = ?, status = 'paid' WHERE id = ?";

            try (PreparedStatement updateStmt = conn.prepareStatement(update)) {
                updateStmt.setDouble(1, amount);
                updateStmt.setString(2, description);
                updateStmt.setInt(3, paymentId);

                return updateStmt.executeUpdate() > 0;
            }

        } else {
            String insert = "INSERT INTO payments (ship_id, owner_id, amount, description, status) VALUES (?, ?, ?, ?, 'paid')";

            try (PreparedStatement insertStmt = conn.prepareStatement(insert)) {
                insertStmt.setInt(1, shipId);
                insertStmt.setInt(2, ownerId);
                insertStmt.setDouble(3, amount);
                insertStmt.setString(4, description);

                return insertStmt.executeUpdate() > 0;
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}