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

        UIHelper.setupFrame(this, "Register Ship", 800, 650);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title = UIHelper.createTitle("Register Ship");
        JLabel subtitle = UIHelper.createSubtitle(
                "Add a new ship and connect it with its owner"
        );

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

        JPanel formPanel = UIHelper.createCardPanel(new GridBagLayout());

        shipIdField = UIHelper.createTextField();
        nameField = UIHelper.createTextField();

        String[] shipTypes = {
                "Cargo",
                "Tanker",
                "Passenger"
        };

        typeBox = UIHelper.createComboBox(shipTypes);
        capacityField = UIHelper.createTextField();
        ownerUsernameField = UIHelper.createTextField();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addFormRow(formPanel, gbc, 0, "Ship ID (7 digits)", shipIdField);
        addFormRow(formPanel, gbc, 1, "Ship Name", nameField);
        addFormRow(formPanel, gbc, 2, "Ship Type", typeBox);
        addFormRow(formPanel, gbc, 3, "Capacity", capacityField);
        addFormRow(formPanel, gbc, 4, "Owner Username", ownerUsernameField);

        JButton backButton = UIHelper.createBackButton();
        JButton registerButton = UIHelper.createButton("Register Ship");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setBackground(UIHelper.CARD);
        buttonPanel.add(backButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);

        registerButton.addActionListener(e -> registerShip());

        backButton.addActionListener(e -> {
            dispose();
            new CaptainDashboard(user);
        });

        setVisible(true);
    }

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JComponent field
    ) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;

        panel.add(UIHelper.createLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 1;

        panel.add(field, gbc);
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
}