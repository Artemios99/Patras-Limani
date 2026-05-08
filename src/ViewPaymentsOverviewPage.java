import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewPaymentsOverviewPage extends JFrame {

    private User user;

    public ViewPaymentsOverviewPage(User user) {

        this.user = user;

        setTitle("View Payments Overview");
        setSize(950, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel("Payments Overview", SwingConstants.CENTER);
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
                "Payment Status"
        };

        PortEntryRequestService portEntryService = new PortEntryRequestService();
        ShipService shipService = new ShipService();
        DockService dockService = new DockService();

        ArrayList<PortEntryRequests> approvedRequests =
                portEntryService.getApprovedRequests();

        Object[][] data = new Object[approvedRequests.size()][8];

        for (int i = 0; i < approvedRequests.size(); i++) {

            PortEntryRequests request = approvedRequests.get(i);
            Ship ship = shipService.getShipById(request.getShipId());

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
            data[i][7] = "unpaid";
        }

        JTable paymentsTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(paymentsTable);

        JButton backButton = new JButton("Back");
        styleButton(backButton, buttonColor);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.add(backButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        backButton.addActionListener(e -> {
            dispose();
            new PortAuthorityDashboard(user);
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