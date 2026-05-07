import javax.swing.*;
import java.awt.*;

public class RegisterPage extends JFrame {

    public RegisterPage() {

        setTitle("Register User");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Fields
        JTextField nameField = new JTextField();
        JTextField surnameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField dobField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        String[] roles = {
                "Captain",
                "ShipOwner",
                "DockWorker",
                "PortAuthorityManager"
        };

        JComboBox<String> roleBox = new JComboBox<>(roles);

        // Labels + Fields
        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Surname:"));
        panel.add(surnameField);

        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Date of Birth:"));
        panel.add(dobField);

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        panel.add(new JLabel("Role:"));
        panel.add(roleBox);

        JButton registerButton = new JButton("Register");

        panel.add(new JLabel());
        panel.add(registerButton);

        add(panel);

        // Register button action
        registerButton.addActionListener(e -> {

            String role = (String) roleBox.getSelectedItem();

            User user = new User(
                    role,
                    nameField.getText(),
                    surnameField.getText(),
                    phoneField.getText(),
                    emailField.getText(),
                    dobField.getText(),
                    usernameField.getText(),
                    new String(passwordField.getPassword())
            );

            AuthService authService = new AuthService();

            boolean success = authService.registerUser(user);

            if (success) {
                JOptionPane.showMessageDialog(this, "User registered successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!");
            }
        });

        setVisible(true);
    }
}