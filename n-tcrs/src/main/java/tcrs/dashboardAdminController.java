package tcrs;

//Author David G

import java.io.IOException;

import javafx.fxml.FXML;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

public class dashboardAdminController {




    @FXML
    public void switchToCreateUser() throws IOException {
        App.setRoot("createUser");
    }

    @FXML
    public void switchToSettings() throws IOException {
        App.setRoot("dashboardAdmin");
    }

    @FXML
    public void switchToLogout() throws IOException {
        App.setRoot("login");
    }

}
