import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {

    public LoginPage() {

        setTitle("PATRAS LIMANI - Login");
        setSize(450, 330);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("PATRAS LIMANI", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        String[] roles = {
                "Captain",
                "ShipOwner",
                "DockWorker",
                "PortAuthorityManager"
        };

        JComboBox<String> roleBox = new JComboBox<>(roles);

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Role:"));
        formPanel.add(roleBox);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register User");

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.add(loginButton, BorderLayout.CENTER);
        bottomPanel.add(registerButton, BorderLayout.EAST);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        loginButton.addActionListener(e -> {

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            AuthService authService = new AuthService();

            User loggedUser = authService.getUserByLogin(username, password, role);

            if (loggedUser != null) {
                JOptionPane.showMessageDialog(this, "Login successful!");

                dispose();

                switch (role) {
                    case "Captain":
                        new CaptainDashboard(loggedUser);
                        break;

                    case "ShipOwner":
                        new ShipOwnerDashboard(loggedUser);
                        break;

                    case "DockWorker":
                        new DockWorkerDashboard(loggedUser);
                        break;

                    case "PortAuthorityManager":
                        new PortAuthorityDashboard(loggedUser);
                        break;

                    default:
                        JOptionPane.showMessageDialog(this, "Unknown role!");
                        break;
                }

            } else {
                JOptionPane.showMessageDialog(this, "Wrong username, password or role!");
            }
        });

        registerButton.addActionListener(e -> {
            new RegisterPage();
        });

        setVisible(true);
    }
}