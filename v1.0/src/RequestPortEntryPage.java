import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RequestPortEntryPage extends JFrame {

    private User user;

    private JComboBox<Ship> shipBox;
    private JDateChooser arrivalDateChooser;

    public RequestPortEntryPage(User user) {

        this.user = user;

        UIHelper.setupFrame(
                this,
                "Request Port Entry",
                850,
                600
        );

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title =
                UIHelper.createTitle("Port Entry Request");

        JLabel subtitle =
                UIHelper.createSubtitle(
                        "Submit a request for ship arrival"
                );

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

        JPanel cardPanel =
                UIHelper.createCardPanel(
                        new BorderLayout(20, 20)
                );

        // LEFT INFO PANEL
        JPanel infoPanel =
                new JPanel(new GridLayout(3, 1, 15, 15));

        infoPanel.setBackground(UIHelper.CARD);

        infoPanel.add(createInfoCard(
                "Arrival",
                "Choose the planned arrival date"
        ));

        infoPanel.add(createInfoCard(
                "Approval",
                "Wait for Port Authority approval"
        ));

        infoPanel.add(createInfoCard(
                "Docking",
                "Then request a docking spot"
        ));

        // FORM PANEL
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
                new Dimension(250, 45)
        );

        // DATE CHOOSER
        arrivalDateChooser = new JDateChooser();

        arrivalDateChooser.setDateFormatString(
                "yyyy-MM-dd"
        );

        arrivalDateChooser.setPreferredSize(
                new Dimension(320, 50)
        );

        arrivalDateChooser.setFont(
                new Font("Segoe UI", Font.PLAIN, 16)
        );

        arrivalDateChooser.getDateEditor().getUiComponent().setFont(
                new Font("Segoe UI", Font.PLAIN, 16)
        );
        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(15, 15, 15, 15);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

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
                UIHelper.createLabel("Arrival Date"),
                gbc
        );

        gbc.gridx = 1;

        formPanel.add(arrivalDateChooser, gbc);

        // BUTTONS
        JButton backButton =
                UIHelper.createBackButton();

        JButton requestButton =
                UIHelper.createButton(
                        "Submit Request"
                );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                15,
                                0
                        )
                );

        buttonPanel.setBackground(UIHelper.CARD);

        buttonPanel.add(backButton);
        buttonPanel.add(requestButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        formPanel.add(buttonPanel, gbc);

        cardPanel.add(infoPanel, BorderLayout.WEST);
        cardPanel.add(formPanel, BorderLayout.CENTER);

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

        if (arrivalDateChooser.getDate() == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an arrival date.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd");

        String arrivalDate =
                sdf.format(arrivalDateChooser.getDate());

        PortEntryRequests request =
                new PortEntryRequests(
                        selectedShip.getId(),
                        user.getId(),
                        arrivalDate,
                        "pending"
                );

        PortEntryRequestService service =
                new PortEntryRequestService();

        boolean success =
                service.createRequest(request);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Port entry request submitted successfully!"
            );

            dispose();

            new CaptainDashboard(user);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Request failed or already exists.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}