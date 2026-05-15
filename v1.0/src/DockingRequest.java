public class DockingRequest {
    private int id;
    private int shipId;
    private int captainId;
    private String requestedDate;
    private String status;

    // Constructor με id (όταν έρχεται από DB)
    public DockingRequest(int id, int shipId, int captainId, String requestedDate, String status) {
        this.id = id;
        this.shipId = shipId;
        this.captainId = captainId;
        this.requestedDate = requestedDate;
        this.status = status;
    }

    // Constructor χωρίς id (για νέο request)
    public DockingRequest(int shipId, int captainId, String requestedDate, String status) {
        this.shipId = shipId;
        this.captainId = captainId;
        this.requestedDate = requestedDate;
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

    public String getRequestedDate() {
        return requestedDate;
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

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}