import javax.swing.*;
import java.awt.*;

public class ShipOwnerDashboard extends JFrame {

    public ShipOwnerDashboard(User user) {

        setTitle("Ship Owner Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel(
                "Welcome Ship Owner " + user.getName(),
                SwingConstants.CENTER
        );

        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JPanel menuPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 180, 50, 180));
        menuPanel.setBackground(backgroundColor);

        JButton fleetButton = createButton("Fleet Overview", buttonColor);
        JButton statusButton = createButton("Ship Status", buttonColor);
        JButton maintenanceButton = createButton("Maintenance", buttonColor);
        JButton costsButton = createButton("Costs", buttonColor);

        menuPanel.add(fleetButton);
        menuPanel.add(statusButton);
        menuPanel.add(maintenanceButton);
        menuPanel.add(costsButton);

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