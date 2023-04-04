package tcrs;

//Author David G

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

//import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;

public class dashboardAdminController {





    public void switchToCreateUser() throws IOException {
        App.setRoot("createUser");
    }

    public void switchToSettings() throws IOException {
        App.setRoot("dashboardAdmin");
    }

    public void switchToLogout() throws IOException {
        App.setRoot("login");
    }

}
