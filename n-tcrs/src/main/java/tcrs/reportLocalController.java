package tcrs;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
        //TO DO generate report
        App.setRoot("DashboardLocal");
    }
}
