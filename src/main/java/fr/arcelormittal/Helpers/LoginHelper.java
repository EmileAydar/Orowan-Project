package fr.arcelormittal.Helpers;

import fr.arcelormittal.Managers.DAOManager;
import fr.arcelormittal.Models.Password;
import fr.arcelormittal.Models.Stand;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginHelper {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginHelper.class);

    public static boolean valideLogin(TextField mailField, PasswordField pwdField, Label loginMessage) throws IOException {
        boolean b = false;
        DAOManager connectNow = DAOManager.getInstance();
        Connection connectionDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM users WHERE" +
                " userMail = '" + mailField.getText()+ "' AND" +
                " hPwd = '" + Password.doHashing(pwdField.getText()) +"';";
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    LOGGER.info("Utilisateur {} connecté", mailField.getText());
                    b = true;
                } else {
                    loginMessage.setText("Invalid login. Please Try Again.");
                    b = false;
                }
            }
        } catch (Exception e){
            LOGGER.error("ERROR : {}", e.getCause());
        }
        resetField(mailField, pwdField);
        return b;
    }

    private static void resetField(TextField mailField, PasswordField pwdField){
        mailField.setText("");
        pwdField.setText("");
    }

    public static Stand getStand(int id) throws IOException {
        return DAOManager.getInstance().getStand(id);
    }

}
