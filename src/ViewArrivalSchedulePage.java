import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewArrivalSchedulePage extends JFrame {

    public ViewArrivalSchedulePage(User user) {

        UIHelper.setupFrame(this, "View Arrival Schedule", 1050, 600);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title = UIHelper.createTitle("Arrival Schedule");
        JLabel subtitle = UIHelper.createSubtitle(
                "Approved arrivals and current docking state"
        );

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

        String[] columns = {
                "Ship ID",
                "Captain Username",
                "Ship Type",
                "Capacity",
                "Arrival Date",
                "Docked"
        };

        PortEntryRequestService portService = new PortEntryRequestService();
        ShipService shipService = new ShipService();
        AuthService authService = new AuthService();
        DockService dockService = new DockService();

        ArrayList<PortEntryRequests> requests =
                portService.getApprovedRequests();

        Object[][] data = new Object[requests.size()][6];

        for (int i = 0; i < requests.size(); i++) {

            PortEntryRequests request = requests.get(i);

            Ship ship = shipService.getShipById(request.getShipId());
            User captain = authService.getUserById(request.getCaptainId());

            if (ship == null || captain == null) {
                continue;
            }

            boolean docked = dockService.isShipDocked(ship.getId());

            data[i][0] = ship.getShipCode();
            data[i][1] = captain.getUsername();
            data[i][2] = ship.getType();
            data[i][3] = ship.getCapacity();
            data[i][4] = request.getArrivalDate();
            data[i][5] = docked ? "Yes" : "No";
        }

        JTable arrivalTable = new JTable(data, columns);

        JScrollPane scrollPane = UIHelper.styleTable(arrivalTable);

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
}