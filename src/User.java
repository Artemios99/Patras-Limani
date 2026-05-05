public class User {
    protected int id;
    protected String userType;
    protected String name;
    protected String surname;
    protected String phone;
    protected String email;
    protected String dateOfBirth;
    protected String username;
    protected String password;

    public User(int id, String userType, String name, String surname,
                String phone, String email, String dateOfBirth,
                String username, String password) {
        this.id = id;
        this.userType = userType;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
    }

    public User(String userType, String name, String surname,
                String phone, String email, String dateOfBirth,
                String username, String password) {
        this.userType = userType;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUserType() {
        return userType;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}