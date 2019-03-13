package model.persist;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import model.Patient;

public class PatientDAO {

    private final Properties queries;
    private static DBConnect dataSource;
    private static String PROPS_FILE;

    public PatientDAO(String path) throws IOException, ClassNotFoundException {

        // instanciamos la conexion a traves del metodo getInstance, patron Singleton
        dataSource = DBConnect.getInstance();

        // estructura ordenada en ficheros de clase Property para guardar las sentencias SQL
        queries = new Properties();
        PROPS_FILE = path + "/resources/queries_on_patients.properties";
        queries.load(new FileInputStream(PROPS_FILE));

    }

    public String getQuery(String queryName) {
        return queries.getProperty(queryName);
    }

    public ArrayList<Patient> listAll() {
        ArrayList<Patient> list = new ArrayList<>();

        // Parenthesis try block: 'finally' is not necessary to close the database connection,
        // because the parenthesis close the connection automatically.
        try (
                Connection conn = dataSource.getConnection();
                Statement st = conn.createStatement();) {
            // SELECT_ALL = SELECT * FROM patients
            ResultSet res = st.executeQuery(getQuery("SELECT_ALL"));
            while (res.next()) {
                Patient patient = new Patient();
                patient.setRegisterId(res.getInt("registerId"));
                patient.setAge(res.getInt("age"));
                patient.setAgeGroup(res.getString("ageGroup"));
                patient.setWeight(res.getInt("weight"));
                patient.setHeight(res.getInt("height"));
                patient.setImc(res.getDouble("imc"));
                patient.setClassification(res.getString("classification"));
                patient.setMenarche(res.getInt("menarche"));
                patient.setMenopause(res.getBoolean("menopause"));
                patient.setMenopauseType(res.getString("menopauseType"));
                list.add(patient);
            }
        } catch (SQLException e) {
        }

        return list;
    }

    public int insert(Patient patient) {
        int rowsAffected;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pst = conn.prepareStatement(getQuery("INSERT")
                );) {

            // INSERT = INSERT INTO patients (age, ageGroup, weight, height, imc, classification, menarche, menopause, menopauseType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            pst.setInt(1, patient.getAge());
            pst.setString(2, patient.getAgeGroup());
            pst.setInt(3, patient.getWeight());
            pst.setInt(4, patient.getHeight());
            pst.setDouble(5, patient.getImc());
            pst.setString(6, patient.getClassification());
            pst.setInt(7, patient.getMenarche());
            pst.setBoolean(8, patient.getMenopause());
            pst.setString(9, patient.getMenopauseType());

            rowsAffected = pst.executeUpdate();
        } catch (SQLException e) {
            rowsAffected = 0;
        }

        return rowsAffected;
    }

    /**
     * Updates an existing user.
     *
     * @param patient
     * @return 1 if success, 0 otherwise
     */
    public int update(Patient patient) {
        int rowsAffected;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pst = conn.prepareStatement(getQuery("UPDATE"));) {

            // UPDATE   = UPDATE patients SET age=?, ageGroup=?, weight=?, height=? imc=?, classification=?, menarche=?, menopause=?, menopauseType=? WHERE registerId=?
            pst.setInt(1, patient.getAge());
            pst.setString(2, patient.getAgeGroup());
            pst.setInt(3, patient.getWeight());
            pst.setInt(4, patient.getHeight());
            pst.setDouble(5, patient.getImc());
            pst.setString(6, patient.getClassification());
            pst.setInt(7, patient.getMenarche());
            pst.setBoolean(8, patient.getMenopause());
            pst.setString(9, patient.getMenopauseType());
            pst.setInt(10, patient.getRegisterId());

            rowsAffected = pst.executeUpdate();
        } catch (SQLException e) {
            rowsAffected = 0;
        }

        return rowsAffected;
    }

    /**
     * Deletes an existing user.
     *
     * @param patient
     * @return 1 if success, -1 if constraint fail, -2 if SQL exception occurs
     */
    public int delete(Patient patient) {
        int rowsAffected = 0;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pst = conn.prepareStatement(getQuery("DELETE"));) {
            pst.setInt(1, patient.getRegisterId());
            rowsAffected = pst.executeUpdate();
        } catch (SQLException e) {
            rowsAffected = -2;
        }

        return rowsAffected;
    }

    /**
     *
     * @param searchCriteria
     * @return
     */
    public ArrayList<Patient> filter(String searchCriteria) {

        ArrayList<Patient> list = new ArrayList<>();

        // Parenthesis try block: 'finally' is not necessary to close the database connection,
        // because the parenthesis close the connection automatically.
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pst = conn.prepareStatement(getQuery("FILTER"));) {
            // FILTER = SELECT * FROM patients WHERE (age LIKE '%?%' OR ageGroup LIKE '%?%' OR weight LIKE '%?%' OR height LIKE '%?%' OR imc LIKE '%?%' OR classification LIKE '%?%' OR menarche LIKE '%?%' OR menopause LIKE '%?%' OR menopauseType LIKE '%?%');
            pst.setInt(1, Integer.parseInt(searchCriteria));
            pst.setString(2, searchCriteria);
            pst.setInt(3, Integer.parseInt(searchCriteria));
            pst.setInt(4, Integer.parseInt(searchCriteria));
            pst.setDouble(5, Double.parseDouble(searchCriteria));
            pst.setString(6, searchCriteria);
            pst.setInt(7, Integer.parseInt(searchCriteria));
            pst.setBoolean(8, searchCriteria.equals("SI"));
            pst.setString(9, searchCriteria);
        } catch (SQLException e) {
        }

        return list;
    }

}
