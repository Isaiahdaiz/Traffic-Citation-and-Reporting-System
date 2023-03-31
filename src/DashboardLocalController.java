
// Author: Isaiah Daiz
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private void handleRefreshButton() throws SQLException {
        refreshTable();
    }

    public void initialize(URL location, ResourceBundle resources) {
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
                        // code to handle driver item creation
                        break;
                }
            }
        });

        // set up columns
        citationIdColumn.setCellValueFactory(new PropertyValueFactory<>("citationID"));
        dateIssuedColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        issuedByColumn.setCellValueFactory(new PropertyValueFactory<>("officer"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        paymentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));

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
                    } catch (IOException e) {
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() throws SQLException {
        // Clear current data in the tables
        citationTable.getItems().clear();
        vehicleTable.getItems().clear();
    
        // Retrieve new data from the database
        List<Citation> citations = Citation.getAllCitations();
        List<Vehicle> vehicles = Vehicle.getAllVehicles();
    
        // Populate the tables with the new data
        citationTable.getItems().addAll(citations);
        vehicleTable.getItems().addAll(vehicles);
    }
}
