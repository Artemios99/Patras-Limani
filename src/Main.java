public class Main {
    public static void main(String[] args) {

        DatabaseManager.createTables();

        // 1. Register/Login User
        User user = new User(
                "Captain",
                "Artemis",
                "Papadopoulos",
                "6900000000",
                "artemis@gmail.com",
                "2000-01-01",
                "artemis123",
                "1234"
        );

        AuthService authService = new AuthService();
        authService.registerUser(user);
        authService.loginUser("artemis123", "1234");

        // 2. Ship
        Ship ship = new Ship(
                "Poseidon",
                "Cargo",
                5000,
                1,
                1
        );

        ShipService shipService = new ShipService();
        shipService.registerShip(ship);

        // 3. Dock
        Dock dock = new Dock(
                1,
                "available",
                null
        );

        DockService dockService = new DockService();
        dockService.addDock(dock);

        // 4. Port Entry Request
        PortEntryRequests portEntryRequest = new PortEntryRequests(
                1,
                1,
                "2025-05-01",
                "pending"
        );

        PortEntryRequestService portEntryRequestService = new PortEntryRequestService();
        portEntryRequestService.createRequest(portEntryRequest);

        // 5. Docking Request
        DockingRequest dockingRequest = new DockingRequest(
                1,
                1,
                "2025-05-02",
                "pending"
        );

        DockingRequestService dockingRequestService = new DockingRequestService();
        dockingRequestService.createRequest(dockingRequest);

        // 6. Payment
        Payment payment = new Payment(
                1,
                1,
                1500.0,
                "Docking fee",
                "pending"
        );

        PaymentService paymentService = new PaymentService();
        paymentService.createPayment(payment);

        System.out.println("ALL BACKEND TESTS FINISHED!");
    }
}