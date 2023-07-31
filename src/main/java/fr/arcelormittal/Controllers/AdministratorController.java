package fr.arcelormittal.Controllers;

import fr.arcelormittal.Helpers.ApplicationHelper;
import fr.arcelormittal.Managers.DAOManager;
import fr.arcelormittal.Models.Application;
import fr.arcelormittal.Models.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorController implements Initializable {

    @FXML
    private VBox updateVBox;
    @FXML
    private ChoiceBox<String> roleChoiceBox, updateChoiceBox;
    @FXML
    private TextField nameField, emailField, updateName, updateEmail, updatePassword;
    @FXML
    private ListView<User> usersListView;
    @FXML
    private Label idLabel;

    private User selectedUser = null;
    private String[] roles = {"Worker", "Process Engineer", "Administrator"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleChoiceBox.getItems().addAll(roles);
        updateChoiceBox.getItems().addAll(roles);
        updateVBox.setVisible(false);
        updateVBox.setDisable(true);
        setListView();
    }

    @FXML
    private void onAjouterClick(ActionEvent actionEvent) throws IOException {
        String name = nameField.getText();
        String email = emailField.getText();
        String role = roleChoiceBox.getValue();
        DAOManager.getInstance().addUser(name,email,role);
        setListView();
    }

    @FXML
    private void onListItemsClick(MouseEvent mouseEvent) {
        this.selectedUser = usersListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            updateVBox.setDisable(false);
            idLabel.setText(String.valueOf(selectedUser.getId()));
            updateName.setText(selectedUser.getName());
            updateEmail.setText(selectedUser.getEmail());
            updatePassword.setText(selectedUser.getPassword());
            updateChoiceBox.setValue(selectedUser.getRole());
            updateVBox.setVisible(true);
        }
    }

    @FXML
    private void onUpdateClick(ActionEvent actionEvent) {
        try {
            selectedUser.setName(updateName.getText());
            selectedUser.setEmail(updateEmail.getText());
            selectedUser.setRole(updateChoiceBox.getValue());
            selectedUser.setPassword(updatePassword.getText());
            ApplicationHelper.updateUser(selectedUser);
            setListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDeleteClick(ActionEvent actionEvent) {
        try {
            if (! selectedUser.getRole().equals("Administrateur")) {
                ApplicationHelper.deleteUser(selectedUser);
            }
            setListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListView() {
        try {
            usersListView.setItems(FXCollections.observableArrayList(ApplicationHelper.getUsers()));
            updateVBox.setVisible(false);
            updateVBox.setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}