// Author: Isaiah Daiz
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.*;

public class SearchAllController {
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

    public void initialize() {
        // This function is intentionally left blank
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
                    Load load = new Load();
                    load.modifyCitation(citationIdInt);
                } else
                    System.out.println("Citation " + citationIdInt + " Not Found");
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

    }

    @FXML
    private void handleVINSearchButton() {
        // code to handle search by VIN
        String regex = "[0-9A-Z]{17}"; // 2T2K1E56A12345674
    }
}
