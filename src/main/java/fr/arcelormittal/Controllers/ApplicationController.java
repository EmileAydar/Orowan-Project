package fr.arcelormittal.Controllers;

import fr.arcelormittal.Models.Application;
import fr.arcelormittal.Models.FxmlLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    @FXML
    private BorderPane mainPane;
    @FXML
    private Label userName, standLabel;
    @FXML
    private Button AdministratorButton, WorkerButton, EngineerButton, SettingsButton;


    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assert Application.getInstance().getUser() != null;
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("FXML/worker");
        mainPane.setCenter(view);
        userName.setText(Application.getInstance().getUser().getName());
        if (Application.getInstance().getStand() != null) standLabel.setText("Stand " + Application.getInstance().getStand().getId());
        if (Application.getInstance().getUser().getRole().equals("Worker")) {
            AdministratorButton.setDisable(true);
            EngineerButton.setDisable(true);
            AdministratorButton.setVisible(false);
        }
        if (Application.getInstance().getUser().getRole().equals("Process Engineer")){
            AdministratorButton.setDisable(true);
            AdministratorButton.setVisible(false);
        }
    }

    @FXML
    private void onWorkerClick(ActionEvent actionEvent) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("FXML/worker");
        mainPane.setCenter(view);
    }

    @FXML
    private void onEngineerClick(ActionEvent actionEvent) {
        String role = Application.getInstance().getUser().getRole();
        if (role.equals("Process Engineer") || role.equals("Administrateur")) {
            FxmlLoader object = new FxmlLoader();
            Pane view = object.getPage("FXML/engineer");
            mainPane.setCenter(view);
        }
    }

    @FXML
    private void onSettingsClick(ActionEvent actionEvent) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("FXML/settings");
        mainPane.setCenter(view);
    }

    @FXML
    private void onAdministratorClick(ActionEvent actionEvent) {
        if (Application.getInstance().getUser().getRole().equals("Administrateur")){
            FxmlLoader object = new FxmlLoader();
            Pane view = object.getPage("FXML/administrator");
            mainPane.setCenter(view);
        } else {
            System.out.println(Application.getInstance().getUser().getRole());
        }
    }

    @FXML
    private void onLogoutClick(ActionEvent actionEvent) throws IOException {
        Application.getInstance().setUser(null);
        switchScene(actionEvent);
    }

    private void switchScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./src/main/resources/fr/arcelormittal/FXML/login.fxml"));
        Parent root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Orowan-Login");
        stage.setWidth(520);
        stage.setHeight(400);
        stage.show();
    }
}