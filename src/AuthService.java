import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {

    // REGISTER USER
    public boolean registerUser(User user) {

        String sql = "INSERT INTO users " +
                "(user_type, name, surname, phone, email, date_of_birth, username, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUserType());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getSurname());
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getDateOfBirth());
            pstmt.setString(7, user.getUsername());
            pstmt.setString(8, user.getPassword());

            pstmt.executeUpdate();

            System.out.println("User registered successfully!");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // LOGIN USER
   public boolean loginUser(String username, String password, String userType) {

    String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND user_type = ?";

    try (Connection conn = DatabaseManager.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, username);
        pstmt.setString(2, password);
        pstmt.setString(3, userType);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Wrong username, password or role!");
            return false;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}
