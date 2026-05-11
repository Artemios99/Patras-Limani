import javax.swing.*;
import java.awt.*;

public class ShipOwnerDashboard extends JFrame {

    public ShipOwnerDashboard(User user) {

        UIHelper.setupFrame(this, "Ship Owner Dashboard", 900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title = UIHelper.createTitle("Ship Owner Dashboard");

        JLabel welcome = UIHelper.createSubtitle(
                "Welcome back, " + user.getName()
        );

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(welcome, BorderLayout.SOUTH);

        JPanel cardPanel = UIHelper.createCardPanel(
                new GridLayout(1, 2, 25, 25)
        );

        JButton viewShipsButton = createDashboardButton(
                "My Ships",
                "View all ships registered under your ownership"
        );

        JButton chargesButton = createDashboardButton(
                "Charges & Payments",
                "View and pay port entry and dock fees"
        );

        cardPanel.add(viewShipsButton);
        cardPanel.add(chargesButton);

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

        viewShipsButton.addActionListener(e -> {
            dispose();
            new ViewMyShipsPage(user);
        });

        chargesButton.addActionListener(e -> {
            dispose();
            new ViewAndPayChargesPage(user);
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