//Author David G
package tcrs;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

//import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;


public class dashboardProvincialController {
    @FXML private Button homeButton;
    @FXML private Button searchButton;
    @FXML private Button reportsButton;
    @FXML private Button logoutButton;
    @FXML private Label welcomeName;
    @FXML private TextField citationTextField;
    @FXML private TextField driverTextField;
    @FXML private Label invalidCitationText;
    @FXML private Label invalidDriver;
    @FXML private Label invalidVIN;
    @FXML private TextField vinTextField;

    public void initialize() {

        welcomeName.setText(welcomeName.getText() + " " + AuthController.user.getUsername());
        invalidCitationText.setVisible(false);
        invalidDriver.setVisible(false);
        invalidVIN.setVisible(false);
        /* 
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
    }

    public void switchToSearch() throws IOException {
        App.setRoot("dashboardProvincial");
    }

    public void switchToLogout() throws IOException{
        App.setRoot("login");
    }

    public void switchToReports() {
        //TO DO
    }

    @FXML
    public void citationSearch(ActionEvent event) throws IOException , SQLException{
        // code to handle search by Citation ID
        String regex = "\\d{8}"; // ########
        if (citationTextField.getText() == null || citationTextField.getText().isEmpty()
                || !citationTextField.getText().matches(regex)) {
            invalidCitationText.setText("*Invalid Input");
            invalidCitationText.setVisible(true);
        } else {
            invalidCitationText.setVisible(false);
            if (!Citation.citationIdExists(Integer.parseInt(citationTextField.getText()))) {
                invalidCitationText.setText("*Citation does not exist");
                invalidCitationText.setVisible(true);
                System.out.println("Citation does not exist");
                return;
            }
            App.setRoot("NewCitation");
            /* 
            try {
                int citationIdInt = Integer.parseInt(citationTextField.getText());
                Citation citation = Citation.searchCitation(citationIdInt);
                if (citation != null) {
                    System.out.println("Citation found \n" + citation.toString());
                    App.setRoot("NewCitation");
                } else
                    System.out.println("Citation " + citationIdInt + " Not Found");
            } catch (SQLException e) {
                System.out.println("Error searching for citation");
                e.printStackTrace();
            }
            */
        }
    }

    @FXML
    public void driverSearch() {
        //To do
    }

    @FXML
    public void vinSearch() {
        //To do
    }
}
