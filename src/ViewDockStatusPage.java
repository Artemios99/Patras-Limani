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

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel title = new JLabel(
                "Dock Status Overview",
                SwingConstants.CENTER
        );

        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 26));

        // Legend
        JPanel legendPanel = new JPanel();

        legendPanel.setBackground(backgroundColor);

        JLabel availableLabel = new JLabel("Available");
        availableLabel.setForeground(Color.WHITE);

        JPanel availableBox = new JPanel();
        availableBox.setBackground(Color.WHITE);
        availableBox.setPreferredSize(new Dimension(20,20));

        JLabel occupiedLabel = new JLabel("Occupied");
        occupiedLabel.setForeground(Color.WHITE);

        JPanel occupiedBox = new JPanel();
        occupiedBox.setBackground(Color.RED);
        occupiedBox.setPreferredSize(new Dimension(20,20));

        legendPanel.add(availableBox);
        legendPanel.add(availableLabel);

        legendPanel.add(Box.createHorizontalStrut(30));

        legendPanel.add(occupiedBox);
        legendPanel.add(occupiedLabel);

        // Dock Grid
        JPanel dockGrid = new JPanel(new GridLayout(5,6,15,15));
        dockGrid.setBackground(backgroundColor);

        DockService dockService = new DockService();
        ShipService shipService = new ShipService();

        ArrayList<Dock> docks = dockService.getAllDocks();

        for (Dock dock : docks) {

            JButton dockButton = new JButton(
                    "Dock " + dock.getNumber()
            );

            dockButton.setFont(new Font("Arial", Font.BOLD, 14));

            if (dock.getStatus().equals("occupied")) {

                dockButton.setBackground(Color.RED);
                dockButton.setForeground(Color.WHITE);

            } else {

                dockButton.setBackground(Color.WHITE);
                dockButton.setForeground(Color.BLACK);
            }

            dockButton.addActionListener(e -> {

                if (dock.getStatus().equals("occupied")) {

                    Ship ship = shipService.getShipById(
                            dock.getCurrentShipId()
                    );

                    if (ship != null) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Dock Number: " + dock.getNumber() + "\n\n" +
                                "Ship Code: " + ship.getShipCode() + "\n" +
                                "Ship Name: " + ship.getName() + "\n" +
                                "Type: " + ship.getType() + "\n" +
                                "Capacity: " + ship.getCapacity()
                        );

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Ship not found."
                        );
                    }

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Dock is available."
                    );
                }
            });

            dockGrid.add(dockButton);
        }

        JButton backButton = new JButton("Back");

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
}