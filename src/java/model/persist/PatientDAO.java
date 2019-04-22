package model.persist;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /**
     * List all patients.
     *
     * @return an array of Patient objects
     */
    public List<Patient> list() {

        List<Patient> patients = new ArrayList<>();

        // Parenthesis try block: 'finally' is not necessary to close the database connection,
        // because the parenthesis close the connection automatically.
        try (
                Connection conn = dataSource.getConnection();
                Statement st = conn.createStatement();
                ResultSet resultSet = st.executeQuery(getQuery("SELECT_ALL"));) {
            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setRegisterId(resultSet.getInt("registerId"));
                patient.setAge(resultSet.getInt("age"));
                patient.setAgeGroup(resultSet.getString("ageGroup"));
                patient.setWeight(resultSet.getInt("weight"));
                patient.setHeight(resultSet.getInt("height"));
                patient.setImc(resultSet.getDouble("imc"));
                patient.setClassification(resultSet.getString("classification"));
                patient.setMenarche(resultSet.getInt("menarche"));
                patient.setMenopause(resultSet.getBoolean("menopause"));
                patient.setMenopauseType(resultSet.getString("menopauseType"));
                patients.add(patient);
            }
        } catch (SQLException e) {
//            System.out.println(e.getMessage());
        }

        return patients;

    }

    /**
     * Inserts a new patient.
     *
     * @param patient to be inserted
     * @return 1 if success; 0 otherwise
     */
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
//            System.out.println(e.getMessage());
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

            // UPDATE = UPDATE patients SET age=?, ageGroup=?, weight=?, height=? imc=?, classification=?, menarche=?, menopause=?, menopauseType=? WHERE registerId=?
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
//            System.out.println(e.getMessage());
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

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pst = conn.prepareStatement(getQuery("DELETE"));) {
            pst.setInt(1, patient.getRegisterId());
            rowsAffected = pst.executeUpdate();
        } catch (SQLException e) {
//            System.out.println(e.getMessage());
            rowsAffected = -2;
        }

        return rowsAffected;

    }

    /**
     * Filters all the patients by the properties of a given patient passed as
     * parameter.
     *
     * @param patientAsFilter
     * @return
     */
    public ArrayList<Patient> filter(Patient patientAsFilter) {

        ArrayList<Patient> patients = new ArrayList<>();

        // Parenthesis try block: 'finally' is not necessary to close the database connection,
        // because the parenthesis close the connection automatically.
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pst = conn.prepareStatement(getQuery("FILTER"));) {

            // Get the values
            String classification = patientAsFilter.getClassification();
            Boolean menopause = patientAsFilter.getMenopause();
            String menopauseType = patientAsFilter.getMenopauseType();

            // Prepare a string variable for the WHERE clause for the SQL query
            String where = "";

            // Full null case
            if (classification == null
                    && menopause == null
                    && menopauseType == null) {
                where += "";
            } else {
                where += "WHERE";
                if (classification != null) {
                    where += String.format(" classification=%s", classification);
                }
                if (menopause != null) {
                    int menopauseInt = menopause ? 1 : 0;
                    where += String.format(" AND menopause=%d", menopauseInt);
                }
                if (menopauseType != null) {
                    where += String.format(" AND menopauseType=%s", menopauseType);
                }
            }

            // NEW WAY Assign the where clause to the prepared statement
            // ? === WHERE (classification=... AND menopause=... AND menopauseType=...)
//            pst.setString(1, where);
            
            // OLD WAY
            pst.setString(1, classification);
            pst.setBoolean(2, menopause);
            pst.setString(3, menopauseType);

            // executeQuery without parameters because it's a prepared statement
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setRegisterId(resultSet.getInt("registerId"));
                patient.setAge(resultSet.getInt("age"));
                patient.setAgeGroup(resultSet.getString("ageGroup"));
                patient.setWeight(resultSet.getInt("weight"));
                patient.setHeight(resultSet.getInt("height"));
                patient.setImc(resultSet.getDouble("imc"));
                patient.setClassification(resultSet.getString("classification"));
                patient.setMenarche(resultSet.getInt("menarche"));
                patient.setMenopause(resultSet.getBoolean("menopause"));
                patient.setMenopauseType(resultSet.getString("menopauseType"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return patients;
    }

    /**
     * Counts the number of patients for each age group in database.
     *
     * @return
     */
    public HashMap<String, Integer> countAgeGroups() {

        HashMap<String, Integer> ageGroups = new HashMap<>();

        try (
                Connection conn = dataSource.getConnection();
                Statement st = conn.createStatement();
                ResultSet resultSet = st.executeQuery(getQuery("COUNT_AGEGROUPS"));) {
            while (resultSet.next()) {
                ageGroups.put(resultSet.getString("ageGroup"), resultSet.getInt("COUNT(ageGroup)"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return ageGroups;

    }
    /**
     * TODO unified search bar
     */
//    public ArrayList<Patient> globalFilter(String searchCriteria) {
//
//        ArrayList<Patient> list = new ArrayList<>();
//
//        // Parenthesis try block: 'finally' is not necessary to close the database connection,
//        // because the parenthesis close the connection automatically.
//        try (Connection conn = dataSource.getConnection();
//                PreparedStatement pst = conn.prepareStatement(getQuery("FILTER"));) {
//            // FILTER = SELECT * FROM patients WHERE (age LIKE '%?%' OR ageGroup LIKE '%?%' OR weight LIKE '%?%' OR height LIKE '%?%' OR imc LIKE '%?%' OR classification LIKE '%?%' OR menarche LIKE '%?%' OR menopause LIKE '%?%' OR menopauseType LIKE '%?%');
//            pst.setInt(1, Integer.parseInt(searchCriteria));
//            pst.setString(2, searchCriteria);
//            pst.setInt(3, Integer.parseInt(searchCriteria));
//            pst.setInt(4, Integer.parseInt(searchCriteria));
//            pst.setDouble(5, Double.parseDouble(searchCriteria));
//            pst.setString(6, searchCriteria);
//            pst.setInt(7, Integer.parseInt(searchCriteria));
//            pst.setBoolean(8, searchCriteria.equals("SI"));
//            pst.setString(9, searchCriteria);
//        } catch (SQLException e) {
//        }
//
//        return list;
//    }

}
