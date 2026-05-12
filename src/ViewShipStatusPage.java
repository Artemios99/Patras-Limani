import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewShipStatusPage extends JFrame {


    public ViewShipStatusPage(User user) {


        UIHelper.setupFrame(this, "View Ship Status", 950, 550);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title = UIHelper.createTitle("Ship Status");
        JLabel subtitle = UIHelper.createSubtitle(
                "Track port entry and docking request statuses"
        );

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

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
            data[i][3] = shipService.getLatestPortEntryStatus(ship.getId());
            data[i][4] = shipService.getLatestDockingStatus(ship.getId());
        }

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = UIHelper.styleTable(table);

        JButton backButton = UIHelper.createBackButton();

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(UIHelper.BACKGROUND);
        bottomPanel.add(backButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        backButton.addActionListener(e -> {
            dispose();
            new CaptainDashboard(user);
        });

        setVisible(true);
    }
}