import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RequestDockingPage extends JFrame {

    private User user;

    private JComboBox<Ship> shipBox;
    private JDateChooser requestedDateChooser;

    public RequestDockingPage(User user) {

        this.user = user;

        UIHelper.setupFrame(
                this,
                "Request Docking",
                900,
                650
        );

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title =
                UIHelper.createTitle("Docking Request");

        JLabel subtitle =
                UIHelper.createSubtitle(
                        "Request a docking position for your ship"
                );

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

        JPanel cardPanel =
                UIHelper.createCardPanel(
                        new BorderLayout(25, 25)
                );

        JPanel infoPanel =
                new JPanel(new GridLayout(3, 1, 15, 15));

        infoPanel.setBackground(UIHelper.CARD);

        infoPanel.add(createInfoCard(
                "Dock Request",
                "Select the ship that needs docking"
        ));

        infoPanel.add(createInfoCard(
                "Port Authority",
                "Wait for approval before assignment"
        ));

        infoPanel.add(createInfoCard(
                "Dock Worker",
                "Dock worker confirms the ship is docked"
        ));

        JPanel formPanel =
                new JPanel(new GridBagLayout());

        formPanel.setBackground(UIHelper.CARD);

        ShipService shipService =
                new ShipService();

        ArrayList<Ship> ships =
                shipService.getShipsByCaptainId(
                        user.getId()
                );

        shipBox = new JComboBox<>();

        for (Ship ship : ships) {
            shipBox.addItem(ship);
        }

        shipBox.setPreferredSize(
                new Dimension(320, 45)
        );

        shipBox.setFont(UIHelper.TABLE_FONT);

        requestedDateChooser = new JDateChooser();

        requestedDateChooser.setDateFormatString(
                "yyyy-MM-dd"
        );

        requestedDateChooser.setPreferredSize(
                new Dimension(320, 45)
        );

        requestedDateChooser.setFont(
                UIHelper.TABLE_FONT
        );

        requestedDateChooser
                .getDateEditor()
                .getUiComponent()
                .setFont(UIHelper.TABLE_FONT);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(18, 18, 18, 18);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(
                UIHelper.createLabel("Select Ship"),
                gbc
        );

        gbc.gridx = 1;

        formPanel.add(shipBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(
                UIHelper.createLabel("Requested Date"),
                gbc
        );

        gbc.gridx = 1;

        formPanel.add(requestedDateChooser, gbc);

        JButton backButton =
                UIHelper.createBackButton();

        JButton requestButton =
                UIHelper.createButton(
                        "Submit Request"
                );

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                15,
                                0
                        )
                );

        bottomPanel.setBackground(UIHelper.CARD);

        bottomPanel.add(backButton);
        bottomPanel.add(requestButton);

        cardPanel.add(infoPanel, BorderLayout.WEST);
        cardPanel.add(formPanel, BorderLayout.CENTER);
        cardPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        add(mainPanel);

        requestButton.addActionListener(e -> submitRequest());

        backButton.addActionListener(e -> {
            dispose();
            new CaptainDashboard(user);
        });

        setVisible(true);
    }

    private JPanel createInfoCard(
            String title,
            String description
    ) {

        JPanel panel =
                new JPanel(new BorderLayout());

        panel.setBackground(
                new Color(25, 50, 80)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(Color.WHITE);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        JLabel descLabel =
                new JLabel(
                        "<html>"
                                + description
                                + "</html>"
                );

        descLabel.setForeground(
                new Color(210, 210, 210)
        );

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(descLabel, BorderLayout.CENTER);

        return panel;
    }

    private void submitRequest() {

        Ship selectedShip =
                (Ship) shipBox.getSelectedItem();

        if (selectedShip == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "No ship found for this captain.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (requestedDateChooser.getDate() == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a requested date.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd");

        String requestedDate =
                sdf.format(
                        requestedDateChooser.getDate()
                );

        DockingRequest request =
                new DockingRequest(
                        selectedShip.getId(),
                        user.getId(),
                        requestedDate,
                        "pending"
                );

        DockingRequestService service =
                new DockingRequestService();

        boolean success =
                service.createRequest(request);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Docking request submitted successfully!"
            );

            dispose();

            new CaptainDashboard(user);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Docking request failed or already exists.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}