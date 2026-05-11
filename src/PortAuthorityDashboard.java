import javax.swing.*;
import java.awt.*;

public class PortAuthorityDashboard extends JFrame {

    public PortAuthorityDashboard(User user) {

        UIHelper.setupFrame(this, "Port Authority Dashboard", 1100, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title =
                UIHelper.createTitle("Port Authority Dashboard");

        JLabel welcome =
                UIHelper.createSubtitle(
                        "Welcome back, " + user.getName()
                );

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(welcome, BorderLayout.SOUTH);

        JPanel cardPanel =
                UIHelper.createCardPanel(
                        new GridLayout(2, 3, 22, 22)
                );

        JButton manageEntryRequestsButton =
                createDashboardButton(
                        "Manage Requests",
                        "Approve or reject entry and docking requests"
                );

        JButton assignDockingSpotButton =
                createDashboardButton(
                        "Assign Dock",
                        "Assign available docks to approved ships"
                );

        JButton viewDockStatusButton =
                createDashboardButton(
                        "Dock Status",
                        "View all dock positions and ship assignments"
                );

        JButton viewArrivalScheduleButton =
                createDashboardButton(
                        "Arrival Schedule",
                        "Monitor approved arrivals and docking status"
                );

        JButton viewPaymentsOverviewButton =
                createDashboardButton(
                        "Payments Overview",
                        "Track all ship payments and unpaid fees"
                );

        JButton systemOverviewButton =
                createDashboardButton(
                        "System Overview",
                        "Monitor overall port activity"
                );

        cardPanel.add(manageEntryRequestsButton);
        cardPanel.add(assignDockingSpotButton);
        cardPanel.add(viewDockStatusButton);
        cardPanel.add(viewArrivalScheduleButton);
        cardPanel.add(viewPaymentsOverviewButton);
        cardPanel.add(systemOverviewButton);

        JButton logoutButton = new JButton("Logout");

        logoutButton.setBackground(new Color(210, 55, 65));
        logoutButton.setForeground(Color.WHITE);

        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);

        logoutButton.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        logoutButton.setPreferredSize(
                new Dimension(130, 42)
        );

        JPanel bottomPanel =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

        bottomPanel.setBackground(UIHelper.BACKGROUND);

        bottomPanel.add(logoutButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        manageEntryRequestsButton.addActionListener(e -> {
            dispose();
            new ManageEntryRequestsPage(user);
        });

        assignDockingSpotButton.addActionListener(e -> {
            dispose();
            new AssignDockingSpotPage(user);
        });

        viewDockStatusButton.addActionListener(e -> {
            dispose();
            new ViewDockStatusPage(user);
        });

        viewArrivalScheduleButton.addActionListener(e -> {
            dispose();
            new ViewArrivalSchedulePage(user);
        });

        viewPaymentsOverviewButton.addActionListener(e -> {
            dispose();
            new ViewPaymentsOverviewPage(user);
        });

        systemOverviewButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "System overview feature coming soon."
            );
        });

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginPage();
        });

        setVisible(true);
    }

    private JButton createDashboardButton(
            String title,
            String description
    ) {

        JButton button = new JButton(
                "<html><center><b style='font-size:16px;'>"
                        + title +
                        "</b><br><span style='font-size:11px;'>"
                        + description +
                        "</span></center></html>"
        );

        button.setBackground(UIHelper.PRIMARY);
        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setFont(
                new Font("Segoe UI", Font.BOLD, 15)
        );

        return button;
    }
}