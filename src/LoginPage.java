import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {

    public LoginPage() {

        UIHelper.setupFrame(this, "PATRAS LIMANI - Login", 750, 660);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = UIHelper.createMainPanel();

        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headerPanel.setBackground(UIHelper.BACKGROUND);

        JLabel appTitle = UIHelper.createTitle("PATRAS LIMANI");
        JLabel subtitle = UIHelper.createSubtitle("Port Management System");

        headerPanel.add(appTitle);
        headerPanel.add(subtitle);

        JPanel cardPanel = UIHelper.createCardPanel(new BorderLayout(15, 15));

        JLabel loginTitle = UIHelper.createTitle("Login");
        cardPanel.add(loginTitle, BorderLayout.NORTH);

        JTextField usernameField = UIHelper.createTextField();
        JPasswordField passwordField = UIHelper.createPasswordField();

        String[] roles = {
                "Captain",
                "ShipOwner",
                "DockWorker",
                "PortAuthorityManager"
        };

        JComboBox<String> roleBox = UIHelper.createComboBox(roles);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UIHelper.CARD);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(UIHelper.createLabel("Username"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(UIHelper.createLabel("Password"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(UIHelper.createLabel("Role"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        formPanel.add(roleBox, gbc);

        JButton loginButton = UIHelper.createButton("Login");

        JPanel loginButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButtonPanel.setBackground(UIHelper.CARD);
        loginButtonPanel.add(loginButton);

        cardPanel.add(formPanel, BorderLayout.CENTER);
        cardPanel.add(loginButtonPanel, BorderLayout.SOUTH);

        JButton registerButton = UIHelper.createButton("Register");
        JLabel newUserLabel = UIHelper.createSubtitle("New User?");

        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        registerPanel.setBackground(UIHelper.BACKGROUND);
        registerPanel.add(newUserLabel);
        registerPanel.add(registerButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        mainPanel.add(registerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        loginButton.addActionListener(e -> {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            AuthService authService = new AuthService();

            User loggedUser = authService.getUserByLogin(username, password, role);

            if (loggedUser != null) {
                JOptionPane.showMessageDialog(this, "Login successful!");

                dispose();

                switch (role) {
                    case "Captain":
                        new CaptainDashboard(loggedUser);
                        break;

                    case "ShipOwner":
                        new ShipOwnerDashboard(loggedUser);
                        break;

                    case "DockWorker":
                        new DockWorkerDashboard(loggedUser);
                        break;

                    case "PortAuthorityManager":
                        new PortAuthorityDashboard(loggedUser);
                        break;

                    default:
                        JOptionPane.showMessageDialog(this, "Unknown role!");
                        break;
                }

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Wrong username, password or role!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        registerButton.addActionListener(e -> {
            new RegisterPage();
        });

        setVisible(true);
    }
}