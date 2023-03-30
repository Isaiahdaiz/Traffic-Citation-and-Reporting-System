// Author: Isaiah Daiz
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Load {
    // Load Modify citation screen
    public void modifyCitation(int citationID) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyCitation.fxml"));
        Parent root = loader.load();
        ModifyCitationController controller = loader.getController();
        controller.initialize(citationID);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    // Load New Citation
    public void newCitation() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewCitation.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}

