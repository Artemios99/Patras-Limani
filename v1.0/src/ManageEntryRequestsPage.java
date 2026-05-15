import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;

public class ManageEntryRequestsPage extends JFrame {


    private JTable portEntryTable;
    private JTable dockingTable;

    public ManageEntryRequestsPage(User user) {


        UIHelper.setupFrame(this, "Manage Requests", 1050, 650);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel title = UIHelper.createTitle("Manage Requests");
        JLabel subtitle = UIHelper.createSubtitle(
                "Approve or reject port entry and docking requests"
        );

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

        JTabbedPane tabs = new JTabbedPane();

        JPanel portPanel = createPortEntryPanel();
        JPanel dockPanel = createDockingPanel();

        tabs.addTab("Port Entry Requests", portPanel);
        tabs.addTab("Docking Requests", dockPanel);

        JButton backButton = UIHelper.createBackButton();

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(UIHelper.BACKGROUND);
        bottomPanel.add(backButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabs, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        backButton.addActionListener(e -> {
            dispose();
            new PortAuthorityDashboard(user);
        });

        setVisible(true);
    }

    private JPanel createPortEntryPanel() {

        JPanel panel = UIHelper.createCardPanel(new BorderLayout(15, 15));

        String[] portColumns = {
                "Request ID",
                "Ship ID",
                "Captain ID",
                "Arrival Date",
                "Status"
        };

        PortEntryRequestService portService = new PortEntryRequestService();

        ArrayList<PortEntryRequests> portRequests =
                portService.getAllRequests();

        Object[][] portData =
                new Object[portRequests.size()][5];

        for (int i = 0; i < portRequests.size(); i++) {

            PortEntryRequests request = portRequests.get(i);

            portData[i][0] = request.getId();
            portData[i][1] = request.getShipId();
            portData[i][2] = request.getCaptainId();
            portData[i][3] = request.getArrivalDate();
            portData[i][4] = request.getStatus();
        }

        portEntryTable = new JTable(portData, portColumns);

        portEntryTable.setDefaultRenderer(
                Object.class,
                new StatusColorRenderer()
        );

        JScrollPane scrollPane = UIHelper.styleTable(portEntryTable);

        JButton rejectButton = UIHelper.createButton("Reject");
        JButton approveButton = UIHelper.createButton("Approve");

        rejectButton.setBackground(new Color(220, 70, 70));
        approveButton.setBackground(new Color(46, 204, 113));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setBackground(UIHelper.CARD);

        buttonPanel.add(rejectButton);
        buttonPanel.add(approveButton);

        rejectButton.addActionListener(e -> updatePortRequest("no"));
        approveButton.addActionListener(e -> updatePortRequest("yes"));

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createDockingPanel() {

        JPanel panel = UIHelper.createCardPanel(new BorderLayout(15, 15));

        String[] dockColumns = {
                "Request ID",
                "Ship ID",
                "Captain ID",
                "Requested Date",
                "Status"
        };

        DockingRequestService dockService = new DockingRequestService();

        ArrayList<DockingRequest> dockRequests =
                dockService.getAllRequests();

        Object[][] dockData =
                new Object[dockRequests.size()][5];

        for (int i = 0; i < dockRequests.size(); i++) {

            DockingRequest request = dockRequests.get(i);

            dockData[i][0] = request.getId();
            dockData[i][1] = request.getShipId();
            dockData[i][2] = request.getCaptainId();
            dockData[i][3] = request.getRequestedDate();
            dockData[i][4] = request.getStatus();
        }

        dockingTable = new JTable(dockData, dockColumns);

        dockingTable.setDefaultRenderer(
                Object.class,
                new StatusColorRenderer()
        );

        JScrollPane scrollPane = UIHelper.styleTable(dockingTable);

        JButton rejectButton = UIHelper.createButton("Reject");
        JButton approveButton = UIHelper.createButton("Approve");

        rejectButton.setBackground(new Color(220, 70, 70));
        approveButton.setBackground(new Color(46, 204, 113));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setBackground(UIHelper.CARD);

        buttonPanel.add(rejectButton);
        buttonPanel.add(approveButton);

        rejectButton.addActionListener(e -> updateDockRequest("no"));
        approveButton.addActionListener(e -> updateDockRequest("yes"));

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
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
                (int) portEntryTable.getValueAt(row, 0);

        PortEntryRequestService service =
                new PortEntryRequestService();

        boolean success =
                service.updateRequestStatus(requestId, status);

        if (success) {
            portEntryTable.setValueAt(status, row, 4);
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
                (int) dockingTable.getValueAt(row, 0);

        DockingRequestService service =
                new DockingRequestService();

        boolean success =
                service.updateRequestStatus(requestId, status);

        if (success) {
            dockingTable.setValueAt(status, row, 4);
        }
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

            if (isSelected) {
                cell.setBackground(UIHelper.PRIMARY);
                cell.setForeground(Color.WHITE);
                return cell;
            }

            if (status.equals("yes")) {
                cell.setBackground(new Color(46, 204, 113));
                cell.setForeground(Color.BLACK);
            } else if (status.equals("no")) {
                cell.setBackground(new Color(231, 76, 60));
                cell.setForeground(Color.WHITE);
            } else {
                cell.setBackground(new Color(245, 245, 245));
                cell.setForeground(Color.BLACK);
            }

            return cell;
        }
    }
}