import javax.swing.*;
import java.awt.*;

public class RegisterShipPage extends JFrame {

    private JTextField shipIdField;
    private JTextField nameField;
    private JComboBox<String> typeBox;
    private JTextField capacityField;
    private JTextField ownerUsernameField;

    private User user;

    public RegisterShipPage(User user) {

        this.user = user;

        setTitle("Register Ship");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(backgroundColor);

        shipIdField = new JTextField();
        nameField = new JTextField();

        String[] shipTypes = {
                "Cargo",
                "Tanker",
                "Passenger"
        };

        typeBox = new JComboBox<>(shipTypes);
        capacityField = new JTextField();
        ownerUsernameField = new JTextField();

        JButton registerButton = new JButton("Register Ship");
        JButton backButton = new JButton("Back");

        styleButton(registerButton, buttonColor);
        styleButton(backButton, buttonColor);

        addLabel(panel, "Ship ID (7 digits):");
        panel.add(shipIdField);

        addLabel(panel, "Ship Name:");
        panel.add(nameField);

        addLabel(panel, "Ship Type:");
        panel.add(typeBox);

        addLabel(panel, "Capacity:");
        panel.add(capacityField);

        addLabel(panel, "Owner Username:");
        panel.add(ownerUsernameField);

        panel.add(backButton);
        panel.add(registerButton);

        add(panel);

        registerButton.addActionListener(e -> registerShip());

        backButton.addActionListener(e -> {
            dispose();
            new CaptainDashboard(user);
        });

        setVisible(true);
    }

    private void registerShip() {

        String shipId = shipIdField.getText().trim();
        String name = nameField.getText().trim();
        String type = (String) typeBox.getSelectedItem();
        String capacityText = capacityField.getText().trim();
        String ownerUsername = ownerUsernameField.getText().trim();

        if (!shipId.matches("\\d+")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ship ID must contain only digits.",
                    "Invalid Ship ID",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (shipId.length() < 7) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ship ID must contain exactly 7 digits. You entered fewer digits.",
                    "Invalid Ship ID",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (shipId.length() > 7) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ship ID must contain exactly 7 digits. You entered too many digits.",
                    "Invalid Ship ID",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ship name cannot be empty.",
                    "Invalid Ship Name",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int capacity;

        try {
            capacity = Integer.parseInt(capacityText);

            if (capacity <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Capacity must be a positive number.",
                        "Invalid Capacity",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Capacity must be a number.",
                    "Invalid Capacity",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (ownerUsername.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Owner username cannot be empty.",
                    "Invalid Owner",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        AuthService authService = new AuthService();
        User owner = authService.getUserByUsername(ownerUsername);

        if (owner == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Owner username does not exist.",
                    "Invalid Owner",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!owner.getUserType().equals("ShipOwner")) {
            JOptionPane.showMessageDialog(
                    this,
                    "This user is not a ShipOwner.",
                    "Invalid Owner",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Ship ship = new Ship(
                shipId,
                name,
                type,
                capacity,
                owner.getId(),
                user.getId()
        );

        ShipService shipService = new ShipService();

        boolean success = shipService.registerShip(ship);

        if (success) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ship registered successfully!"
            );

            dispose();
            new CaptainDashboard(user);

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Ship ID already exists or registration failed.",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void addLabel(JPanel panel, String text) {

        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        panel.add(label);
    }

    private void styleButton(JButton button, Color color) {

        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }
}