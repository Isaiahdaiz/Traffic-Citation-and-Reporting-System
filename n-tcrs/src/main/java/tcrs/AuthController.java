package tcrs;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.Authenticator;
import java.sql.SQLException;

public class AuthController {

    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;


    @FXML
    private void handleLoginButtonAction() throws IOException, SQLException {

        AuthModel authModel = new AuthModel();
        User user = new User(usernameField.getText(), passwordField.getText());
        boolean isUserValid = authModel.AuthenticateUser(user.getUsername(), user.getPassword());
        if (isUserValid) {
            App.setRoot("primary");
        }

    }
}
