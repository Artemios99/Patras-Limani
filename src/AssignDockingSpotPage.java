import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AssignDockingSpotPage extends JFrame {

    private User user;
    private JTable requestTable;

    public AssignDockingSpotPage(User user) {

        this.user = user;

        setTitle("Assign Docking Spot");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel(
                "Assign Docking Spot",
                SwingConstants.CENTER
        );

        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        String[] columns = {
                "Request ID",
                "Ship Code",
                "Captain Username",
                "Requested Date"
        };

        ShipService shipService = new ShipService();
        AuthService authService = new AuthService();

        DockingRequestService requestService =
                new DockingRequestService();

        ArrayList<DockingRequest> requests =
                requestService.getApprovedRequests();

        Object[][] data =
                new Object[requests.size()][4];

        for (int i = 0; i < requests.size(); i++) {

            DockingRequest request =
                    requests.get(i);

            Ship ship =
                    shipService.getShipById(
                            request.getShipId()
                    );

            User captain =
                    authService.getUserById(
                            request.getCaptainId()
                    );

            data[i][0] = request.getId();
            data[i][1] = ship.getShipCode();
            data[i][2] = captain.getUsername();
            data[i][3] = request.getRequestedDate();
        }

        requestTable =
                new JTable(data, columns);

        JScrollPane scrollPane =
                new JScrollPane(requestTable);

        JPanel bottomPanel =
                new JPanel(new GridLayout(2,2,10,10));

        bottomPanel.setBackground(backgroundColor);

        JLabel dockLabel =
                new JLabel("Dock Number:");

        dockLabel.setForeground(Color.WHITE);

        JTextField dockField =
                new JTextField();

        JButton assignButton =
                new JButton("Assign Dock");

        JButton backButton =
                new JButton("Back");

        styleButton(assignButton, buttonColor);
        styleButton(backButton, buttonColor);

        bottomPanel.add(dockLabel);
        bottomPanel.add(dockField);
        bottomPanel.add(backButton);
        bottomPanel.add(assignButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        assignButton.addActionListener(e -> {

            int selectedRow =
                    requestTable.getSelectedRow();

            if (selectedRow == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a request."
                );

                return;
            }

            String dockText =
                    dockField.getText().trim();

            int dockNumber;

            try {

                dockNumber =
                        Integer.parseInt(dockText);

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Dock number must be a number."
                );

                return;
            }

            int requestId =
                    (int) requestTable.getValueAt(
                            selectedRow,
                            0
                    );

            DockingRequest selectedRequest =
                    requestService.getRequestById(
                            requestId
                    );

            if (selectedRequest == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Request not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            int shipId =
                    selectedRequest.getShipId();

            DockService dockService =
                    new DockService();

            boolean success =
                    dockService.assignDock(
                            dockNumber,
                            shipId
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Dock " + dockNumber +
                                " assigned successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Assignment failed. Dock may not exist or is already occupied.",
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