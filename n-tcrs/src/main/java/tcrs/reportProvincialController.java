package tcrs;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

public class reportProvincialController {
    @FXML Label currentDate;
    @FXML DatePicker startDate;
    @FXML DatePicker endDate;
    @FXML TextArea notes;
    @FXML TextField nameTextField;

    //TO DO - get data from startDate, endDate, nameTextField to query database to generate report. Notes will be added to report after query successful

    public void initialize () {
        currentDate.setText("Current Report: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    @FXML
    public void switchToSearch() throws IOException {
        App.setRoot("dashboardProvincial");
    }

    @FXML
    public void switchToLogout() throws IOException{
        App.setRoot("login");
    }

    @FXML
    public void switchToReports() throws IOException {
        App.setRoot("reportProvincial");
    }

    //Submit button clicked - generate report
    @FXML void switchToDashboard() throws Exception {

        reportLocalController report = new reportLocalController();
        report.getCitationReport(String.valueOf(startDate.getValue()),String.valueOf(endDate.getValue()));
        //TO DO generate report
        App.setRoot("dashboardProvincial");
    }
}
