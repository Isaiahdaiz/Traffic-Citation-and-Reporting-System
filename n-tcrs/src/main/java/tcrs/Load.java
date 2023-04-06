// Author: Isaiah Daiz
package tcrs;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Load {
    private Scene previousScene;
    // Load Modify citation screen
    // public void modifyCitation(int citationID) throws IOException {
    // FXMLLoader loader = new
    // FXMLLoader(getClass().getResource("ModifyCitation.fxml"));
    // Parent root = loader.load();
    // ModifyCitationController controller = loader.getController();
    // controller.initialize(citationID);
    // Stage stage = new Stage();
    // stage.setScene(new Scene(root));
    // stage.show();
    // }

    public void modifyCitation(int citationID, Stage currentStage) throws IOException {
        // previousScene = currentStage.getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyCitation.fxml"));
        Parent root = loader.load();
        ModifyCitationController controller = loader.getController();
        Stage stage = new Stage();
        controller.initialize(citationID);
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Load New Citation
    public void newCitation() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewCitation.fxml"));
        Parent root = loader.load();
        NewCitationController controller = loader.getController();
        Stage stage = new Stage();
        controller.initialize(AuthController.user.getUsername());
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Load New Citation
    public void newDriver() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewDriver.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Modify Driver
    public void modifyDriver(String dLNUmber, Stage currentStage) throws IOException {
        previousScene = currentStage.getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyDriver.fxml"));
        Parent root = loader.load();
        ModifyDriverController controller = loader.getController();
        Stage stage = new Stage();
        controller.initialize(dLNUmber);
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Modify Vehicle
    public void modifyVehicle(String vehicleId, Stage currentStage) throws IOException {
        previousScene = currentStage.getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyVehicle.fxml"));
        Parent root = loader.load();
        ModifyVehicleController controller = loader.getController();
        Stage stage = new Stage();
        controller.initialize(vehicleId);
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Load New Vehicle
    public void newVehicle() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewVehicle.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Load Modify citation screen
    public void trafficSchool(int citationID, String dlNumber) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TrafficSchool.fxml"));
        Parent root = loader.load();
        TrafficSchoolController controller = loader.getController();
        controller.initialize(citationID, dlNumber);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Modify Vehicle
    public void modifyUser(String username, Stage currentStage) throws Exception {
        previousScene = currentStage.getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyUser.fxml"));
        Parent root = loader.load();
        modifyUserController controller = loader.getController();
        Stage stage = new Stage();
        controller.initialize(username);
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Method to switch back to the previous scene
    public void goBack(Stage currentStage) {
        if (previousScene != null) {
            currentStage.setScene(previousScene);
        }
    }
}
