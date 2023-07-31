package fr.arcelormittal.Managers;

import fr.arcelormittal.Models.Password;
import fr.arcelormittal.Models.Stand;
import fr.arcelormittal.Models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DAOManager {

    private static DAOManager instance = null;
    private Connection connection = null;
    private final static Logger LOGGER = LoggerFactory.getLogger(DAOManager.class);

    private DAOManager() throws IOException {
        Properties props = new Properties();
        props.load(new FileReader("db.properties"));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection c = DriverManager.getConnection(props.getProperty("url"),props.getProperty("user"),props.getProperty("password"));
            this.connection = c;
        } catch (Exception e){
            LOGGER.error("ERROR : {}", e.getCause());
        }
    }

    public static DAOManager getInstance() throws IOException {
        if (instance == null) instance = new DAOManager();
        return instance;
    }

    public Connection getConnection(){
        return this.connection;
    }

    public void addUser(String name, String email, String role){
        try {
            PreparedStatement pStmt = connection.prepareStatement("INSERT INTO USERS (userName, userMail, hPwd, userRole)" +
                    "VALUES (?,?,?,(SELECT roleID FROM USERROLES WHERE roleLabel LIKE ?));");
            pStmt.setString(1, name);
            pStmt.setString(2, email);
            pStmt.setString(3, Password.doHashing(Password.getRandomPassword()));
            pStmt.setString(4,role);
            pStmt.executeUpdate();
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
    }

    public void deleteUser(int id) {
        try {
            PreparedStatement pStmt = connection.prepareStatement("DELETE FROM users WHERE userID = ?");
            pStmt.setInt(1,id);
            pStmt.executeUpdate();
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
    }

    public void updateUser(int id, String name, String mail, String password, String role) {
        try {
            PreparedStatement pStmt = connection.prepareStatement("UPDATE users SET userName = ?," +
                    " userMail = ?, hPwd = ?, userRole = " +
                    "(SELECT roleID FROM userroles WHERE roleLabel LIKE ?)" +
                    " WHERE userID = ?");
            pStmt.setString(1, name);
            pStmt.setString(2,mail);
            pStmt.setString(3,Password.doHashing(password));
            pStmt.setString(4,role);
            pStmt.setInt(5,id);
            pStmt.executeUpdate();
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
    }

    public List<User> getUsers(){
        List<User> returnList = new ArrayList<User>();
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT userID, userName, userMail, hPwd, userRole FROM users;");
            ResultSet result = pStmt.executeQuery();
            boolean next = result.next();
            while (next) {
                    returnList.add(new User(result.getInt("userID"),
                            result.getString("userName"),
                            result.getString("userMail"),
                            result.getString("hPwd"),
                            result.getInt("userRole")));
                    next = result.next();
            }
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
        return returnList;
    }

    public Stand getStand(int id) {
        Stand returnStand = null;
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT isEnable FROM stands WHERE standID = ?;");
            pStmt.setInt(1,id);
            ResultSet result = pStmt.executeQuery();
            boolean next = result.next();
            while(next) {
                returnStand = new Stand(id, result.getBoolean("isEnable"));
                next = result.next();
            }
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
        return returnStand;
    }

    public void updateStand(int id, boolean state){
        try {
            PreparedStatement pStmt = connection.prepareStatement("UPDATE stands SET isEnable = ? WHERE standID = ?;");
            pStmt.setBoolean(1,state);
            pStmt.setInt(2,id);
            pStmt.executeUpdate();
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
    }

    public List<Stand> getStands() {
        List<Stand> returnList = new ArrayList<Stand>();
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT standID, isEnable FROM stands;");
            ResultSet result = pStmt.executeQuery();
            boolean next = result.next();
            while (next) {
                returnList.add(new Stand(result.getInt("standID"), result.getBoolean("isEnable")));
                next = result.next();
            }
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
        return returnList;
    }

    public String getInput(){
        String resultInput = "";
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT Cas, He, Hs, Te, Ts, " +
                    "Diameter, WRYoung, offsetMesure, Mu, Strengh, G FROM inputorowan ORDER BY id DESC;");
            ResultSet result = pStmt.executeQuery();
            boolean next = result.next();
            resultInput += result.getInt("Cas") + ";";
            resultInput += result.getDouble("He") + ";";
            resultInput += result.getDouble("Hs") + ";";
            resultInput += result.getDouble("Te") + ";";
            resultInput += result.getDouble("Ts") + ";";
            resultInput += result.getDouble("Diameter") + ";";
            resultInput += result.getDouble("WRYoung") + ";";
            resultInput += result.getDouble("offsetMesure") + ";";
            resultInput += result.getDouble("Mu") + ";";
            resultInput += result.getDouble("Strengh") + ";";
            resultInput += result.getDouble("G");
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
        return resultInput;
    }

    public void addOuput(String[] data) {
        try {
            PreparedStatement pStmt = connection.prepareStatement("INSERT INTO outputorowan " +
                    " (Cas, caseErrors, OffsetYield, Friction, RollingTorque, SigmaMoy, SigmaIni, SigmaOut, SigmaMax," +
                    " ForceError, SlipError, hasConverged) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?);");
            pStmt.setInt(1,Integer.parseInt(data[0]));
            pStmt.setString(2,data[1]);
            pStmt.setDouble(3,Double.parseDouble(data[2]));
            pStmt.setDouble(4,Double.parseDouble(data[3]));
            pStmt.setDouble(5,Double.parseDouble(data[4]));
            pStmt.setDouble(6,Double.parseDouble(data[5]));
            pStmt.setDouble(7,Double.parseDouble(data[6]));
            pStmt.setDouble(8,Double.parseDouble(data[7]));
            pStmt.setDouble(9,Double.parseDouble(data[8]));
            pStmt.setDouble(10,Double.parseDouble(data[9]));
            pStmt.setDouble(11,Double.parseDouble(data[10]));
            pStmt.setString(12,data[11]);
            pStmt.executeUpdate();
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
    }

    public void addMeans() {
        double[] values = {0,0,0};
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT Friction, SigmaOut FROM outputorowan WHERE " +
                    "id >= (SELECT MAX(id) - 5 FROM outputorowan);");
            ResultSet result = pStmt.executeQuery();
            while (result.next()) {
                values[0] += result.getDouble("Friction");
                values[1] += result.getDouble("SigmaOut");
            }
            pStmt.close();
            PreparedStatement pStmt2 = connection.prepareStatement("SELECT WorkRollSpeed FROM othervalue WHERE " +
                    "id >= (SELECT MAX(id) - 5 FROM othervalue);");
            ResultSet result2 = pStmt2.executeQuery();
            while (result2.next()) {
                values[2] += result2.getDouble("WorkRollSpeed");
            }
            pStmt2.close();
            values[0] = values[0] / 5f;
            values[1] = values[1] / 5f;
            values[2] = values[2] / 5f;
            insertMean(values);
        } catch (SQLException e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
    }

    private void insertMean(double[] values) {
        try {
            PreparedStatement pStmt = connection.prepareStatement("INSERT INTO meanvalues (Friction, Sigma, RollSpeed) " +
                    "VALUES (?,?,?);");
            pStmt.setDouble(1,values[0]);
            pStmt.setDouble(2,values[1]);
            pStmt.setDouble(3,values[2]);
            pStmt.executeUpdate();
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
    }

    public double[] getFriction(){
        double[] resultValues = new double[4];
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT Friction FROM meanvalues WHERE id >= (SELECT MAX(id) - 5 FROM meanValues);");
            ResultSet result = pStmt.executeQuery();
            boolean next = result.next();
            int i = 0;
            while (next) {
                resultValues[i] = result.getDouble("Friction");
                i++;
                next = result.next();
            }
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
        return resultValues;
    }

    public double[] getSigma(){
        double[] resultValues = new double[4];
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT Sigma FROM meanvalues WHERE id >= (SELECT MAX(id) - 5 FROM meanValues);");
            ResultSet result = pStmt.executeQuery();
            boolean next = result.next();
            int i = 0;
            while (next) {
                resultValues[0] = result.getDouble("Sigma");
                i++;
                next = result.next();
            }
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
        return resultValues;
    }

    public double[] getRollSpeed(){
        double[] resultValues = new double[4];
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT RollSpeed FROM meanvalues WHERE id >= (SELECT MAX(id) - 5 FROM meanValues);");
            ResultSet result = pStmt.executeQuery();
            boolean next = result.next();
            int i = 0;
            while(next) {
                resultValues[0] = result.getDouble("RollSpeed");
                i++;
                next = result.next();
            }
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
        return resultValues;
    }

}
