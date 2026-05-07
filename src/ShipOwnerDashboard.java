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

        JPanel menuPanel = new JPanel(new GridLayout(2, 1, 15, 15));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(100, 180, 100, 180));
        menuPanel.setBackground(backgroundColor);

        JButton viewShipsButton = createButton("View My Ships", buttonColor);
        JButton chargesButton = createButton("View And Pay Taxes", buttonColor);

        menuPanel.add(viewShipsButton);
        menuPanel.add(chargesButton);
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(menuPanel, BorderLayout.CENTER);

        add(mainPanel);

        viewShipsButton.addActionListener(e -> {
            dispose();
            new ViewMyShipsPage(user);
        });

        chargesButton.addActionListener(e -> {
            dispose();
            new ViewAndPayChargesPage(user);
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