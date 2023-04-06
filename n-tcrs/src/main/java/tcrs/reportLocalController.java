package tcrs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import static tcrs.AuthController.user;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

public class reportLocalController {
    @FXML Label currentDate;
    @FXML DatePicker startDate;
    @FXML DatePicker endDate;
    @FXML TextArea notes;
    @FXML TextField nameTextField;
    @FXML RadioButton yesWarrant;
    @FXML RadioButton noWarrant;
    @FXML TextField driverTextField;
    @FXML TextField licenseTextField;
    PreparedStatement preparedStatement = null;

    //TO DO - get data from startDate, endDate, nameTextField to query database to generate report. Notes will be added to report after query successful

    public void initialize () {
        currentDate.setText("Current Report: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    @FXML
    public void switchToSearch() throws IOException {
        App.setRoot("SearchAll");
    }

    @FXML
    public void switchToLogout() throws IOException{
        App.setRoot("login");
    }

    @FXML
    public void switchToReports() throws IOException {
        App.setRoot("reportLocal");
    }

    //Submit button clicked - generate report
    @FXML void switchToDashboard() throws IOException {
        try {
            getCitationReport(String.valueOf(startDate.getValue()),String.valueOf(endDate.getValue()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //TO DO generate report
        App.setRoot("DashboardLocal");
    }

    public void getCitationReport(String startDate, String endDate) throws Exception {
        List<Citation> citations = new ArrayList<>();

        try (Connection connection = DatabaseUtils.getConnection()) {
            String selectCitationsSql = "SELECT * FROM citations where Date >= ? and Date <= ?";

            if (nameTextField != null && !nameTextField.getText().trim().isEmpty()) {
                selectCitationsSql = "SELECT * FROM citations where Date >= ? and Date <= ? and Officer = ?";
                preparedStatement = connection.prepareStatement(selectCitationsSql);
                preparedStatement.setString(1, startDate);
                preparedStatement.setString(2, endDate);
                preparedStatement.setString(2, nameTextField.getText());
            } else {

                preparedStatement = connection.prepareStatement(selectCitationsSql);
                preparedStatement.setString(1, startDate);
                preparedStatement.setString(2, endDate);
            }
            //Statement statement = connection.createStatement();

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int citationId = resultSet.getInt("CitationID");
                String officer = resultSet.getString("Officer");
                String type = resultSet.getString("Type");
                String dlNumber = resultSet.getString("DLNumber");
                String vin = resultSet.getString("VIN");
                LocalDateTime date = resultSet.getTimestamp("Date").toLocalDateTime();
                double fineAmount = resultSet.getDouble("FineAmount");
                String paymentStatus = resultSet.getString("PaymentStatus");
                String trafficSchool = resultSet.getString("TrafficSchool");
                String notes = resultSet.getString("Notes");

                Citation citation = new Citation(officer, type, dlNumber, vin, date, fineAmount, paymentStatus,
                        trafficSchool, notes);
                citation.setCitationID(citationId);
                citations.add(citation);
            }
            exportToExcel(citations);
        }
        //return citations;
    }

    public void exportToExcel(List<Citation> citations) throws IOException {
        Date date = new Date();
        final String formatTemplate = "{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10}";
        FileWriter writer = new FileWriter("./n-tcrs/src/main/util/Report_" + date.getTime() +
                ".csv");
        BufferedWriter bw = new BufferedWriter(writer);

        final int size = citations.size();

        for (int i = 0; i < size; i++) {
            final String lineString = MessageFormat.format(formatTemplate, citations.get(i).getCitationID()
                    , citations.get(i).getType()
                    , citations.get(i).getOfficer()
                    , citations.get(i).getDLNumber()
                    , citations.get(i).getVIN()
                    , citations.get(i).getDate()
                    , citations.get(i).getFineAmount()
                    , citations.get(i).getPaymentStatus()
                    , citations.get(i).getTrafficSchool()
                    , citations.get(i).getNotes());

            bw.write(lineString);
            bw.newLine();
        }
        bw.close();
        writer.close();

    }



}
