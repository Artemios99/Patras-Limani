public class PortEntryRequests {
    private int id;
    private int shipId;
    private int captainId;
    private String arrivalDate;
    private String status;

    // Constructor με id (όταν έρχεται από DB)
    public PortEntryRequests(int id, int shipId, int captainId, String arrivalDate, String status) {
        this.id = id;
        this.shipId = shipId;
        this.captainId = captainId;
        this.arrivalDate = arrivalDate;
        this.status = status;
    }

    // Constructor χωρίς id (για δημιουργία νέου request)
    public PortEntryRequests(int shipId, int captainId, String arrivalDate, String status) {
        this.shipId = shipId;
        this.captainId = captainId;
        this.arrivalDate = arrivalDate;
        this.status = status;
    }

    
    public int getId() {
        return id;
    }

    public int getShipId() {
        return shipId;
    }

    public int getCaptainId() {
        return captainId;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getStatus() {
        return status;
    }

    
    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public void setCaptainId(int captainId) {
        this.captainId = captainId;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}