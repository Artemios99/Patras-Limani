import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewPaymentsOverviewPage extends JFrame {

    public ViewPaymentsOverviewPage(User user) {

        UIHelper.setupFrame(this, "View Payments Overview", 1050, 600);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title = UIHelper.createTitle("Payments Overview");
        JLabel subtitle = UIHelper.createSubtitle(
                "Approved ships, port fees, dock fees and payment status"
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
                "Payment Status"
        };

        PortEntryRequestService portEntryService = new PortEntryRequestService();
        ShipService shipService = new ShipService();
        DockService dockService = new DockService();
        PaymentService paymentService = new PaymentService();

        ArrayList<PortEntryRequests> approvedRequests =
                portEntryService.getApprovedRequests();

        Object[][] data = new Object[approvedRequests.size()][8];

        for (int i = 0; i < approvedRequests.size(); i++) {

            PortEntryRequests request = approvedRequests.get(i);
            Ship ship = shipService.getShipById(request.getShipId());

            if (ship == null) {
                continue;
            }

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
            data[i][7] = paymentService.getPaymentStatus(
                    ship.getId(),
                    ship.getOwnerId()
            );
        }

        JTable paymentsTable = new JTable(data, columns);

        JScrollPane scrollPane = UIHelper.styleTable(paymentsTable);

        JPanel contentPanel = UIHelper.createCardPanel(new BorderLayout());
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = UIHelper.createBackButton();

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(UIHelper.BACKGROUND);
        bottomPanel.add(backButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
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
}