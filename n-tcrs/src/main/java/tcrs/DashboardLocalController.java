// Author: Isaiah Daiz

package tcrs;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import java.time.format.FormatStyle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class DashboardLocalController implements Initializable {

    @FXML
    private TableView<Citation> citationTable;

    @FXML
    private TableColumn<Citation, LocalDateTime> dateIssuedColumn;

    @FXML
    private TableColumn<Citation, String> typeColumn;

    @FXML
    private TableColumn<Citation, String> paymentStatusColumn;

    @FXML
    private ComboBox<String> itemTypeComboBox;

    @FXML
    private TableColumn<Citation, String> issuedByColumn;

    @FXML
    private TableColumn<Citation, Integer> citationIdColumn;

    @FXML
    private TableView<Driver> driverTable;

    @FXML
    private TableColumn<Driver, String> dLColumn;

    @FXML
    private TableColumn<Driver, String> firstNameColumn;

    @FXML
    private TableColumn<Driver, String> lastNameColumn;

    @FXML
    private TableColumn<Driver, String> licenseColumn;

    @FXML
    private TableView<Vehicle> vehicleTable;

    @FXML
    private TableColumn<Vehicle, String> makeColumn;

    @FXML
    private TableColumn<Vehicle, String> modelColumn;

    @FXML
    private TableColumn<Vehicle, String> yearColumn;

    @FXML
    private TableColumn<Vehicle, String> vINColumn;

    @FXML
    private TableColumn<Vehicle, String> registrationStatusColumn;

    @FXML
    private Button refreshButton;
    @FXML
    private Label welcomeTitle;

    @FXML
    private TextField citationIDTextField;

    @FXML
    private Label citationIDErrorLabel;

    @FXML
    private Button citationIDSearch;

    @FXML
    private TextField driverLicenseNumberTextField;

    @FXML
    private Label driverLicenseNumberErrorLabel;

    @FXML
    private Button driverLicenseNumberSearch;

    @FXML
    private TextField vehicleIdTextField;

    @FXML
    private Label vehicleIdErrorLabel;

    @FXML
    private Button vehicleIdSearch;

    @FXML
    private void handleRefreshButton() throws Exception {
        refreshTable();
    }

    public void initialize(URL location, ResourceBundle resources) {

        welcomeTitle.setText(welcomeTitle.getText() + " " + AuthController.user.getUsername());

        // Combo Box
        itemTypeComboBox.getItems().addAll("Citation", "Driver", "Vehicle");
        itemTypeComboBox.setOnAction(event -> {
            String selectedItemType = itemTypeComboBox.getValue();
            if (selectedItemType != null) {
                Load load = new Load();
                switch (selectedItemType) {
                    case "Citation":
                        try {
                            load.newCitation();
                            itemTypeComboBox.setPromptText("Create New");
                            itemTypeComboBox.setValue(null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Vehicle":
                        try {
                            load.newVehicle();
                            itemTypeComboBox.setPromptText("Create New");
                            itemTypeComboBox.setValue(null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Driver":
                        try {
                            load.newDriver();
                            itemTypeComboBox.setPromptText("Create New");
                            itemTypeComboBox.setValue(null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });

        // set up columns
        citationIdColumn.setCellValueFactory(new PropertyValueFactory<>("citationID"));
        citationIdColumn.setCellFactory(column -> new TextFieldTableCell<>(new StringConverter<>() {
            @Override
            public String toString(Integer integer) {
                return String.format("%08d", integer);
            }
        
            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }
        }));
        dateIssuedColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        // Define the date and time format
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

        // Set up custom cell factory for the dateIssuedColumn
        dateIssuedColumn.setCellFactory(column -> new TableCell<Citation, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime date, boolean empty) {
                super.updateItem(date, empty);

                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(formatter.format(date));
                }
            }
        });
        issuedByColumn.setCellValueFactory(new PropertyValueFactory<>("officer"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        paymentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));

        // set up columns
        dLColumn.setCellValueFactory(new PropertyValueFactory<>("dLNumber"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        licenseColumn.setCellValueFactory(new PropertyValueFactory<>("licenseStatus"));

        // set up columns for vehicleTable
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        vINColumn.setCellValueFactory(new PropertyValueFactory<>("VIN"));
        registrationStatusColumn.setCellValueFactory(new PropertyValueFactory<>("registrationStatus"));

        try {
            // get all citations from the database
            List<Citation> citations = Citation.getAllCitations();

            // populate the table with the citations
            citationTable.getItems().addAll(citations);

            // get all drivers from database
            List<Driver> drivers = Driver.getAllDrivers();

            // populate the table with the citations
            driverTable.getItems().addAll(drivers);

            // get all vehicles from the database
            List<Vehicle> vehicles = Vehicle.getAllVehicles();

            // populate the vehicleTable with the vehicles
            vehicleTable.getItems().addAll(vehicles);

            // add listener for double click
            citationTable.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Citation citation = citationTable.getSelectionModel().getSelectedItem();
                    try {
                        if (citation != null && Citation.citationIdExists(citation.getCitationID())) {
                            Load load = new Load();
                            Node node = (Node) event.getSource();
                            Stage currentStage = (Stage) node.getScene().getWindow();

                            load.modifyCitation(citation.getCitationID(), currentStage);
                        } else {
                            refreshTable();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            // add listener for double click
            driverTable.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Driver driver = driverTable.getSelectionModel().getSelectedItem();
                    try {
                        if (driver != null && Driver.searchDriver(driver.getDLNumber()) != null) {
                            Load load = new Load();
                            Node node = (Node) event.getSource();
                            Stage currentStage = (Stage) node.getScene().getWindow();

                            load.modifyDriver(driver.getDLNumber(), currentStage);
                        } else {
                            refreshTable();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            // add listener for double click on vehicleTable
            vehicleTable.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Vehicle vehicle = vehicleTable.getSelectionModel().getSelectedItem();
                    try {
                        if (vehicle != null && Vehicle.searchVehicle(vehicle.getVIN()) != null) {
                            Load load = new Load();
                            Node node = (Node) event.getSource();
                            Stage currentStage = (Stage) node.getScene().getWindow();

                            load.modifyVehicle(vehicle.getVIN(), currentStage);
                        } else {
                            refreshTable();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() throws Exception {
        // Clear current data in the tables
        citationTable.getItems().clear();
        driverTable.getItems().clear();
        vehicleTable.getItems().clear();

        // Retrieve new data from the database
        List<Citation> citations = Citation.getAllCitations();
        List<Driver> drivers = Driver.getAllDrivers();
        List<Vehicle> vehicles = Vehicle.getAllVehicles();

        // Populate the tables with the new data
        citationTable.getItems().addAll(citations);
        driverTable.getItems().addAll(drivers);
        vehicleTable.getItems().addAll(vehicles);
    }

    @FXML
    public void switchToLogout() throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void switchToSettings() throws IOException {
        App.setRoot("DashboardLocal");
    }

    @FXML
    public void switchToSearch() throws IOException {
        App.setRoot("SearchAll");
    }

    @FXML
    public void switchToReports() throws IOException{
        App.setRoot("reportLocal");
    }

    @FXML
    private void handleCitaionIDSearchButton() throws IOException, SQLException {
        // code to handle search by Citation ID
        String regex = "\\d{8}"; // ########
        if (citationIDTextField.getText() == null || citationIDTextField.getText().isEmpty()
                || !citationIDTextField.getText().matches(regex)) {
            citationIDErrorLabel.setText("*Invalid Input");
            citationIDErrorLabel.setVisible(true);
        } else {
            citationIDErrorLabel.setVisible(false);
            if (!Citation.citationIdExists(Integer.parseInt(citationIDTextField.getText()))) {
                citationIDErrorLabel.setText("*Citation does not exist");
                citationIDErrorLabel.setVisible(true);
                System.out.println("Citation does not exist");
                return;
            }
            try {
                int citationIdInt = Integer.parseInt(citationIDTextField.getText());
                Citation citation = Citation.searchCitation(citationIdInt);
                if (citation != null) {
                    System.out.println("Citation found \n" + citation.toString());
                    Load load = new Load(); // Get the current stage and pass it to the modifyCitation method
                    Stage currentStage = (Stage) citationIDTextField.getScene().getWindow();
                    load.modifyCitation(citationIdInt, currentStage);
                } else
                    System.out.println("Citation " + citationIdInt + " Not Found");
                citationIDErrorLabel.setText("*Citation not found");
                citationIDErrorLabel.setVisible(true);
            } catch (SQLException e) {
                System.out.println("Error searching for citation");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDLNumberSearchtButton() {
        // code to handle search by DL number
        String regex = "\\d{4}-\\d{4}-\\d{4}-\\d{4}"; // ####-####-####-####
        if (driverLicenseNumberTextField.getText() == null || driverLicenseNumberTextField.getText().isEmpty()
                || !driverLicenseNumberTextField.getText().matches(regex)) {
            driverLicenseNumberErrorLabel.setText("*Invalid Input");
            driverLicenseNumberErrorLabel.setVisible(true);
        } else {
            driverLicenseNumberErrorLabel.setVisible(false);
            String dLNumber = driverLicenseNumberTextField.getText();
            try {
                Driver driver = Driver.searchDriver(dLNumber);
                if (driver != null) {
                    System.out.println("Driver found \n" + driver.toString());
                    Load load = new Load(); // Get the current stage and pass it to the modifyVehicle method
                    Stage currentStage = (Stage) driverLicenseNumberTextField.getScene().getWindow();
                    load.modifyDriver(dLNumber, currentStage);
                } else {
                    System.out.println("Driver " + driver + " Not Found");
                    driverLicenseNumberErrorLabel.setText("*Driver not found");
                    driverLicenseNumberErrorLabel.setVisible(true);
                }
            } catch (SQLException | IOException e) {
                System.out.println("Error searching for driver");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleVINSearchButton() {
        // code to handle search by VIN
        String regex = "[0-9A-Z]{17}"; // 2T2K1E56A12345674

        if (vehicleIdTextField.getText() == null || vehicleIdTextField.getText().isEmpty()
                || !vehicleIdTextField.getText().matches(regex)) {
            vehicleIdErrorLabel.setText("*Invalid Input");
            vehicleIdErrorLabel.setVisible(true);
        } else {
            vehicleIdErrorLabel.setVisible(false);
            String vin = vehicleIdTextField.getText();
            try {
                Vehicle vehicle = Vehicle.searchVehicle(vin);
                if (vehicle != null) {
                    System.out.println("Vehicle found \n" + vehicle.toString());
                    Load load = new Load(); // Get the current stage and pass it to the modifyVehicle method
                    Stage currentStage = (Stage) vehicleIdTextField.getScene().getWindow();
                    load.modifyVehicle(vin, currentStage);
                } else {
                    System.out.println("Vehicle " + vin + " Not Found");
                    vehicleIdErrorLabel.setText("*Vehicle not found");
                    vehicleIdErrorLabel.setVisible(true);
                }
            } catch (SQLException | IOException e) {
                System.out.println("Error searching for vehicle");
                e.printStackTrace();
            }
        }
    }
}
