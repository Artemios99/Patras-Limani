import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {

    public LoginPage() {

        setTitle("PATRAS LIMANI - Login");
        setSize(450, 330);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel("PATRAS LIMANI", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel roleLabel = new JLabel("Role:");
        String[] roles = {"Captain", "ShipOwner", "DockWorker", "PortAuthorityManager"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(roleLabel);
        formPanel.add(roleComboBox);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register User");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(loginButton, BorderLayout.CENTER);
        bottomPanel.add(registerButton, BorderLayout.EAST);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            AuthService authService = new AuthService();

            if (authService.loginUser(username, password,role)) {
                JOptionPane.showMessageDialog(this, "Login successful as " + role);
            } else {
                JOptionPane.showMessageDialog(this, "Wrong username, password or role");
            }
        });

        registerButton.addActionListener(e -> {
            new RegisterPage();
            
        });

        setVisible(true);
    }
}