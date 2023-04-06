// Author: Your Name
package tcrs;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CreateUserController {
    @FXML
    private Button submitButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField statusTextField;

    public void initialize() {
//        defaultStyle = dOBDatePicker.getStyle();
        submitButton.setOnAction(event -> {
            try {
                handleSubmitButton();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleSubmitButton() throws SQLException {
        // Check if fields are correct
        if (!validSubmission()) {
            System.out.println("Inputs invalid");
            return;
        }

        User user = new User(usernameTextField.getText(), passwordTextField.getText(), typeTextField.getText(), statusTextField.getText());

        try {
            user.saveUser(); // Save the driver to the database
            System.out.println("User saved successfully!");
            // close current window
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("User not saved");
        }
    }

    private boolean validSubmission() {
        boolean isValid = true;

        // Driver's License Validation
        if (usernameTextField.getText() == null || usernameTextField.getText().isEmpty()
                || !usernameTextField.getText().matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
            usernameTextField.setVisible(true);
            isValid = false;
        } else {
            usernameTextField.setVisible(false);
        }

        // First Name Validation
        if (passwordTextField.getText() == null || passwordTextField.getText().isEmpty()) {
            passwordTextField.setVisible(true);
            isValid = false;
        } else {
            passwordTextField.setVisible(false);
        }

        // Last Name Validation
        if (typeTextField.getText() == null || typeTextField.getText().isEmpty()) {
            typeTextField.setVisible(true);
            isValid = false;
        } else {
            typeTextField.setVisible(false);
        }

        // Last Name Validation
        if (statusTextField.getText() == null || statusTextField.getText().isEmpty()) {
            statusTextField.setVisible(true);
            isValid = false;
        } else {
            statusTextField.setVisible(false);
        }

        return isValid;
    }
}
