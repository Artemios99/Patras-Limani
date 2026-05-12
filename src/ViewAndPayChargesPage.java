import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewAndPayChargesPage extends JFrame {

    private JTable table;

    public ViewAndPayChargesPage(User user) {

        UIHelper.setupFrame(this, "View And Pay Charges", 1050, 600);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title = UIHelper.createTitle("My Charges");
        JLabel subtitle = UIHelper.createSubtitle(
                "View approved charges and complete pending payments"
        );

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

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

        JScrollPane scrollPane = UIHelper.styleTable(table);

        JPanel contentPanel = UIHelper.createCardPanel(new BorderLayout());
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = UIHelper.createBackButton();
        JButton payButton = UIHelper.createButton("PAY");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        bottomPanel.setBackground(UIHelper.BACKGROUND);
        bottomPanel.add(backButton);
        bottomPanel.add(payButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
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
}