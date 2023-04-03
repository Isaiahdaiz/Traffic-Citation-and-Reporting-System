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
        user = authModel.AuthenticateUser(user);

        if (user.isValid) {
            if (user.getType().toUpperCase().equals("LOCAL")) {
                App.setRoot("DashboardLocal");
            } else if (user.getType().toUpperCase().equals("PROVINCIAL")) {
                App.setRoot("dashboardProvincial");
            }
        } else {
            loginValidation.setTextFill(Color.RED);
            loginValidation.setVisible(true);
        }

    }
}
