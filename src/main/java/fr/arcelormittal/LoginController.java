package fr.arcelormittal;

import fr.arcelormittal.Helpers.ApplicationHelper;
import fr.arcelormittal.Helpers.LoginHelper;
import fr.arcelormittal.Models.Application;
import fr.arcelormittal.Models.Password;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class LoginController {

    @FXML
    private Label loginMessage;
    @FXML
    private TextField mailField;
    @FXML
    private PasswordField pwdField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void onLoginClick(ActionEvent event) throws IOException, SQLException {
        String mail = mailField.getText();
        String pwd = Password.doHashing(pwdField.getText());
        if (mail.isBlank() && pwd.isBlank()){
            loginMessage.setText("Enter your Mail & Password!");
        } else if (mail.isBlank() && !pwd.isBlank()){
            loginMessage.setText("Enter your Mail!");
        } else if (!mail.isBlank() && pwd.isBlank()){
            loginMessage.setText("Enter your Password!");
        } else {
            if(LoginHelper.valideLogin(mailField, pwdField, loginMessage)){
                Application.getInstance().setUser(ApplicationHelper.getUser(mail,pwd));
                Random rd = new Random();
                Application.getInstance().setStand(LoginHelper.getStand(rd.nextInt(8 + 1) + 1));
                switchScene(event);
            }
        }
    }

    private void switchScene(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/application.fxml"));
        Parent root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(960);
        stage.setHeight(540);
        stage.setTitle("Arcelor Mittal");
        stage.show();
    }
}