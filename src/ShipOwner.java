public class ShipOwner extends User {

    // Constructor με id
    public ShipOwner(int id, String name, String surname,
                     String phone, String email, String dateOfBirth,
                     String username, String password) {

        // Ορίζουμε τον τύπο ως "ShipOwner"
        super(id, "ShipOwner", name, surname, phone, email, dateOfBirth, username, password);
    }

    // Constructor χωρίς id
    public ShipOwner(String name, String surname,
                     String phone, String email, String dateOfBirth,
                     String username, String password) {

        super("ShipOwner", name, surname, phone, email, dateOfBirth, username, password);
    }

    // Μέθοδοι ειδικές για ShipOwner μπορούν να μπουν εδώ
}

