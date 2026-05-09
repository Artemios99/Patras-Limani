import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;

public class ManageEntryRequestsPage extends JFrame {

    private User user;

    private JTable portEntryTable;
    private JTable dockingTable;

    public ManageEntryRequestsPage(User user) {

        this.user = user;

        setTitle("Manage Requests");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(10,35,66);
        Color buttonColor = new Color(0,119,182);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        JLabel title = new JLabel(
                "Manage Requests",
                SwingConstants.CENTER
        );

        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 26));

        // =========================
        // PORT ENTRY TAB
        // =========================

        String[] portColumns = {
                "Request ID",
                "Ship ID",
                "Captain ID",
                "Arrival Date",
                "Status"
        };

        PortEntryRequestService portService =
                new PortEntryRequestService();

        ArrayList<PortEntryRequests> portRequests =
                portService.getAllRequests();

        Object[][] portData =
                new Object[portRequests.size()][5];

        for (int i = 0; i < portRequests.size(); i++) {

            PortEntryRequests request =
                    portRequests.get(i);

            portData[i][0] = request.getId();
            portData[i][1] = request.getShipId();
            portData[i][2] = request.getCaptainId();
            portData[i][3] = request.getArrivalDate();
            portData[i][4] = request.getStatus();
        }

        portEntryTable =
                new JTable(portData, portColumns);

        portEntryTable.setDefaultRenderer(
        Object.class,
        new StatusColorRenderer()
);

        JScrollPane portScroll =
                new JScrollPane(portEntryTable);

        JPanel portPanel =
                new JPanel(new BorderLayout());

        JButton approvePortButton =
                new JButton("Approve");

        JButton rejectPortButton =
                new JButton("Reject");

        styleButton(approvePortButton, buttonColor);
        styleButton(rejectPortButton, buttonColor);

        JPanel portButtons =
                new JPanel();

        portButtons.add(rejectPortButton);
        portButtons.add(approvePortButton);

        portPanel.add(portScroll, BorderLayout.CENTER);
        portPanel.add(portButtons, BorderLayout.SOUTH);

        String[] dockColumns = {
                "Request ID",
                "Ship ID",
                "Captain ID",
                "Requested Date",
                "Status"
        };

        DockingRequestService dockService =
                new DockingRequestService();

        ArrayList<DockingRequest> dockRequests =
                dockService.getAllRequests();

        Object[][] dockData =
                new Object[dockRequests.size()][5];

        for (int i = 0; i < dockRequests.size(); i++) {

            DockingRequest request =
                    dockRequests.get(i);

            dockData[i][0] = request.getId();
            dockData[i][1] = request.getShipId();
            dockData[i][2] = request.getCaptainId();
            dockData[i][3] = request.getRequestedDate();
            dockData[i][4] = request.getStatus();
        }

        dockingTable =
                new JTable(dockData, dockColumns);

        dockingTable.setDefaultRenderer(
        Object.class,
        new StatusColorRenderer()
);

        JScrollPane dockScroll =
                new JScrollPane(dockingTable);

        JPanel dockPanel =
                new JPanel(new BorderLayout());

        JButton approveDockButton =
                new JButton("Approve");

        JButton rejectDockButton =
                new JButton("Reject");

        styleButton(approveDockButton, buttonColor);
        styleButton(rejectDockButton, buttonColor);

        JPanel dockButtons =
                new JPanel();

        dockButtons.add(rejectDockButton);
        dockButtons.add(approveDockButton);

        dockPanel.add(dockScroll, BorderLayout.CENTER);
        dockPanel.add(dockButtons, BorderLayout.SOUTH);

        // =========================
        // TABS
        // =========================

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Port Entry Requests", portPanel);
        tabs.addTab("Docking Requests", dockPanel);

        // =========================
        // BACK BUTTON
        // =========================

        JButton backButton = new JButton("Back");

        styleButton(backButton, buttonColor);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);

        bottomPanel.add(backButton);

        // =========================
        // ACTIONS
        // =========================

        approvePortButton.addActionListener(
                e -> updatePortRequest("yes")
        );

        rejectPortButton.addActionListener(
                e -> updatePortRequest("no")
        );

        approveDockButton.addActionListener(
                e -> updateDockRequest("yes")
        );

        rejectDockButton.addActionListener(
                e -> updateDockRequest("no")
        );

        backButton.addActionListener(e -> {

            dispose();

            new PortAuthorityDashboard(user);
        });

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(tabs, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    private void updatePortRequest(String status) {

        int row = portEntryTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select a Port Entry request."
            );
            return;
        }

        int requestId =
                (int) portEntryTable.getValueAt(row,0);

        PortEntryRequestService service =
                new PortEntryRequestService();

        boolean success =
                service.updateRequestStatus(
                        requestId,
                        status
                );

        if (success) {

            portEntryTable.setValueAt(
                    status,
                    row,
                    4
            );
        }
    }

    private void updateDockRequest(String status) {

        int row = dockingTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select a Docking request."
            );
            return;
        }

        int requestId =
                (int) dockingTable.getValueAt(row,0);

        DockingRequestService service =
                new DockingRequestService();

        boolean success =
                service.updateRequestStatus(
                        requestId,
                        status
                );

        if (success) {

            dockingTable.setValueAt(
                    status,
                    row,
                    4
            );
        }
    }

    private void styleButton(JButton button,
                             Color color) {

        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(
                new Font("Arial",
                        Font.BOLD,
                        14)
        );
    }

    private static class StatusColorRenderer
        extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column
    ) {

        Component cell =
                super.getTableCellRendererComponent(
                        table,
                        value,
                        isSelected,
                        hasFocus,
                        row,
                        column
                );

        String status =
                table.getValueAt(row, 4).toString();

        if (status.equals("yes")) {

            cell.setBackground(Color.GREEN);
            cell.setForeground(Color.BLACK);

        }

        else if (status.equals("no")) {

            cell.setBackground(Color.RED);
            cell.setForeground(Color.WHITE);

        }

        else {

            cell.setBackground(Color.WHITE);
            cell.setForeground(Color.BLACK);
        }

        return cell;
    }
}
}