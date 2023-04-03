package tcrs;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private String VIN;
    private String licensePlateNumber;
    private String make;
    private String model;
    private boolean registrationStatus;
    private boolean stolenStatus;
    private boolean warrantStatus;
    private String notes;

    // SQL Server and Credentials
    private static String url = "jdbc:mysql://localhost:3306/project";
    private static String username = "root";
    private static String password = "SQL@dm1nms";

    // Constructors
    public Vehicle() {
        // Default values for the vehicle attributes
        licensePlateNumber = "";
        make = "";
        model = "";
        registrationStatus = false;
        stolenStatus = false;
        warrantStatus = false;
        notes = "";
    }

    public Vehicle(String VIN, String licensePlateNumber, String make, String model, boolean registrationStatus,
            boolean stolenStatus, boolean warrantStatus, String notes) {
        this.VIN = VIN;
        this.licensePlateNumber = licensePlateNumber;
        this.make = make;
        this.model = model;
        this.registrationStatus = registrationStatus;
        this.stolenStatus = stolenStatus;
        this.warrantStatus = warrantStatus;
        this.notes = notes;
    }

    // Getters and Setters
    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(boolean registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public boolean isStolenStatus() {
        return stolenStatus;
    }

    public void setStolenStatus(boolean stolenStatus) {
        this.stolenStatus = stolenStatus;
    }

    public boolean isWarrantStatus() {
        return warrantStatus;
    }

    public void setWarrantStatus(boolean warrantStatus) {
        this.warrantStatus = warrantStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Methods
    // Save to the database
    public void save() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String insertVehicleSql = "INSERT INTO Vehicles (VIN, LicensePlateNumber, Make, Model, RegistrationStatus, StolenStatus, WarrantStatus, Notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertVehicleSql);
            preparedStatement.setString(1, this.VIN);
            preparedStatement.setString(2, this.licensePlateNumber);
            preparedStatement.setString(3, this.make);
            preparedStatement.setString(4, this.model);
            preparedStatement.setBoolean(5, this.registrationStatus);
            preparedStatement.setBoolean(6, this.stolenStatus);
            preparedStatement.setBoolean(7, this.warrantStatus);
            preparedStatement.setString(8, this.notes);

            preparedStatement.executeUpdate();
        }
    }

    // Update Vehicle
    public void updateVehicle() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String updateVehicleSql = "UPDATE Vehicles SET LicensePlateNumber = ?, Make = ?, Model = ?, RegistrationStatus = ?, StolenStatus = ?, WarrantStatus = ?, Notes = ? WHERE VIN = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateVehicleSql);
            preparedStatement.setString(1, this.licensePlateNumber);
            preparedStatement.setString(2, this.make);
            preparedStatement.setString(3, this.model);
            preparedStatement.setBoolean(4, this.registrationStatus);
            preparedStatement.setBoolean(5, this.stolenStatus);
            preparedStatement.setBoolean(6, this.warrantStatus);
            preparedStatement.setString(7, this.notes);
            preparedStatement.setString(8, this.VIN);

            preparedStatement.executeUpdate();
        }
    }

    // List all vehicles in the database
    public static List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = DatabaseUtils.getConnection()) {
            String selectVehiclesSql = "SELECT * FROM Vehicles";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectVehiclesSql);

            while (resultSet.next()) {
                String vin = resultSet.getString("VIN");
                String licensePlateNumber = resultSet.getString("LicensePlateNumber");
                String make = resultSet.getString("Make");
                String model = resultSet.getString("Model");
                boolean registrationStatus = resultSet.getBoolean("RegistrationStatus");
                boolean stolenStatus = resultSet.getBoolean("StolenStatus");
                boolean warrantStatus = resultSet.getBoolean("WarrantStatus");
                String notes = resultSet.getString("Notes");

                Vehicle vehicle = new Vehicle(vin, licensePlateNumber, make, model, registrationStatus, stolenStatus,
                        warrantStatus, notes);
                vehicles.add(vehicle);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    // Search for a vehicle
    public static Vehicle searchVehicle(String vin) throws SQLException {
        Vehicle vehicle = null;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Create a PreparedStatement to search for the vehicle with the given VIN
            String searchVehicleSql = "SELECT * FROM Vehicles WHERE VIN = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(searchVehicleSql);
            preparedStatement.setString(1, vin);

            // Execute the query and get the ResultSet of matching vehicles
            ResultSet resultSet = preparedStatement.executeQuery();

            // If a vehicle was found, create a Vehicle object and set its fields to the
            // values from the ResultSet
            if (resultSet.next()) {
                String licensePlateNumber = resultSet.getString("LicensePlateNumber");
                String make = resultSet.getString("Make");
                String model = resultSet.getString("Model");
                boolean registrationStatus = resultSet.getBoolean("RegistrationStatus");
                boolean stolenStatus = resultSet.getBoolean("StolenStatus");
                boolean warrantStatus = resultSet.getBoolean("WarrantStatus");
                String notes = resultSet.getString("Notes");

                vehicle = new Vehicle(vin, licensePlateNumber, make, model, registrationStatus, stolenStatus,
                        warrantStatus, notes);
            }
        }

        return vehicle;
    }

    // Delete this vehicle
    public void deleteVehicle() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String deleteVehicleSql = "DELETE FROM Vehicles WHERE VIN = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteVehicleSql);
            preparedStatement.setString(1, this.VIN);

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "VIN='" + VIN + '\'' +
                ", licensePlateNumber='" + licensePlateNumber + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", registrationStatus=" + registrationStatus +
                ", stolenStatus=" + stolenStatus +
                ", warrantStatus=" + warrantStatus +
                ", notes='" + notes + '\'' +
                '}';
    }

//    public static void main(String[] args) {
//
//        try {
//            System.out.println(Vehicle.searchVehicle("1HGBH41JXMN109186").toString());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
