
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

        JPanel menuPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 180, 50, 180));
        menuPanel.setBackground(backgroundColor);

        JButton tasksButton = createButton("Assigned Tasks", buttonColor);
        JButton cargoButton = createButton("Cargo Loading", buttonColor);
        JButton scheduleButton = createButton("Schedule", buttonColor);
        JButton shiftButton = createButton("Shift Info", buttonColor);

        menuPanel.add(tasksButton);
        menuPanel.add(cargoButton);
        menuPanel.add(scheduleButton);
        menuPanel.add(shiftButton);

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