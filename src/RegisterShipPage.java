// RegisterShipPage.java

import javax.swing.*;
import java.awt.*;

public class RegisterShipPage extends JFrame {

    private JTextField nameField;
    private JComboBox<String> typeBox;
    private JTextField capacityField;

    private User user;

    public RegisterShipPage(User user) {

        this.user = user;

        setTitle("Register Ship");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        panel.setBackground(backgroundColor);

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

    private void registerShip() {

        String name = nameField.getText();

        String type = (String) typeBox.getSelectedItem();

        int capacity = Integer.parseInt(
                capacityField.getText()
        );

        JOptionPane.showMessageDialog(
                this,
                "Ship Registered Successfully!\n"
                        + "Ship Name: " + name + "\n"
                        + "Type: " + type + "\n"
                        + "Capacity: " + capacity
        );

        dispose();

        new CaptainDashboard(user);
    }
}