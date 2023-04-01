// Author: Isaiah Daiz
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NewCitationController {
    @FXML
    private Label citationIdLabel;

    @FXML
    private Label dateTimeLabel;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField customTypeTextField;

    @FXML
    private TextField driverLicenseNumberTextField;

    @FXML
    private Label driverLicenseNumberErrorLabel;

    @FXML
    private TextField licensePlateNumberTextField;

    @FXML
    private Label licensePlateNumberErrorLabel;

    @FXML
    private TextField vehicleIdTextField;

    @FXML
    private Label vehicleIdErrorLabel;

    @FXML
    private TextField fineAmountTextArea;

    @FXML
    private Label fineAmountErrorLabel;

    @FXML
    private TextArea notesTextArea;

    @FXML
    private Label trafficSchoolLabel;

    @FXML
    private Button bookButton;
 
    @FXML
    private Button submitButton;

    private Citation citation = new Citation();

    public void initialize() {
        // get next citation ID, pad left zeroes
        citationIdLabel.setText(String.format("%0" + 8 + "d", Citation.getNextCitationID()));
        dateTimeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        typeComboBox.getItems().addAll("Parking Violation", "Moving Vehicle Violation", "Fix-it Ticket");
        // Set the value of the combo box to the first item in the list
        typeComboBox.setValue(typeComboBox.getItems().get(0));
        System.out.println(typeComboBox.getValue());
        // hide/show traffic school section
        typeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Moving Vehicle Violation")) {
                trafficSchoolLabel.setVisible(true);
                bookButton.setVisible(true);
            } else {
                trafficSchoolLabel.setVisible(false);
                bookButton.setVisible(false);
            }
        });
        fineAmountTextArea.setText("0.00");
        bookButton.setOnAction(event -> {
            try {
                handleBookButton();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        submitButton.setOnAction(event -> {
            try {
                handleSubmitButton();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleBookButton() throws IOException, SQLException {
        driverLicenseNumberErrorLabel.setVisible(false);
        driverLicenseNumberErrorLabel.setText("*Invalid Input");
        if (driverLicenseNumberTextField.getText().isEmpty() || !driverLicenseNumberTextField.getText().matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
            driverLicenseNumberErrorLabel.setVisible(true);
            return;
        }
        if (!Citation.dLNumberExists(driverLicenseNumberTextField.getText())) {
            driverLicenseNumberErrorLabel.setText("*Driver does not exist");
            driverLicenseNumberErrorLabel.setVisible(true);
            return;
        }
        Load load = new Load();
        load.trafficSchool(citation);
    }

    @FXML
    private void handleSubmitButton() throws SQLException {
        // Check if fields are correct
        if (!validSubmission()) {
            System.out.println("Inputs invalid");
            return;
        }

         // Check if foreign keys exists
         if (!Citation.dLNumberExists(driverLicenseNumberTextField.getText())) {
            driverLicenseNumberErrorLabel.setText("*Driver does not exist");
            driverLicenseNumberErrorLabel.setVisible(true);
            System.out.println("Driver does not exist");
            return;
        } 
            driverLicenseNumberErrorLabel.setText("*Invalid Input");
            driverLicenseNumberErrorLabel.setVisible(false);
        
        if (!Citation.vinExists(vehicleIdTextField.getText())) {
            vehicleIdErrorLabel.setText("*Vehicle does not exist");
            vehicleIdErrorLabel.setVisible(true);
            System.out.println("VIN does not exist");
            return;
        }
        vehicleIdErrorLabel.setText("*Invalid Input");
        vehicleIdErrorLabel.setVisible(false);

        // Create a new Citation object with the values entered in the form fields
        citation.setOfficer("John Smith"); // ** This needs to be replaced with user that logged in
        citation.setDate(LocalDateTime.now());
        citation.setType(typeComboBox.getValue()); // Replace with actual value from form field
        citation.setDLNumber(driverLicenseNumberTextField.getText());
        citation.setVIN(vehicleIdTextField.getText());
        citation.setFineAmount(Double.parseDouble(fineAmountTextArea.getText())); // Replace with actual value from form
        citation.setPaymentStatus("Unpaid");
        citation.setTrafficSchool("None");
        citation.setNotes(notesTextArea.getText());
        try {
            citation.save(); // Save the citation to the database
            System.out.println("Citation saved successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Citation save unsuccessful");
        }
    }

    private boolean validSubmission() {
        boolean isValid = true;

        String regex1 = "\\d{4}-\\d{4}-\\d{4}-\\d{4}"; // ####-####-####-####
        String regex2 = "[A-Z]{4} \\d{3}"; // AAAA ###
        String regex3 = "[0-9A-Z]{17}"; // 2T2K1E56A12345674
        String regex4 = "^\\d+(\\.\\d{1,2})?$"; // ..###.##

        // Convert text fields to uppercase
        licensePlateNumberTextField.setText(licensePlateNumberTextField.getText().toUpperCase());

        // Driver's License Validation
        if (driverLicenseNumberTextField.getText() == null || driverLicenseNumberTextField.getText().isEmpty() || !driverLicenseNumberTextField.getText().matches(regex1)) {
            driverLicenseNumberErrorLabel.setText("*Invalid Input");
            driverLicenseNumberErrorLabel.setVisible(true);
            isValid = false;
        } else {
            driverLicenseNumberErrorLabel.setVisible(false);
        }
        // License Plate Validation
        if (licensePlateNumberTextField.getText() == null || licensePlateNumberTextField.getText().isEmpty() || !licensePlateNumberTextField.getText().matches(regex2)) {
            licensePlateNumberErrorLabel.setText("*Invalid Input");
            licensePlateNumberErrorLabel.setVisible(true);
            isValid = false;
        } else {
            licensePlateNumberErrorLabel.setVisible(false);

        }
            
        // VIN Validation
        if (vehicleIdTextField.getText() == null || vehicleIdTextField.getText().isEmpty() || !vehicleIdTextField.getText().matches(regex3)) {
            vehicleIdErrorLabel.setText("*Invalid Input");
            vehicleIdErrorLabel.setVisible(true);
            isValid = false;
        } else {
            vehicleIdErrorLabel.setVisible(false);
        }

        // Fine Amount Validation
        if (fineAmountTextArea.getText() == null || fineAmountTextArea.getText().isEmpty() || !fineAmountTextArea.getText().matches(regex4)) {
            fineAmountErrorLabel.setText("*Invalid Input");
            fineAmountErrorLabel.setVisible(true);
            isValid = false;
        } else {
            fineAmountErrorLabel.setVisible(false);

        }
        return isValid;
    }
        
    
}
