package fr.arcelormittal.Managers;

import fr.arcelormittal.Models.Mesure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class FileManager {

    private static FileManager instance = null;
    private Scanner scSensor = null;
    private Scanner scOutput = null;

    public FileManager() throws FileNotFoundException {
        scSensor = new Scanner(new File("./src/main/resources/fr/arcelormittal/Orowan/Sensors/1939351_F2.txt"));
    }

    public static FileManager getInstance() throws FileNotFoundException {
        if (instance == null) instance = new FileManager();
        return instance;
    }

    public void initCompute() throws SQLException, IOException {
        readSensor();
        write();
    }

    private void readSensor() throws IOException, SQLException {
        DecimalFormat df = new DecimalFormat(".000");
        scSensor.useDelimiter(";");
        if (scSensor.hasNextLine()) {
            String line = scSensor.nextLine();
            if (!line.isEmpty()){
                Mesure m = new Mesure();
                String[] data = line.split("; ");
                for (int i = 0; i < data.length; i++) {
                    double d = Double.parseDouble(data[i].replace(",","."));
                    String value = df.format(d).replace(",",".");
                    m.getValues().add(value);
                }
                m.addValue();
            }
        } else {
            scSensor.close();
        }
    }

    public void readOutput() throws IOException {
        scOutput = new Scanner(new File("./src/main/resources/fr/arcelormittal/Orowan/Output/output.txt"));
        scOutput.useDelimiter("\n");
        while (scOutput.hasNextLine()){
            String line = scOutput.nextLine();
            if (!line.isEmpty()) {
                String[] data = line.split("\t");
                DAOManager.getInstance().addOuput(data);
            }
        }
        scOutput.close();
    }

    private void write(){
        try {
            FileWriter writer = new FileWriter("./src/main/resources/fr/arcelormittal/Orowan/Input/inv_cst.txt");
            writer.write("Cas\tHe\tHs\tTe\tTs\tDiam_WR\tWRyoung\toffset ini\tmu_ini\tForce\tG\n");
            writer.write(DAOManager.getInstance().getInput().replace(";","\t"));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
