import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewDockingAssignmentsPage extends JFrame {

    public ViewDockingAssignmentsPage(User user) {

        UIHelper.setupFrame(this, "View Docking Assignments", 1000, 550);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title = UIHelper.createTitle("Docking Assignments");
        JLabel subtitle = UIHelper.createSubtitle(
                "View all active dock assignments and ship information"
        );

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

        String[] columns = {
                "Dock Number",
                "Ship Code",
                "Ship Name",
                "Ship Type",
                "Capacity",
                "Status"
        };

        DockService dockService = new DockService();
        ShipService shipService = new ShipService();

        ArrayList<Dock> assignedDocks = dockService.getAssignedDocks();

        Object[][] data = new Object[assignedDocks.size()][6];

        for (int i = 0; i < assignedDocks.size(); i++) {

            Dock dock = assignedDocks.get(i);
            Ship ship = shipService.getShipById(dock.getCurrentShipId());

            data[i][0] = dock.getNumber();

            if (ship != null) {
                data[i][1] = ship.getShipCode();
                data[i][2] = ship.getName();
                data[i][3] = ship.getType();
                data[i][4] = ship.getCapacity();
            } else {
                data[i][1] = "-";
                data[i][2] = "Unknown";
                data[i][3] = "-";
                data[i][4] = "-";
            }

            data[i][5] = dock.getStatus();
        }

        JTable table = new JTable(data, columns);

        JScrollPane scrollPane = UIHelper.styleTable(table);

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
            new DockWorkerDashboard(user);
        });

        setVisible(true);
    }
}