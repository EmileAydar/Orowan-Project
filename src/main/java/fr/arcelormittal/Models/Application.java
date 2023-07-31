package fr.arcelormittal.Models;

import fr.arcelormittal.Helpers.ApplicationHelper;
import fr.arcelormittal.Managers.DAOManager;
import fr.arcelormittal.Managers.FileManager;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Application {

    private static Application instance = null;
    private User user = null;
    private List<User> userList = null;
    private Stand stand = null;
    private List<Stand> standList = null;
    private Timer timer = null;
    private TimerTask task = null;
    private XYChart.Series<Number, Number> frictionSeries;
    private XYChart.Series<Number, Number> sigmaSeries;
    private XYChart.Series<Number, Number> rollSpeedSeries;
    private int time = 0;

    private Application() throws IOException {
        userList = ApplicationHelper.getUsers();
        standList = ApplicationHelper.getStands();
    }

    public static Application getInstance() {
        try {
            if (instance == null) {
                instance = new Application();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stand getStand() {
        return stand;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public void startTask(Label computeLabel, LineChart frictionChart, LineChart sigmaChart, LineChart rollSpeedChart){
        this.timer = new Timer();

        this.task = new TimerTask() {
            private int count = 0;
            Instant start = Instant.now();
            @Override
            public void run() {
                try {
                    frictionSeries = new XYChart.Series<>();
                    sigmaSeries = new XYChart.Series<>();
                    rollSpeedSeries = new XYChart.Series<>();
                    if (count == 5) {
                        count = 0;
                        time ++;
                        ApplicationHelper.computeMean();
                        showCharts(frictionChart,sigmaChart,rollSpeedChart);
                        System.out.println("Mean!");
                    }
                    count++;
                    Instant end = Instant.now();
                    Duration time = Duration.between(start, end);
                    FileManager.getInstance().initCompute();
                    ApplicationHelper.orowanCompute();
                    FileManager.getInstance().readOutput();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(task,0,200);
    }

    private void showCharts(LineChart frictionChart, LineChart sigmaChart, LineChart rollSpeedChart){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                frictionSeries.getData().clear();
                sigmaSeries.getData().clear();
                rollSpeedSeries.getData().clear();
                try {
                    double[] frictionValues = DAOManager.getInstance().getFriction();
                    double[] sigmaValues = DAOManager.getInstance().getSigma();
                    double[] rollSpeedValues = DAOManager.getInstance().getRollSpeed();
                    for (int i = 1; i < frictionValues.length; i++) {
                        frictionSeries.getData().add(new XYChart.Data(i,frictionValues[i]));
                        sigmaSeries.getData().add(new XYChart.Data<>(i,sigmaValues[i]));
                        rollSpeedSeries.getData().add(new XYChart.Data<>(i,rollSpeedValues[i]));
                    }
                    frictionChart.getData().add(frictionSeries);
                    sigmaChart.getData().add(sigmaSeries);
                    rollSpeedChart.getData().add(rollSpeedSeries);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void endTask(){
        timer.cancel();
        this.timer = null;
        this.task = null;
    }
}
