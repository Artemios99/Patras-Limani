import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class UIHelper {

    public static final Color BACKGROUND =
            new Color(5, 18, 33);

    public static final Color CARD =
            new Color(15, 35, 60);

    public static final Color PRIMARY =
            new Color(0, 140, 255);

    public static final Color PRIMARY_HOVER =
            new Color(35, 160, 255);

    public static final Color TEXT =
            Color.WHITE;

    public static final Color MUTED_TEXT =
            new Color(190, 205, 220);

    public static final Font TITLE_FONT =
            new Font("Segoe UI", Font.BOLD, 30);

    public static final Font SUBTITLE_FONT =
            new Font("Segoe UI", Font.PLAIN, 15);

    public static final Font LABEL_FONT =
            new Font("Segoe UI", Font.BOLD, 14);

    public static final Font BUTTON_FONT =
            new Font("Segoe UI", Font.BOLD, 14);

    public static final Font TABLE_FONT =
            new Font("Segoe UI", Font.PLAIN, 13);


    public static void setupFrame(
            JFrame frame,
            String title,
            int width,
            int height
    ) {

        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


    public static JPanel createMainPanel() {

        JPanel panel =
                new JPanel(new BorderLayout(20, 20));

        panel.setBackground(BACKGROUND);

        panel.setBorder(
                new EmptyBorder(25, 30, 25, 30)
        );

        return panel;
    }


    public static JPanel createCardPanel(
            LayoutManager layout
    ) {

        JPanel panel = new JPanel(layout);

        panel.setBackground(CARD);

        panel.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                new Color(40, 70, 100),
                                1,
                                true
                        ),
                        new EmptyBorder(25, 25, 25, 25)
                )
        );

        return panel;
    }


    public static JLabel createTitle(String text) {

        JLabel label =
                new JLabel(text, SwingConstants.CENTER);

        label.setForeground(TEXT);
        label.setFont(TITLE_FONT);

        return label;
    }


    public static JLabel createSubtitle(String text) {

        JLabel label =
                new JLabel(text, SwingConstants.CENTER);

        label.setForeground(MUTED_TEXT);
        label.setFont(SUBTITLE_FONT);

        return label;
    }


    public static JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setForeground(TEXT);
        label.setFont(LABEL_FONT);

        return label;
    }


    public static JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setBackground(PRIMARY);
        button.setForeground(TEXT);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setFont(BUTTON_FONT);

        button.setPreferredSize(
                new Dimension(150, 45)
        );

        button.setBorder(
                new EmptyBorder(12, 18, 12, 18)
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    public void mouseEntered(
                            java.awt.event.MouseEvent evt
                    ) {
                        button.setBackground(PRIMARY_HOVER);
                    }

                    public void mouseExited(
                            java.awt.event.MouseEvent evt
                    ) {
                        button.setBackground(PRIMARY);
                    }
                }
        );

        return button;
    }


    public static JButton createBackButton() {

        JButton button =
                createButton("Back");

        button.setBackground(
                new Color(90, 105, 120)
        );

        return button;
    }


    public static JTextField createTextField() {

        JTextField field = new JTextField();

        field.setFont(TABLE_FONT);

        field.setPreferredSize(
                new Dimension(220, 40)
        );

        field.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                new Color(70, 90, 120),
                                1,
                                true
                        ),
                        new EmptyBorder(8, 12, 8, 12)
                )
        );

        return field;
    }


    public static JPasswordField createPasswordField() {

        JPasswordField field =
                new JPasswordField();

        field.setFont(TABLE_FONT);

        field.setPreferredSize(
                new Dimension(220, 40)
        );

        field.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                new Color(70, 90, 120),
                                1,
                                true
                        ),
                        new EmptyBorder(8, 12, 8, 12)
                )
        );

        return field;
    }


    public static JComboBox<String> createComboBox(
            String[] items
    ) {

        JComboBox<String> box =
                new JComboBox<>(items);

        box.setFont(TABLE_FONT);

        box.setBackground(Color.WHITE);

        box.setForeground(
                new Color(30, 30, 30)
        );

        box.setPreferredSize(
                new Dimension(220, 40)
        );

        box.setFocusable(false);

        box.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                new Color(70, 90, 120),
                                1,
                                true
                        ),
                        new EmptyBorder(5, 10, 5, 10)
                )
        );

        return box;
    }


    public static JScrollPane styleTable(
            JTable table
    ) {

        table.setFont(TABLE_FONT);

        table.setRowHeight(32);

        table.setGridColor(
                new Color(55, 75, 95)
        );

        table.setBackground(
                new Color(20, 40, 65)
        );

        table.setForeground(Color.WHITE);

        table.setSelectionBackground(PRIMARY);

        table.setSelectionForeground(Color.WHITE);

        JTableHeader header =
                table.getTableHeader();

        header.setBackground(
                new Color(0, 90, 150)
        );

        header.setForeground(Color.WHITE);

        header.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setBorder(
                new LineBorder(
                        new Color(50, 70, 95),
                        1
                )
        );

        return scrollPane;
    }
}