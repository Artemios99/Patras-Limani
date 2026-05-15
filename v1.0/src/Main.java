import com.formdev.flatlaf.FlatDarkLaf;

public class Main {

    public static void main(String[] args) {

        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DatabaseManager.createTables();

        new LoginPage();
    }
}