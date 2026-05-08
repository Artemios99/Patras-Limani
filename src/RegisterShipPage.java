import javax.swing.*;
import java.awt.*;

public class RegisterShipPage extends JFrame {

    private JTextField shipIdField;
    private JTextField nameField;
    private JComboBox<String> typeBox;
    private JTextField capacityField;

    private User user;

    public RegisterShipPage(User user) {

        this.user = user;

        setTitle("Register Ship");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
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
                    "Ship name cannot be empty."
            );
            return;
        }

        int capacity;

        try {
            capacity = Integer.parseInt(capacityText);

            if (capacity <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Capacity must be a positive number."
                );
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Capacity must be a number."
            );
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Ship Registered Successfully!\n"
                        + "Ship ID: " + shipId + "\n"
                        + "Ship Name: " + name + "\n"
                        + "Type: " + type + "\n"
                        + "Capacity: " + capacity
        );

        dispose();
        new CaptainDashboard(user);
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