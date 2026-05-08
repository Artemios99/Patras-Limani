import javax.swing.*;
import java.awt.*;

public class DockWorkerDashboard extends JFrame {

    public DockWorkerDashboard(User user) {

        setTitle("Dock Worker Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel(
                "Welcome Dock Worker " + user.getName(),
                SwingConstants.CENTER
        );

        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JPanel menuPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(70, 180, 70, 180));
        menuPanel.setBackground(backgroundColor);

        JButton viewAssignmentsButton = createButton("View Docking Assignments", buttonColor);
        JButton updateStatusButton = createButton("Update Docking Status", buttonColor);
        JButton releaseDockButton = createButton("Release Dock", buttonColor);

        menuPanel.add(viewAssignmentsButton);
        menuPanel.add(updateStatusButton);
        menuPanel.add(releaseDockButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(menuPanel, BorderLayout.CENTER);

        add(mainPanel);

        viewAssignmentsButton.addActionListener(e -> {
            dispose();
            new ViewDockingAssignmentsPage(user);
        });

        updateStatusButton.addActionListener(e -> {
            dispose();
            new UpdateDockingStatusPage(user);
        });

        releaseDockButton.addActionListener(e -> {
            dispose();
            new ReleaseDockPage(user);
        });

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