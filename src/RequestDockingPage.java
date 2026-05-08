import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RequestDockingPage extends JFrame {

    private User user;
    private JComboBox<Ship> shipBox;
    private JTextField requestedDateField;

    public RequestDockingPage(User user) {

        this.user = user;

        setTitle("Request Docking");
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(backgroundColor);

        ShipService shipService = new ShipService();
        ArrayList<Ship> ships = shipService.getShipsByCaptainId(user.getId());

        shipBox = new JComboBox<>();

        for (Ship ship : ships) {
            shipBox.addItem(ship);
        }

        requestedDateField = new JTextField();

        JButton backButton = new JButton("Back");
        JButton requestButton = new JButton("Request Docking");

        styleButton(backButton, buttonColor);
        styleButton(requestButton, buttonColor);

        addLabel(panel, "Select Ship:");
        panel.add(shipBox);

        addLabel(panel, "Requested Date:");
        panel.add(requestedDateField);

        panel.add(backButton);
        panel.add(requestButton);

        add(panel);

        requestButton.addActionListener(e -> submitRequest());

        backButton.addActionListener(e -> {
            dispose();
            new CaptainDashboard(user);
        });

        setVisible(true);
    }

    private void submitRequest() {

        Ship selectedShip = (Ship) shipBox.getSelectedItem();
        String requestedDate = requestedDateField.getText().trim();

        if (selectedShip == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No ship found for this captain.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (requestedDate.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Requested date cannot be empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        DockingRequest request = new DockingRequest(
                selectedShip.getId(),
                user.getId(),
                requestedDate,
                "pending"
        );

        DockingRequestService service = new DockingRequestService();

        boolean success = service.createRequest(request);

        if (success) {
            JOptionPane.showMessageDialog(
                    this,
                    "Docking request submitted successfully!"
            );

            dispose();
            new CaptainDashboard(user);

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Docking request failed.",
                    "Error",
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