import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentService {

    public boolean createPayment(Payment payment) {

        String sql = "INSERT INTO payments " +
                     "(ship_id, owner_id, amount, description, status) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, payment.getShipId());
            pstmt.setInt(2, payment.getOwnerId());
            pstmt.setDouble(3, payment.getAmount());
            pstmt.setString(4, payment.getDescription());
            pstmt.setString(5, payment.getStatus());

            pstmt.executeUpdate();

            System.out.println("Payment created successfully!");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}