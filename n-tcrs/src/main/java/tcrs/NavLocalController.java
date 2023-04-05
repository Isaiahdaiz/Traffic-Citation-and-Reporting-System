// Author: Isaiah Daiz
package tcrs;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class NavLocalController {

    @FXML
    private HBox navbar;

    @FXML
    private Label title;

    @FXML
    private Button homeButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button reportsButton;

    @FXML
    private Button logoutButton;

    public void initialize() {
        // Add event handlers or other initialization code here
    }

    @FXML
    private void handleHomeButton() throws IOException {
        App.setCenter("DashboardLocal");
    }

    @FXML
    private void handleSearchButton() throws IOException {
        App.setCenter("SearchAll");
    }

    @FXML
    private void handleReportsButton() {
        // Add code to handle the Reports button here
    }

    @FXML
    private void handleLogoutButton() {
        // Add code to handle the Logout button here
    }

    // Add additional methods here as needed
}
