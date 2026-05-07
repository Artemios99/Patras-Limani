import javax.swing.*;
import java.awt.*;

public class ManageEntryRequestsPage extends JFrame {

    private User user;
    private JTable requestsTable;

    public ManageEntryRequestsPage(User user) {

        this.user = user;

        setTitle("Manage Entry Requests");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel("Manage Entry Requests", SwingConstants.CENTER);
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
                {2, 3, 4, "2025-05-03", "pending"}
        };

        requestsTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(requestsTable);

        JButton approveButton = new JButton("Approve");
        JButton rejectButton = new JButton("Reject");
        JButton backButton = new JButton("Back");

        styleButton(approveButton, buttonColor);
        styleButton(rejectButton, buttonColor);
        styleButton(backButton, buttonColor);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        buttonPanel.setBackground(backgroundColor);

        buttonPanel.add(backButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(approveButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        approveButton.addActionListener(e -> updateSelectedRequest("yes"));
        rejectButton.addActionListener(e -> updateSelectedRequest("no"));

        backButton.addActionListener(e -> {
            dispose();
            new PortAuthorityDashboard(user);
        });

        setVisible(true);
    }

    private void updateSelectedRequest(String newStatus) {

        int selectedRow = requestsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request first.");
            return;
        }

        requestsTable.setValueAt(newStatus, selectedRow, 4);

        JOptionPane.showMessageDialog(
                this,
                "Request updated to: " + newStatus
        );
    }

    private void styleButton(JButton button, Color color) {

        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }
}