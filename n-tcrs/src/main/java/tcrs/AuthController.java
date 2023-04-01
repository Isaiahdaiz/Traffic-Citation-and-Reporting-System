package tcrs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.Authenticator;
import java.sql.SQLException;

public class AuthController {

    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    Label loginValidation;


    @FXML
    private void handleLoginButtonAction() throws IOException, SQLException {

        AuthModel authModel = new AuthModel();
        User user = new User(usernameField.getText(), passwordField.getText());
        boolean isUserValid = authModel.AuthenticateUser(user.getUsername(), user.getPassword());
        if (isUserValid) {
            App.setRoot("primary");
        } else {
            loginValidation.setTextFill(Color.RED);
            loginValidation.setVisible(true);
        }

    }
}
