public class Ship {

    private int id;
    private String shipCode;
    private String name;
    private String type;
    private int capacity;
    private int ownerId;
    private int captainId;

    // Constructor με id
    public Ship(int id, String shipCode, String name, String type,
                int capacity, int ownerId, int captainId) {

        this.id = id;
        this.shipCode = shipCode;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.ownerId = ownerId;
        this.captainId = captainId;
    }

    // Constructor χωρίς id
    public Ship(String shipCode, String name, String type,
                int capacity, int ownerId, int captainId) {

        this.shipCode = shipCode;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.ownerId = ownerId;
        this.captainId = captainId;
    }

    public int getId() {
        return id;
    }

    public String getShipCode() {
        return shipCode;
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

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode;
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

    @Override
    public String toString() {
        return shipCode + " - " + name;
    }
    
}