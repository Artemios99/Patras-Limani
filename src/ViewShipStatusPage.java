import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewShipStatusPage extends JFrame {

    private User user;

    public ViewShipStatusPage(User user) {

        this.user = user;

        setTitle("View Ship Status");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel("Ship Status", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        String[] columns = {
                "Ship Code",
                "Ship Name",
                "Type",
                "Port Entry Status",
                "Docking Status"
        };

        ShipService shipService = new ShipService();

        ArrayList<Ship> ships =
                shipService.getShipsByCaptainId(user.getId());

        Object[][] data = new Object[ships.size()][5];

        for (int i = 0; i < ships.size(); i++) {

            Ship ship = ships.get(i);
            
            data[i][0] = ship.getShipCode();
            data[i][1] = ship.getName();
            data[i][2] = ship.getType();

            data[i][3] =
                    shipService.getLatestPortEntryStatus(ship.getId());

            data[i][4] =
                    shipService.getLatestDockingStatus(ship.getId());
        }

        JTable table = new JTable(data, columns);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton backButton = new JButton("Back");

        styleButton(backButton, buttonColor);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);

        bottomPanel.add(backButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        backButton.addActionListener(e -> {

            dispose();

            new CaptainDashboard(user);
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