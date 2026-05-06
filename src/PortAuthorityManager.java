 public class PortAuthorityManager extends User {

    // Constructor με id
    public PortAuthorityManager(int id, String name, String surname,
                                String phone, String email, String dateOfBirth,
                                String username, String password) {

        super(id, "PortAuthorityManager", name, surname, phone, email, dateOfBirth, username, password);
    }

    // Constructor χωρίς id
    public PortAuthorityManager(String name, String surname,
                                String phone, String email, String dateOfBirth,
                                String username, String password) {

        super("PortAuthorityManager", name, surname, phone, email, dateOfBirth, username, password);
    }

    // Μέθοδοι για διαχείριση λιμανιού μπορούν να μπουν εδώ
}
    

