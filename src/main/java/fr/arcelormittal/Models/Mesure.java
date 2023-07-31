package fr.arcelormittal.Models;

import fr.arcelormittal.Helpers.MesureHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class Mesure {

    private ArrayList<String> mesureValues = null;

    public Mesure(){
        mesureValues = new ArrayList();
    }

    public ArrayList<String> getValues(){
        return this.mesureValues;
    }

    public void addValue() throws SQLException, IOException {
        MesureHelper.addInput(mesureValues);
        MesureHelper.addOther(mesureValues);
    }
}
