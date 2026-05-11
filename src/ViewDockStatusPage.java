import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewDockStatusPage extends JFrame {

    private User user;

    public ViewDockStatusPage(User user) {

        this.user = user;

        setTitle("View Dock Status");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Dock Status Overview", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 26));

        JPanel legendPanel = new JPanel();
        legendPanel.setBackground(backgroundColor);

        JPanel availableBox = new JPanel();
        availableBox.setBackground(Color.WHITE);
        availableBox.setPreferredSize(new Dimension(20, 20));

        JLabel availableLabel = new JLabel("Available");
        availableLabel.setForeground(Color.WHITE);

        JPanel assignedBox = new JPanel();
        assignedBox.setBackground(Color.ORANGE);
        assignedBox.setPreferredSize(new Dimension(20, 20));

        JLabel assignedLabel = new JLabel("Assigned");
        assignedLabel.setForeground(Color.WHITE);

        JPanel dockedBox = new JPanel();
        dockedBox.setBackground(Color.RED);
        dockedBox.setPreferredSize(new Dimension(20, 20));

        JLabel dockedLabel = new JLabel("Docked");
        dockedLabel.setForeground(Color.WHITE);

        legendPanel.add(availableBox);
        legendPanel.add(availableLabel);

        legendPanel.add(Box.createHorizontalStrut(25));

        legendPanel.add(assignedBox);
        legendPanel.add(assignedLabel);

        legendPanel.add(Box.createHorizontalStrut(25));

        legendPanel.add(dockedBox);
        legendPanel.add(dockedLabel);

        JPanel dockGrid = new JPanel(new GridLayout(5, 6, 15, 15));
        dockGrid.setBackground(backgroundColor);

        DockService dockService = new DockService();
        ShipService shipService = new ShipService();

        ArrayList<Dock> docks = dockService.getAllDocks();

        for (Dock dock : docks) {

            JButton dockButton = new JButton("Dock " + dock.getNumber());
            dockButton.setFont(new Font("Arial", Font.BOLD, 14));

            if (dock.getStatus().equals("assigned")) {
                dockButton.setBackground(Color.ORANGE);
                dockButton.setForeground(Color.BLACK);
            } else if (dock.getStatus().equals("docked")) {
                dockButton.setBackground(Color.RED);
                dockButton.setForeground(Color.WHITE);
            } else {
                dockButton.setBackground(Color.WHITE);
                dockButton.setForeground(Color.BLACK);
            }

            dockButton.addActionListener(e -> {

                if (dock.getStatus().equals("assigned") || dock.getStatus().equals("docked")) {

                    Ship ship = shipService.getShipById(dock.getCurrentShipId());

                    if (ship != null) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Dock Number: " + dock.getNumber() + "\n" +
                                        "Status: " + dock.getStatus() + "\n\n" +
                                        "Ship Code: " + ship.getShipCode() + "\n" +
                                        "Ship Name: " + ship.getName() + "\n" +
                                        "Type: " + ship.getType() + "\n" +
                                        "Capacity: " + ship.getCapacity()
                        );
                    } else {
                        JOptionPane.showMessageDialog(this, "Ship not found.");
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Dock is available.");
                }
            });

            dockGrid.add(dockButton);
        }

        JButton backButton = new JButton("Back");
        styleButton(backButton, buttonColor);

        backButton.addActionListener(e -> {
            dispose();
            new PortAuthorityDashboard(user);
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.add(backButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(legendPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(dockGrid, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }
}