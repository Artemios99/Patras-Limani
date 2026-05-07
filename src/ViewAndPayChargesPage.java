import javax.swing.*;
import java.awt.*;

public class ViewAndPayChargesPage extends JFrame {

    private User user;

    public ViewAndPayChargesPage(User user) {

        this.user = user;

        setTitle("View And Pay Charges");
        setSize(750, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10, 35, 66);
        Color buttonColor = new Color(0, 119, 182);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel("My Charges", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        String[] columns = {
                "Payment ID",
                "Ship ID",
                "Amount",
                "Description",
                "Status"
        };

        Object[][] data = {
                {1, 1, 1500.0, "Docking Fee", "pending"},
                {2, 2, 3000.0, "Port Entry Fee", "paid"}
        };

        JTable table = new JTable(data, columns);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton payButton = new JButton("Pay Selected Charge");
        JButton backButton = new JButton("Back");

        styleButton(payButton, buttonColor);
        styleButton(backButton, buttonColor);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        bottomPanel.setBackground(backgroundColor);

        bottomPanel.add(backButton);
        bottomPanel.add(payButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        payButton.addActionListener(e -> {

            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a payment."
                );
                return;
            }

            table.setValueAt("paid", selectedRow, 4);

            JOptionPane.showMessageDialog(
                    this,
                    "Payment completed successfully!"
            );
        });

        backButton.addActionListener(e -> {

            dispose();

            new ShipOwnerDashboard(user);
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