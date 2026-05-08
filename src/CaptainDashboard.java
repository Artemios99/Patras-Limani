import javax.swing.*;
import java.awt.*;

public class CaptainDashboard extends JFrame {

    public CaptainDashboard(User user) {

        setTitle("Captain Dashboard");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);
        Color logcolor = new Color(255, 1, 1);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel(
                "Welcome Captain " + user.getName(),
                SwingConstants.CENTER
        );

        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 15, 15));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 180, 50, 180));
        menuPanel.setBackground(backgroundColor);

        JButton registerShipButton =
                createButton("Register Ship", buttonColor);

        JButton requestPortEntryButton =
                createButton("Request Port Entry", buttonColor);

        JButton requestDockingButton =
                createButton("Request Docking", buttonColor);

        JButton viewShipStatusButton =
                createButton("View Ship Status", buttonColor);

        JButton logoutButton =
                createButton("Logout", logcolor);

        registerShipButton.addActionListener(e -> {
            dispose();
            new RegisterShipPage(user);
        });

        

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginPage();
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

        menuPanel.add(registerShipButton);
        menuPanel.add(requestPortEntryButton);
        menuPanel.add(requestDockingButton);
        menuPanel.add(viewShipStatusButton);
        menuPanel.add(logoutButton);

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