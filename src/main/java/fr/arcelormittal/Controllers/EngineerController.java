package fr.arcelormittal.Controllers;

import fr.arcelormittal.Helpers.ApplicationHelper;
import fr.arcelormittal.Models.Stand;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EngineerController implements Initializable {

    private Stand selectedStand = null;

    @FXML
    private ListView<Stand> standListView;
    @FXML
    private Button standButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        standButton.setVisible(false);
        standButton.setDisable(true);
        setListView();
    }

    @FXML
    private void onListItemsClick(MouseEvent mouseEvent) {
        selectedStand = standListView.getSelectionModel().getSelectedItem();
        standButton.setVisible(true);
        standButton.setDisable(false);
        if (selectedStand.isActive()) standButton.setText("Désactiver");
        else standButton.setText("Activer");
    }

    @FXML
    private void onButtonStandClick(ActionEvent actionEvent){
        assert selectedStand != null;
        selectedStand.setActive(!selectedStand.isActive());
        ApplicationHelper.updateStand(selectedStand);
        setListView();
    }

    private void setListView(){
        try {
            standListView.setItems(FXCollections.observableArrayList(ApplicationHelper.getStands()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
