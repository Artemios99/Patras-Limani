public class Payment {
    private int id;
    private int shipId;
    private int ownerId;
    private double amount;
    private String description;
    private String status;

    // Constructor με id (όταν έρχεται από DB)
    public Payment(int id, int shipId, int ownerId, double amount, String description, String status) {
        this.id = id;
        this.shipId = shipId;
        this.ownerId = ownerId;
        this.amount = amount;
        this.description = description;
        this.status = status;
    }

    // Constructor χωρίς id (για νέο payment)
    public Payment(int shipId, int ownerId, double amount, String description, String status) {
        this.shipId = shipId;
        this.ownerId = ownerId;
        this.amount = amount;
        this.description = description;
        this.status = status;
    }

    
    public int getId() {
        return id;
    }

    public int getShipId() {
        return shipId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    
    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}