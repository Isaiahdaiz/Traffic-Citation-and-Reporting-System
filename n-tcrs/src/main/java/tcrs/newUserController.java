package tcrs;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class newUserController {
    @FXML ComboBox<String> setAccountType;
    @FXML ComboBox<String> setAccountStatus;
    @FXML TextField passwordTextField;


    @FXML
    public void switchToSettings() throws IOException{
        App.setRoot("dashboardAdmin");
    }

    @FXML
    public void switchToLogout() throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void createUser() throws IOException {

    }
}
