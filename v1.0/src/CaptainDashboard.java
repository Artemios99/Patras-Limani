import javax.swing.*;
import java.awt.*;

public class CaptainDashboard extends JFrame {

    public CaptainDashboard(User user) {

        UIHelper.setupFrame(this, "Captain Dashboard", 900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title = UIHelper.createTitle("Captain Dashboard");

        JLabel welcome = UIHelper.createSubtitle(
                "Welcome back, Captain " + user.getName()
        );

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(welcome, BorderLayout.SOUTH);

        JPanel cardPanel = UIHelper.createCardPanel(new GridLayout(2, 2, 22, 22));

        JButton registerShipButton = createDashboardButton(
                "Register Ship",
                "Add a new ship to the system"
        );

        JButton requestPortEntryButton = createDashboardButton(
                "Request Port Entry",
                "Submit a port entry request"
        );

        JButton requestDockingButton = createDashboardButton(
                "Request Docking",
                "Ask for a docking position"
        );

        JButton viewShipStatusButton = createDashboardButton(
                "View Ship Status",
                "Check request approvals and ship status"
        );

        cardPanel.add(registerShipButton);
        cardPanel.add(requestPortEntryButton);
        cardPanel.add(requestDockingButton);
        cardPanel.add(viewShipStatusButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(210, 55, 65));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setPreferredSize(new Dimension(130, 42));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(UIHelper.BACKGROUND);
        bottomPanel.add(logoutButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        registerShipButton.addActionListener(e -> {
            dispose();
            new RegisterShipPage(user);
        });

        requestPortEntryButton.addActionListener(e -> {
            dispose();
            new RequestPortEntryPage(user);
        });

        requestDockingButton.addActionListener(e -> {
            dispose();
            new RequestDockingPage(user);
        });

        viewShipStatusButton.addActionListener(e -> {
            dispose();
            new ViewShipStatusPage(user);
        });

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginPage();
        });

        setVisible(true);
    }

    private JButton createDashboardButton(String title, String description) {

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
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));

        return button;
    }
}