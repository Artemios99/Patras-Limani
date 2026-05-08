import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RequestPortEntryPage extends JFrame {

    private User user;
    private JComboBox<Ship> shipBox;
    private JTextField arrivalDateField;

    public RequestPortEntryPage(User user) {

        this.user = user;

        setTitle("Request Port Entry");
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

        arrivalDateField = new JTextField();

        JButton backButton = new JButton("Back");
        JButton requestButton = new JButton("Request Entry");

        styleButton(backButton, buttonColor);
        styleButton(requestButton, buttonColor);

        addLabel(panel, "Select Ship:");
        panel.add(shipBox);

        addLabel(panel, "Arrival Date:");
        panel.add(arrivalDateField);

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
        String arrivalDate = arrivalDateField.getText().trim();

        if (selectedShip == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No ship found for this captain.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (arrivalDate.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Arrival date cannot be empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        PortEntryRequests request = new PortEntryRequests(
                selectedShip.getId(),
                user.getId(),
                arrivalDate,
                "pending"
        );

        PortEntryRequestService service = new PortEntryRequestService();

        boolean success = service.createRequest(request);

        if (success) {
            JOptionPane.showMessageDialog(
                    this,
                    "Port entry request submitted successfully!"
            );

            dispose();
            new CaptainDashboard(user);

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Request failed.",
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