package fr.arcelormittal.Controllers;

import fr.arcelormittal.Models.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkerController implements Initializable {

    @FXML
    private Button computeButton;
    private boolean isComputing = false;

    @FXML
    private Label computeLabel;

    @FXML
    private LineChart<Number,Number> frictionLineChart, sigmaLineChart, rollSpeedLineChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        computeLabel.setText("0s");
    }

    @FXML
    private void onComputeClick(MouseEvent mouseEvent) {
        isComputing = !isComputing;
        if (isComputing) {
            computeButton.setText("Stop");
            Application.getInstance().startTask(computeLabel, frictionLineChart, sigmaLineChart, rollSpeedLineChart);
        } else if (!isComputing) {
            computeButton.setText("Compute");
            Application.getInstance().endTask();
        }
    }
}
