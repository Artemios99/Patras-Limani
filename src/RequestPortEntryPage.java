import javax.swing.*;
import java.awt.*;

public class RequestPortEntryPage extends JFrame {

    private User user;

    public RequestPortEntryPage(User user) {

        this.user = user;

        setTitle("Request Port Entry");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(backgroundColor);

        JTextField shipIdField = new JTextField();
        JTextField arrivalDateField = new JTextField();

        JButton backButton = new JButton("Back");
        JButton requestButton = new JButton("Request Entry");

        styleButton(backButton, buttonColor);
        styleButton(requestButton, buttonColor);

        addLabel(panel, "Ship ID:");
        panel.add(shipIdField);

        addLabel(panel, "Arrival Date:");
        panel.add(arrivalDateField);

        panel.add(backButton);
        panel.add(requestButton);

        add(panel);

        requestButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "Port entry request submitted successfully!"
            );
        });

        backButton.addActionListener(e -> {
            dispose();
            new CaptainDashboard(user);
        });

        setVisible(true);
    }

    private void addLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        panel.add(label);
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }
}