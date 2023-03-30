// Author: Isaiah Daiz
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class DashboardAdminController implements Initializable {

    @FXML
    private TableView<Citation> citationTable;

    @FXML
    private TableColumn<Citation, LocalDateTime> dateIssuedColumn;

    @FXML
    private TableColumn<Citation, String> typeColumn;

    @FXML
    private ComboBox<String> itemTypeComboBox;

    @FXML
    private TableColumn<Citation, String> issuedByColumn;

    @FXML
    private TableColumn<Citation, Integer> citationIdColumn;

    @FXML
    private Button refreshButton;

    @FXML
    private void handleRefreshButton() throws SQLException {
        // Clear current data in the table
        citationTable.getItems().clear();

        // Retrieve new data from the database
        List<Citation> citations = Citation.getAllCitations();

        // Populate the table with the new data
        citationTable.getItems().addAll(citations);
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
                        // code to handle vehicle item creation
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

        try {
            // get all citations from the database
            List<Citation> citations = Citation.getAllCitations();

            // populate the table with the citations
            citationTable.getItems().addAll(citations);

            // add listener for double click
            citationTable.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    Citation citation = citationTable.getSelectionModel().getSelectedItem();
                    if (citation != null) {
                        try {
                            // open ModifyCitation screen for selected citation
                            Load load = new Load();
                            load.modifyCitation(citation.getCitationID());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
