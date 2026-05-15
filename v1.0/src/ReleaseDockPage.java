import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReleaseDockPage extends JFrame {

    private JTable table;
    private ArrayList<Dock> dockedDocks;

    public ReleaseDockPage(User user) {

        UIHelper.setupFrame(this, "Release Dock", 1000, 550);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title = UIHelper.createTitle("Release Dock");
        JLabel subtitle = UIHelper.createSubtitle(
                "Release a dock only after the ship owner has paid all charges"
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

        dockedDocks = dockService.getDockedDocks();

        Object[][] data = new Object[dockedDocks.size()][6];

        for (int i = 0; i < dockedDocks.size(); i++) {

            Dock dock = dockedDocks.get(i);
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
        JButton releaseButton = UIHelper.createButton("Release Dock");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        bottomPanel.setBackground(UIHelper.BACKGROUND);

        bottomPanel.add(backButton);
        bottomPanel.add(releaseButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        releaseButton.addActionListener(e -> {

            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a dock.");
                return;
            }

            Dock selectedDock = dockedDocks.get(selectedRow);

            if (selectedDock.getCurrentShipId() == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "No ship is assigned to this dock.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Ship ship = shipService.getShipById(selectedDock.getCurrentShipId());

            if (ship == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Ship not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            PaymentService paymentService = new PaymentService();

            String paymentStatus = paymentService.getPaymentStatus(
                    ship.getId(),
                    ship.getOwnerId()
            );

            if (!paymentStatus.equals("paid")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Cannot release dock.\nThe ship owner has not paid the charges yet.",
                        "Payment Required",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            boolean success = dockService.releaseDock(selectedDock.getNumber());

            if (success) {
                JOptionPane.showMessageDialog(this, "Dock released successfully.");

                dispose();
                new ReleaseDockPage(user);

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Release failed.",
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