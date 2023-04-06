package tcrs;

//Author David G

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

public class dashboardAdminController implements Initializable {
    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, String> userColumn;

    @FXML
    private TableColumn<User, String> typeColumn;

    @FXML
    private TableColumn<User, String> statusColumn;

    @FXML
    public void switchToCreateUser() throws IOException {
        App.setRoot("newUser");
    }

    @FXML
    public void switchToSettings() throws IOException {
        App.setRoot("dashboardAdmin");
    }

    @FXML
    public void switchToLogout() throws IOException {
        App.setRoot("login");
    }

    public void initialize(URL location, ResourceBundle recourses) {
        // populate table
        userColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            // get all users from the database
            List<User> users = User.getAllUsers();
            System.out.println("Users" + users.toString());
            // populate table with users
            userTableView.getItems().addAll(users);


        // add listener for double click on userTable
        userTableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                User user = userTableView.getSelectionModel().getSelectedItem();
                try {
                    if (user != null && User.searchUser(user.getUsername()) != null) {
                        Load load = new Load();
                        Node node = (Node) event.getSource();
                        Stage currentStage = (Stage) node.getScene().getWindow();

                        load.modifyUser(user.getUsername(), currentStage);
                    } else {
                        refreshTable();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() throws Exception {
        // Clear current data in table
        userTableView.getItems().clear();
        
        // Retrieve new data from Database
        List<User> users = User.getAllUsers();

        // Populate table with new data
        userTableView.getItems().addAll(users);
    }
}
