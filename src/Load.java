// Author: Isaiah Daiz
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Load {
    private Scene previousScene;
    // Load Modify citation screen
    // public void modifyCitation(int citationID) throws IOException {
    //     FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyCitation.fxml"));
    //     Parent root = loader.load();
    //     ModifyCitationController controller = loader.getController();
    //     controller.initialize(citationID);
    //     Stage stage = new Stage();
    //     stage.setScene(new Scene(root));
    //     stage.show();
    // }
    public void modifyCitation(int citationID, Stage currentStage) throws IOException {
        previousScene = currentStage.getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyCitation.fxml"));
        Parent root = loader.load();
        ModifyCitationController controller = loader.getController();
        controller.initialize(citationID);
        currentStage.setScene(new Scene(root));
    }
    // Load New Citation
    public void newCitation() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewCitation.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void modifyVehicle(String vehicleId, Stage currentStage) throws IOException {
        previousScene = currentStage.getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyVehicle.fxml"));
        Parent root = loader.load();
        ModifyVehicleController controller = loader.getController();
        controller.initialize(vehicleId);
        currentStage.setScene(new Scene(root));
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
    public void trafficSchool(Citation citation) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TrafficSchool.fxml"));
        Parent root = loader.load();
        TrafficSchoolController controller = loader.getController();
        controller.initialize(citation);

        controller.setCallback(data -> {
            citation.setTrafficSchool(data);
            System.out.println("Data received: " + data);
        });


        Stage stage = new Stage();
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

