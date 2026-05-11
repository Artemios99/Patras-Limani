import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReleaseDockPage extends JFrame {

    private User user;
    private JTable table;

    public ReleaseDockPage(User user) {

        this.user = user;

        setTitle("Release Dock");
        setSize(850, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel("Release Dock", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

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

        ArrayList<Dock> dockedDocks = dockService.getDockedDocks();

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

        JButton releaseButton = new JButton("Release Selected Dock");
        JButton backButton = new JButton("Back");

        styleButton(releaseButton, buttonColor);
        styleButton(backButton, buttonColor);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        bottomPanel.setBackground(backgroundColor);

        bottomPanel.add(backButton);
        bottomPanel.add(releaseButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        releaseButton.addActionListener(e -> {

            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a dock.");
                return;
            }

            int dockNumber = (int) table.getValueAt(selectedRow, 0);

            boolean success = dockService.releaseDock(dockNumber);

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

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }
}