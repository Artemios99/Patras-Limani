import javax.swing.*;
import java.awt.*;

public class ReleaseDockPage extends JFrame {

    private User user;

    public ReleaseDockPage(User user) {

        this.user = user;

        setTitle("Release Dock");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel("Release Dock", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        String[] columns = {"Dock ID", "Dock Number", "Ship ID", "Status"};

        Object[][] data = {
                {1, 1, 3, "occupied"},
                {2, 2, 5, "occupied"}
        };

        JTable table = new JTable(data, columns);

        JButton releaseButton = new JButton("Release Selected Dock");
        JButton backButton = new JButton("Back");

        styleButton(releaseButton, buttonColor);
        styleButton(backButton, buttonColor);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        bottomPanel.setBackground(backgroundColor);

        bottomPanel.add(backButton);
        bottomPanel.add(releaseButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        releaseButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a dock first.");
                return;
            }

            table.setValueAt(null, selectedRow, 2);
            table.setValueAt("available", selectedRow, 3);

            JOptionPane.showMessageDialog(this, "Dock released successfully!");
        });

        backButton.addActionListener(e -> {
            dispose();
            new DockWorkerDashboard(user);
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