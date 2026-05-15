import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewDockStatusPage extends JFrame {

    public ViewDockStatusPage(User user) {

        UIHelper.setupFrame(this, "View Dock Status", 1050, 720);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title = UIHelper.createTitle("Dock Status Overview");
        JLabel subtitle = UIHelper.createSubtitle(
                "Monitor all dock positions and current ship assignments"
        );

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

        JPanel legendPanel = UIHelper.createCardPanel(
                new FlowLayout(FlowLayout.CENTER, 30, 8)
        );

        legendPanel.add(createLegendItem(Color.WHITE, "Available"));
        legendPanel.add(createLegendItem(Color.ORANGE, "Assigned"));
        legendPanel.add(createLegendItem(Color.RED, "Docked"));

        JPanel dockGrid = UIHelper.createCardPanel(
                new GridLayout(5, 6, 15, 15)
        );

        DockService dockService = new DockService();
        ShipService shipService = new ShipService();

        ArrayList<Dock> docks = dockService.getAllDocks();

        for (Dock dock : docks) {

            JButton dockButton = createDockButton(dock);

            dockButton.addActionListener(e -> {

                if (dock.getStatus().equals("assigned")
                        || dock.getStatus().equals("docked")) {

                    Ship ship = shipService.getShipById(
                            dock.getCurrentShipId()
                    );

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
                        JOptionPane.showMessageDialog(
                                this,
                                "Ship not found.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }

                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Dock " + dock.getNumber() + " is available."
                    );
                }
            });

            dockGrid.add(dockButton);
        }

        JButton backButton = UIHelper.createBackButton();

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(UIHelper.BACKGROUND);
        bottomPanel.add(backButton);

        JPanel topPanel = new JPanel(new BorderLayout(15, 15));
        topPanel.setBackground(UIHelper.BACKGROUND);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(legendPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(dockGrid, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        backButton.addActionListener(e -> {
            dispose();
            new PortAuthorityDashboard(user);
        });

        setVisible(true);
    }

    private JButton createDockButton(Dock dock) {

        JButton button = new JButton(
                "<html><center><b>Dock " + dock.getNumber() + "</b><br>" +
                        "<span style='font-size:10px;'>" +
                        dock.getStatus().toUpperCase() +
                        "</span></center></html>"
        );

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (dock.getStatus().equals("assigned")) {
            button.setBackground(Color.ORANGE);
            button.setForeground(Color.BLACK);
        } else if (dock.getStatus().equals("docked")) {
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
        }

        return button;
    }

    private JPanel createLegendItem(Color color, String text) {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panel.setBackground(UIHelper.CARD);

        JPanel box = new JPanel();
        box.setBackground(color);
        box.setPreferredSize(new Dimension(22, 22));

        JLabel label = UIHelper.createLabel(text);

        panel.add(box);
        panel.add(label);

        return panel;
    }
}