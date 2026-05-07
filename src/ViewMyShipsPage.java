import javax.swing.*;
import java.awt.*;

public class ViewMyShipsPage extends JFrame {

    public ViewMyShipsPage() {

        setTitle("View My Ships");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("My Ships", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        String[] columns = { "ID", "Name", "Type", "Capacity", "Captain ID" };

        Object[][] data = {
                { 1, "Poseidon", "Cargo", 5000, 2 },
                { 2, "Aegean Star", "Passenger", 800, 3 }
        };

        JTable table = new JTable(data, columns);

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}
