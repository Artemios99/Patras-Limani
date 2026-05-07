import javax.swing.*;
import java.awt.*;

public class ViewDockStatusPage extends JFrame {

    private User user;

    public ViewDockStatusPage(User user) {

        this.user = user;

        setTitle("View Dock Status");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel("Dock Status", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        String[] columns = {
                "Dock ID",
                "Dock Number",
                "Status",
                "Current Ship ID"
        };

        Object[][] data = {
                {1, 1, "available", null},
                {2, 2, "occupied", 1},
                {3, 3, "available", null}
        };

        JTable dockTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(dockTable);

        JButton backButton = new JButton("Back");
        styleButton(backButton, buttonColor);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.add(backButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        backButton.addActionListener(e -> {
            dispose();
            new PortAuthorityDashboard(user);
        });

        setVisible(true);
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }
}