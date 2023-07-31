package fr.arcelormittal.Helpers;

import fr.arcelormittal.Managers.DAOManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MesureHelper {

    private final static Logger LOGGER = LoggerFactory.getLogger(MesureHelper.class);

    public static void addInput(ArrayList<String> mesureValues) throws IOException, SQLException {
        DAOManager connect = DAOManager.getInstance();
        Connection connectionDB = connect.getConnection();
        String sql = "INSERT INTO inputorowan" +
                "(Cas, He, Hs, Te, Ts, Diameter, WRYoung, offsetMesure, Mu, Strengh, G) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement pStmt = connectionDB.prepareStatement(sql);
        pStmt.setInt(1, Integer.parseInt(mesureValues.get(0).replace(".000","")));
        pStmt.setDouble(2, Double.parseDouble(mesureValues.get(4)));
        pStmt.setDouble(3, Double.parseDouble(mesureValues.get(5)));
        pStmt.setDouble(4, Double.parseDouble(mesureValues.get(6)));
        pStmt.setDouble(5, Double.parseDouble(mesureValues.get(7)));
        pStmt.setDouble(6, Double.parseDouble(mesureValues.get(8)));
        pStmt.setDouble(7, Double.parseDouble(mesureValues.get(9)));
        pStmt.setDouble(8, Double.parseDouble(mesureValues.get(10)));
        pStmt.setDouble(9, Double.parseDouble(mesureValues.get(12)));
        pStmt.setDouble(10, Double.parseDouble(mesureValues.get(15)));
        pStmt.setDouble(11, Double.parseDouble(mesureValues.get(17)));
        pStmt.executeUpdate();
        pStmt.close();
        LOGGER.info("INFO : Orowan Input Value Ajouté à la Database");
    }

    public static void addOther(ArrayList<String> mesureValues) throws IOException, SQLException {
        DAOManager connect = DAOManager.getInstance();
        Connection connectionDB = connect.getConnection();
        String sql1 = "INSERT INTO othervalue" +
                "(Cas, MatID, XTime, XLoc, LengthRoll, BackUpRoll, LengthBackUpRoll, Torque," +
                " InputError, WaterFlowUp, WaterFlowLo, OilFlowUp, OilFlowLo, WorkRollSpeed) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement pStmt1 = connectionDB.prepareStatement(sql1);
        pStmt1.setInt(1,Integer.parseInt(mesureValues.get(0).replace(".000","")));
        pStmt1.setInt(2,Integer.parseInt(mesureValues.get(1).replace(".000","")));
        pStmt1.setDouble(3,Double.parseDouble(mesureValues.get(2)));
        pStmt1.setDouble(4,Double.parseDouble(mesureValues.get(3)));
        pStmt1.setDouble(5,Double.parseDouble(mesureValues.get(11)));
        pStmt1.setDouble(6,Double.parseDouble(mesureValues.get(13)));
        pStmt1.setDouble(7,Double.parseDouble(mesureValues.get(14)));
        pStmt1.setDouble(8,Double.parseDouble(mesureValues.get(16)));
        pStmt1.setDouble(9,Double.parseDouble(mesureValues.get(18)));
        pStmt1.setDouble(10,Double.parseDouble(mesureValues.get(19)));
        pStmt1.setDouble(11,Double.parseDouble(mesureValues.get(20)));
        pStmt1.setDouble(12,Double.parseDouble(mesureValues.get(21)));
        pStmt1.setDouble(13,Double.parseDouble(mesureValues.get(22)));
        pStmt1.setDouble(14,Double.parseDouble(mesureValues.get(23)));
        pStmt1.executeUpdate();
        pStmt1.close();
        LOGGER.info("INFO : Orowan Other Value Ajouté à la Database");
    }
}
