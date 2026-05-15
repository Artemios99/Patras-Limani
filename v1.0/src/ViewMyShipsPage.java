import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewMyShipsPage extends JFrame {

    public ViewMyShipsPage(User user) {

        UIHelper.setupFrame(this, "View My Ships", 950, 500);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title = UIHelper.createTitle("My Ships");
        JLabel subtitle = UIHelper.createSubtitle(
                "View all registered ships under your ownership"
        );

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

        String[] columns = {
                "ID",
                "Ship Code",
                "Name",
                "Type",
                "Capacity",
                "Captain ID"
        };

        ShipService shipService = new ShipService();
        ArrayList<Ship> ships = shipService.getShipsByOwnerId(user.getId());

        Object[][] data = new Object[ships.size()][6];

        for (int i = 0; i < ships.size(); i++) {

            Ship ship = ships.get(i);

            data[i][0] = ship.getId();
            data[i][1] = ship.getShipCode();
            data[i][2] = ship.getName();
            data[i][3] = ship.getType();
            data[i][4] = ship.getCapacity();
            data[i][5] = ship.getCaptainId();
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
            new ShipOwnerDashboard(user);
        });

        setVisible(true);
    }
}