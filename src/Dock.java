public class Dock {
    private int id;
    private int number;
    private String status;
    private Integer currentShipId; // μπορεί να είναι null

    // Constructor με id (όταν έρχεται από DB)
    public Dock(int id, int number, String status, Integer currentShipId) {
        this.id = id;
        this.number = number;
        this.status = status;
        this.currentShipId = currentShipId;
    }

    // Constructor χωρίς id (για δημιουργία νέου dock)
    public Dock(int number, String status, Integer currentShipId) {
        this.number = number;
        this.status = status;
        this.currentShipId = currentShipId;
    }

    
    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCurrentShipId() {
        return currentShipId;
    }

    
    public void setNumber(int number) {
        this.number = number;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCurrentShipId(Integer currentShipId) {
        this.currentShipId = currentShipId;
    }
}