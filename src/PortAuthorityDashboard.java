

import javax.swing.*;
import java.awt.*;

public class PortAuthorityDashboard extends JFrame {

    public PortAuthorityDashboard(User user) {

        setTitle("Port Authority Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel(
                "Welcome Port Authority " + user.getName(),
                SwingConstants.CENTER
        );

        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 15, 15));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(40, 180, 40, 180));
        menuPanel.setBackground(backgroundColor);

        JButton manageEntryRequestsButton = createButton("Manage Entry Requests", buttonColor);
        JButton assignDockingSpotButton = createButton("Assign Docking Spot", buttonColor);
        JButton viewDockStatusButton = createButton("View Dock Status", buttonColor);
        JButton viewArrivalScheduleButton = createButton("View Arrival Schedule", buttonColor);
        JButton viewPaymentsOverviewButton = createButton("View Payments Overview", buttonColor);

        menuPanel.add(manageEntryRequestsButton);
        menuPanel.add(assignDockingSpotButton);
        menuPanel.add(viewDockStatusButton);
        menuPanel.add(viewArrivalScheduleButton);
        menuPanel.add(viewPaymentsOverviewButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(menuPanel, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
    }

    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));

        return button;
    }
}