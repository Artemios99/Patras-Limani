import javax.swing.*;
import java.awt.*;

public class ViewAndPayChargesPage extends JFrame {

    public ViewAndPayChargesPage() {

        setTitle("View And Pay Charges");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Title
        JLabel title = new JLabel("My Charges", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        // Table columns
        String[] columns = {
                "Payment ID",
                "Ship ID",
                "Amount",
                "Description",
                "Status"
        };

        // Dummy data προσωρινά
        Object[][] data = {
                { 1, 1, 1500.0, "Docking Fee", "pending" },
                { 2, 2, 3000.0, "Port Entry Fee", "paid" }
        };

        JTable table = new JTable(data, columns);

        // Pay button
        JButton payButton = new JButton("Pay Selected Charge");

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(payButton);

        // Add to main panel
        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);

        setVisible(true);
    }
}