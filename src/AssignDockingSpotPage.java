import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AssignDockingSpotPage extends JFrame {

    private JTable requestTable;

    public AssignDockingSpotPage(User user) {


        UIHelper.setupFrame(this, "Assign Docking Spot", 1000, 620);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title = UIHelper.createTitle("Assign Docking Spot");
        JLabel subtitle = UIHelper.createSubtitle(
                "Assign an available dock to an approved docking request"
        );

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

        String[] columns = {
                "Request ID",
                "Ship Code",
                "Captain Username",
                "Requested Date"
        };

        ShipService shipService = new ShipService();
        AuthService authService = new AuthService();
        DockingRequestService requestService = new DockingRequestService();

        ArrayList<DockingRequest> requests = requestService.getApprovedRequests();

        Object[][] data = new Object[requests.size()][4];

        for (int i = 0; i < requests.size(); i++) {

            DockingRequest request = requests.get(i);

            Ship ship = shipService.getShipById(request.getShipId());
            User captain = authService.getUserById(request.getCaptainId());

            data[i][0] = request.getId();
            data[i][1] = ship != null ? ship.getShipCode() : "-";
            data[i][2] = captain != null ? captain.getUsername() : "-";
            data[i][3] = request.getRequestedDate();
        }

        requestTable = new JTable(data, columns);
        JScrollPane scrollPane = UIHelper.styleTable(requestTable);

        JPanel contentPanel = UIHelper.createCardPanel(new BorderLayout(18, 18));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UIHelper.CARD);

        JLabel dockLabel = UIHelper.createLabel("Dock Number");
        JTextField dockField = UIHelper.createTextField();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        formPanel.add(dockLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        formPanel.add(dockField, gbc);

        JButton backButton = UIHelper.createBackButton();
        JButton assignButton = UIHelper.createButton("Assign Dock");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setBackground(UIHelper.CARD);

        buttonPanel.add(backButton);
        buttonPanel.add(assignButton);

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(formPanel, BorderLayout.NORTH);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);

        assignButton.addActionListener(e -> {

            int selectedRow = requestTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a request.");
                return;
            }

            String dockText = dockField.getText().trim();

            int dockNumber;

            try {
                dockNumber = Integer.parseInt(dockText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Dock number must be a number.");
                return;
            }

            int requestId = (int) requestTable.getValueAt(selectedRow, 0);

            DockingRequest selectedRequest = requestService.getRequestById(requestId);

            if (selectedRequest == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Request not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            int shipId = selectedRequest.getShipId();

            DockService dockService = new DockService();

            boolean success = dockService.assignDock(dockNumber, shipId);

            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Dock " + dockNumber + " assigned successfully!"
                );

                dispose();
                new AssignDockingSpotPage(user);

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Assignment failed. Dock may not exist, may not be available, or ship is already assigned/docked.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new PortAuthorityDashboard(user);
        });

        setVisible(true);
    }
}