public class Ship {
    private int id;
    private String name;
    private String type;
    private int capacity;
    private int ownerId;
    private int captainId;

    // Constructor με id (όταν έρχεται από database)
    public Ship(int id, String name, String type, int capacity, int ownerId, int captainId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.ownerId = ownerId;
        this.captainId = captainId;
    }

    // Constructor χωρίς id (όταν δημιουργείς νέο ship)
    public Ship(String name, String type, int capacity, int ownerId, int captainId) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.ownerId = ownerId;
        this.captainId = captainId;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getCaptainId() {
        return captainId;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setCaptainId(int captainId) {
        this.captainId = captainId;
    }
}