import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class RegisterPage extends JFrame {

    public RegisterPage() {

        UIHelper.setupFrame(this, "Register User", 700, 720);

        JPanel mainPanel = UIHelper.createMainPanel();

        JLabel title = UIHelper.createTitle("Register User");

        JPanel panel =
                UIHelper.createCardPanel(
                        new GridLayout(9, 2, 12, 12)
                );

        JTextField nameField =
                UIHelper.createTextField();

        JTextField surnameField =
                UIHelper.createTextField();

        JTextField phoneField =
                UIHelper.createTextField();

        JTextField emailField =
                UIHelper.createTextField();

        JTextField usernameField =
                UIHelper.createTextField();

        JPasswordField passwordField =
                UIHelper.createPasswordField();

        // DATE CHOOSER
        JDateChooser dobChooser =
                new JDateChooser();

        dobChooser.setDateFormatString(
                "yyyy-MM-dd"
        );

        dobChooser.setPreferredSize(
                new Dimension(320, 50)
        );

        dobChooser.setFont(
                new Font("Segoe UI", Font.PLAIN, 16)
        );

        dobChooser.getDateEditor().getUiComponent().setFont(
                new Font("Segoe UI", Font.PLAIN, 16)
        );

        String[] roles = {
                "Captain",
                "ShipOwner",
                "DockWorker",
                "PortAuthorityManager"
        };

        JComboBox<String> roleBox =
                UIHelper.createComboBox(roles);

        panel.add(UIHelper.createLabel("Name"));
        panel.add(nameField);

        panel.add(UIHelper.createLabel("Surname"));
        panel.add(surnameField);

        panel.add(UIHelper.createLabel("Phone"));
        panel.add(phoneField);

        panel.add(UIHelper.createLabel("Email"));
        panel.add(emailField);

        panel.add(UIHelper.createLabel("Date of Birth"));
        panel.add(dobChooser);

        panel.add(UIHelper.createLabel("Username"));
        panel.add(usernameField);

        panel.add(UIHelper.createLabel("Password"));
        panel.add(passwordField);

        panel.add(UIHelper.createLabel("Role"));
        panel.add(roleBox);

        JButton backButton =
                UIHelper.createBackButton();

        JButton registerButton =
                UIHelper.createButton("Register");

        panel.add(backButton);
        panel.add(registerButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(panel, BorderLayout.CENTER);

        add(mainPanel);

        registerButton.addActionListener(e -> {

            String name =
                    nameField.getText().trim();

            String surname =
                    surnameField.getText().trim();

            String phone =
                    phoneField.getText().trim();

            String email =
                    emailField.getText().trim();

            String username =
                    usernameField.getText().trim();

            String password =
                    new String(
                            passwordField.getPassword()
                    ).trim();

            String role =
                    (String) roleBox.getSelectedItem();

            if (dobChooser.getDate() == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select date of birth.",
                        "Missing Information",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd");

            String dob =
                    sdf.format(dobChooser.getDate());

            if (name.isEmpty() ||
                    surname.isEmpty() ||
                    phone.isEmpty() ||
                    email.isEmpty() ||
                    username.isEmpty() ||
                    password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill in all fields.",
                        "Missing Information",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            AuthService authService =
                    new AuthService();

            User existingUsername =
                    authService.getUserByUsername(
                            username
                    );

            if (existingUsername != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Username already exists.",
                        "Duplicate Username",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            User user = new User(
                    role,
                    name,
                    surname,
                    phone,
                    email,
                    dob,
                    username,
                    password
            );

            boolean success =
                    authService.registerUser(user);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "User registered successfully!"
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration failed. Email may already exist.",
                        "Registration Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        backButton.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }
}