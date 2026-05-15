import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UpdateDockingStatusPage extends JFrame {

    private JTable table;

    public UpdateDockingStatusPage(User user) {

        UIHelper.setupFrame(this, "Update Docking Status", 1000, 550);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title = UIHelper.createTitle("Update Docking Status");
        JLabel subtitle = UIHelper.createSubtitle(
                "Select an assigned dock and mark its ship as docked"
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

        table = new JTable(data, columns);

        JScrollPane scrollPane = UIHelper.styleTable(table);

        JPanel contentPanel = UIHelper.createCardPanel(new BorderLayout());
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = UIHelper.createBackButton();
        JButton markDockedButton = UIHelper.createButton("Mark As Docked");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        bottomPanel.setBackground(UIHelper.BACKGROUND);

        bottomPanel.add(backButton);
        bottomPanel.add(markDockedButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        markDockedButton.addActionListener(e -> {

            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an assigned dock.");
                return;
            }

            int dockNumber = (int) table.getValueAt(selectedRow, 0);

            boolean success = dockService.markAsDocked(dockNumber);

            if (success) {
                JOptionPane.showMessageDialog(this, "Dock marked as docked.");

                dispose();
                new UpdateDockingStatusPage(user);

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Update failed.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new DockWorkerDashboard(user);
        });

        setVisible(true);
    }
}