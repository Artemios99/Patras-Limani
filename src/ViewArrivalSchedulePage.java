import javax.swing.*;
import java.awt.*;

public class ViewArrivalSchedulePage extends JFrame {

    private User user;

    public ViewArrivalSchedulePage(User user) {

        this.user = user;

        setTitle("View Arrival Schedule");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel(
                "Arrival Schedule",
                SwingConstants.CENTER
        );

        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        String[] columns = {
                "Request ID",
                "Ship ID",
                "Captain ID",
                "Arrival Date",
                "Status"
        };

        Object[][] data = {
                {1, 1, 2, "2025-05-01", "pending"},
                {2, 3, 4, "2025-05-03", "yes"},
                {3, 5, 6, "2025-05-05", "pending"}
        };

        JTable arrivalTable = new JTable(data, columns);

        JScrollPane scrollPane = new JScrollPane(arrivalTable);

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