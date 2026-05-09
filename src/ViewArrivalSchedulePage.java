import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewArrivalSchedulePage extends JFrame {

    private User user;

    public ViewArrivalSchedulePage(User user) {

        this.user = user;

        setTitle("View Arrival Schedule");
        setSize(950, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel(
                "Arrival Schedule",
                SwingConstants.CENTER
        );

        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        String[] columns = {
                "Ship ID",
                "Captain Username",
                "Ship Type",
                "Capacity",
                "Arrival Date",
                "Docked"
        };

        PortEntryRequestService portService =
                new PortEntryRequestService();

        ShipService shipService =
                new ShipService();

        AuthService authService =
                new AuthService();

        DockService dockService =
                new DockService();

        ArrayList<PortEntryRequests> requests =
                portService.getApprovedRequests();

        Object[][] data =
                new Object[requests.size()][6];

        for (int i = 0; i < requests.size(); i++) {

            PortEntryRequests request =
                    requests.get(i);

            Ship ship =
                    shipService.getShipById(
                            request.getShipId()
                    );

            User captain =
                    authService.getUserById(
                            request.getCaptainId()
                    );

            boolean docked =
                    dockService.isShipDocked(
                            ship.getId()
                    );

            data[i][0] = ship.getShipCode();
            data[i][1] = captain.getUsername();
            data[i][2] = ship.getType();
            data[i][3] = ship.getCapacity();
            data[i][4] = request.getArrivalDate();
            data[i][5] = docked ? "Yes" : "No";
        }

        JTable arrivalTable =
                new JTable(data, columns);

        JScrollPane scrollPane =
                new JScrollPane(arrivalTable);

        JButton backButton =
                new JButton("Back");

        styleButton(backButton, buttonColor);

        JPanel bottomPanel =
                new JPanel();

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

    private void styleButton(JButton button,
                             Color color) {

        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(
                new Font("Arial",
                        Font.BOLD,
                        14)
        );
    }
}