package tcrs;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class modifyUserController {
    @FXML ComboBox<String> typeStatus;
    @FXML ComboBox<String> typeAccount;
    @FXML TextField usernamTextField;

    @FXML
    public void switchToSettings() throws IOException {
        App.setRoot("dashboardAdmin");
    }

    @FXML
    public void switchToLogout() throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void deleteCurrentUser() {

    }

    @FXML
    public void saveUserInfo() {

    }
}
