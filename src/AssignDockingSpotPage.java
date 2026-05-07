import javax.swing.*;
import java.awt.*;

public class AssignDockingSpotPage extends JFrame {

    private User user;

    public AssignDockingSpotPage(User user) {

        this.user = user;

        setTitle("Assign Docking Spot");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel(
                "Assign Docking Spot",
                SwingConstants.CENTER
        );

        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        String[] columns = {
                "Request ID",
                "Ship ID",
                "Captain ID",
                "Status"
        };

        Object[][] data = {
                {1, 1, 2, "yes"},
                {2, 3, 4, "yes"}
        };

        JTable requestTable = new JTable(data, columns);

        JScrollPane scrollPane = new JScrollPane(requestTable);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        bottomPanel.setBackground(backgroundColor);

        JLabel dockLabel = new JLabel("Dock Number:");
        dockLabel.setForeground(Color.WHITE);

        JTextField dockField = new JTextField();

        JButton assignButton = new JButton("Assign Dock");
        JButton backButton = new JButton("Back");

        styleButton(assignButton, buttonColor);
        styleButton(backButton, buttonColor);

        bottomPanel.add(dockLabel);
        bottomPanel.add(dockField);
        bottomPanel.add(backButton);
        bottomPanel.add(assignButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        assignButton.addActionListener(e -> {

            int selectedRow = requestTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a request."
                );
                return;
            }

            String dockNumber = dockField.getText();

            JOptionPane.showMessageDialog(
                    this,
                    "Dock " + dockNumber + " assigned successfully!"
            );
        });

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