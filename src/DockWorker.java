public class DockWorker extends User {

    // Constructor με id (π.χ. όταν ο χρήστης υπάρχει ήδη στη βάση)
    public DockWorker(int id, String name, String surname,
                      String phone, String email, String dateOfBirth,
                      String username, String password) {

        // Καλούμε τον constructor της User
        // Ορίζουμε τον τύπο χρήστη ως "DockWorker"
        super(id, "DockWorker", name, surname, phone, email, dateOfBirth, username, password);
    }

    // Constructor χωρίς id (για δημιουργία νέου χρήστη)
    public DockWorker(String name, String surname,
                      String phone, String email, String dateOfBirth,
                      String username, String password) {

        // Κλήση constructor χωρίς id
        super("DockWorker", name, surname, phone, email, dateOfBirth, username, password);
    }

    // Εδώ μπορείς να προσθέσεις μεθόδους ειδικές για DockWorker
    // π.χ. φόρτωση/εκφόρτωση πλοίων
}
    

