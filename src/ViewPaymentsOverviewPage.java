import javax.swing.*;
import java.awt.*;

public class ViewPaymentsOverviewPage extends JFrame {

    private User user;

    public ViewPaymentsOverviewPage(User user) {

        this.user = user;

        setTitle("View Payments Overview");
        setSize(750, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel(
                "Payments Overview",
                SwingConstants.CENTER
        );

        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        String[] columns = {
                "Payment ID",
                "Ship ID",
                "Owner ID",
                "Amount",
                "Description",
                "Status"
        };

        Object[][] data = {
                {1, 1, 2, 1500.0, "Docking Fee", "pending"},
                {2, 3, 4, 3000.0, "Port Entry Fee", "paid"},
                {3, 5, 6, 2000.0, "Dock Usage", "pending"}
        };

        JTable paymentsTable = new JTable(data, columns);

        JScrollPane scrollPane = new JScrollPane(paymentsTable);

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