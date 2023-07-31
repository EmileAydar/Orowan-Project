package fr.arcelormittal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXML/login.fxml"));
            Scene scene = new Scene(root, 520, 400);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Arcelor Mittal Login");
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, IOException {
        launch();
    }
}