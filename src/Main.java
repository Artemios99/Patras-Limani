public class Main {
    public static void main(String[] args) {

        DatabaseManager.createTables();

        // Δημιουργία user
        User user = new User(
                "Captain",
                "Artemis",
                "Papadopoulos",
                "6900000000",
                "artemis@gmail.com",
                "2000-01-01",
                "artemis123",
                "1234");

        // Εκτύπωση για έλεγχο
        System.out.println("Username: " + user.getUsername());
        System.out.println("Name: " + user.getName());
        System.out.println("Type: " + user.getUserType());

    }
}