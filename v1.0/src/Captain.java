 
    public class Captain extends User {

    // Constructor με id (όταν π.χ. διαβάζεις από βάση δεδομένων)
    public Captain(int id, String name, String surname,
                   String phone, String email, String dateOfBirth,
                   String username, String password) {

        // Κλήση του constructor της User
        // Ορίζουμε αυτόματα τον τύπο χρήστη ως "Captain"
        super(id, "Captain", name, surname, phone, email, dateOfBirth, username, password);
    }

    // Constructor χωρίς id (π.χ. για νέο χρήστη πριν αποθηκευτεί)
    public Captain(String name, String surname,
                   String phone, String email, String dateOfBirth,
                   String username, String password) {

        // Κλήση constructor χωρίς id
        super("Captain", name, surname, phone, email, dateOfBirth, username, password);
    }

    // Εδώ μπορούν να προστεθούν μέθοδοι που αφορούν μόνο Captain
}

