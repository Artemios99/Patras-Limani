import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewAndPayChargesPage extends JFrame {

    private User user;
    private JTable table;

    public ViewAndPayChargesPage(User user) {

        this.user = user;

        setTitle("View And Pay Charges");
        setSize(950, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel("My Charges", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        String[] columns = {
                "Ship ID",
                "Ship Code",
                "Ship Name",
                "Type",
                "Port Fee (€)",
                "Dock Fee (€)",
                "Total (€)",
                "Status"
        };

        ShipService shipService = new ShipService();
        DockService dockService = new DockService();
        PaymentService paymentService = new PaymentService();
        PortEntryRequestService portEntryService = new PortEntryRequestService();

        ArrayList<Ship> allShips = shipService.getShipsByOwnerId(user.getId());
        ArrayList<Ship> ships = new ArrayList<>();

        for (Ship ship : allShips) {
            if (portEntryService.hasApprovedPortEntry(ship.getId())) {
                ships.add(ship);
            }
        }

        Object[][] data = new Object[ships.size()][8];

        for (int i = 0; i < ships.size(); i++) {

            Ship ship = ships.get(i);

            double portFee = calculatePortFee(ship.getType());
            double dockFee = dockService.isShipDocked(ship.getId()) ? 100 : 0;
            double total = portFee + dockFee;

            data[i][0] = ship.getId();
            data[i][1] = ship.getShipCode();
            data[i][2] = ship.getName();
            data[i][3] = ship.getType();
            data[i][4] = portFee;
            data[i][5] = dockFee;
            data[i][6] = total;
            data[i][7] = paymentService.getPaymentStatus(ship.getId(), user.getId());
        }

        table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton payButton = new JButton("PAY");
        JButton backButton = new JButton("Back");

        styleButton(payButton, buttonColor);
        styleButton(backButton, buttonColor);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        bottomPanel.setBackground(backgroundColor);

        bottomPanel.add(backButton);
        bottomPanel.add(payButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        payButton.addActionListener(e -> {

            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a charge.");
                return;
            }

            String status = table.getValueAt(selectedRow, 7).toString();

            if (status.equals("paid")) {
                JOptionPane.showMessageDialog(this, "This charge is already paid.");
                return;
            }

            int shipId = (int) table.getValueAt(selectedRow, 0);
            double total = (double) table.getValueAt(selectedRow, 6);

            boolean success = paymentService.payOrCreatePayment(
                    shipId,
                    user.getId(),
                    total,
                    "Port entry and dock fee"
            );

            if (success) {
                table.setValueAt("paid", selectedRow, 7);

                JOptionPane.showMessageDialog(
                        this,
                        "Payment completed successfully!"
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Payment failed.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new ShipOwnerDashboard(user);
        });

        setVisible(true);
    }

    private double calculatePortFee(String type) {
        if (type.equals("Cargo")) {
            return 200;
        } else if (type.equals("Tanker")) {
            return 300;
        } else if (type.equals("Passenger")) {
            return 450;
        }

        return 0;
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }
}