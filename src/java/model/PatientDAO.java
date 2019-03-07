package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alejandro Asensio
 * @version 1.0, 2019-01-29
 */
public class PatientDAO {

    private DataBase d; // object of class DataBase

    public PatientDAO() {
    }

    public PatientDAO(String path) {
        d = new DataBase(path + "/files/osteoporosis.csv");
    }

    /**
     * Check if a given patient is registered in the osteoporosis file.
     *
     * @param patient Patient to be found
     * @return boolean true if patient's registerId is in file; false otherwise
     */
    public boolean find(Patient patient) {
        boolean found = false;
        List<String> all = d.listAllLines();
        for (String s : all) {
            String[] pieces = s.split(";");
            if (pieces[0].equals(patient.getRegisterId())) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Lists all patients in database.
     *
     * @return a List of Patient objects
     */
    public List listAll() {
        List<Patient> patients = new ArrayList<>();
        List<String> all = d.listAllLines();

        for (String s : all) {
            String[] pieces = s.split(";");
            Patient p = new Patient();

            int id = Integer.parseInt(pieces[0]);
            int age = Integer.parseInt(pieces[1]);
            String ageGroup = pieces[2];
            int weight = Integer.parseInt(pieces[3]);
            int height = Integer.parseInt(pieces[4]);
            double imc = Double.parseDouble(pieces[5]);
            String classification = pieces[6];
            int menarche = Integer.parseInt(pieces[7]);
            boolean menopause = pieces[8].toUpperCase().equals("SI");
            String menopauseType = pieces[9];

            patients.add(new Patient(id, age, ageGroup, weight, height, imc,
                    classification, menarche, menopause, menopauseType));
        }

        return patients;
    }

    /**
     * Lists the patients filtered by a patient object.
     *
     * @param filterPatient Patient object
     * @return a List of Patient objects
     */
    public List listFiltered(Patient filterPatient) {
        List<Patient> allPatients = listAll();
        List<Patient> filteredPatients = new ArrayList<>();

        for (Patient p : allPatients) {
            boolean match = p.getClassification().equals(filterPatient.getClassification())
                    || p.isMenopause() == filterPatient.isMenopause()
                    || p.getMenopauseType().equals(filterPatient.getMenopauseType());
            if (match) {
                filteredPatients.add(p);
            }
        }
        return filteredPatients;
    }

    /**
     * Creates a new patient in the application.
     *
     * @param patient Patient to be created
     * @return boolean true if creation is successful; false otherwise
     */
    public boolean addasdf(Patient patient) {
        boolean flag = false;
        if (d.insertToFile(patient.toString()) == 1) {
            flag = true;
        }
        return flag;

        // TODO
        // Check if new patientname already exists
    }

    public void deletePatient(Patient patient) {
        // TODO
    }

    /**
     * Adds a new patient to file. If the registerId of the new patient is
     * already in the file, this method does not add any patient to file and
     * return -1. Otherwise, it tries to add the new patient: return 0 when some
     * problem occur with file manipulation, or return 1 when success.
     *
     * @param patient Patient object
     * @return -1 if patient already exists, 0 if problems with file, 1 if success.
     */
    public int add(Patient patient) {

        int inserted;

        // Check the new registerId is not in database yet
        if (find(patient)) {
            // This patient already exists, skip adding
            inserted = -1;
        } else {
            // Add the new patient to file
            inserted = d.insertToFile(patient.toString());//0 -> problemas, 1-> ok
        }

        return inserted;
    }
}
