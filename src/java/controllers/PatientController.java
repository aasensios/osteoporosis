package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Patient;
import model.persist.PatientDAO;

// Imports for graphics
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import java.io.File;
import org.jfree.chart.plot.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jfree.data.category.DefaultCategoryDataset;

@WebServlet(name = "PatientController", urlPatterns = {"/patient_controller"})
public class PatientController extends HttpServlet {

    private String path;
    private PatientDAO patientDAO;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {

        String action = request.getParameter("action");
        if (action == null) {
            // to avoid problems entering the switch, which does not accept null values
            action = "";
        }

        this.path = getServletContext().getRealPath("/WEB-INF/");
        this.patientDAO = new PatientDAO(path);

        switch (action) {
            case "list_all":
                listAll(request, response);
                break;
            case "make_graphic":
                makeGraphic(request, response);
                break;
            case "filter_form":
                showFormFilter(request, response);
                break;
            case "filter":
                filterPatient(request, response);
                break;
            case "add_form":
                showFormAdd(request, response);
                break;
            case "add":
                addPatient(request, response);
                break;
            case "modify_form":
                showFormModify(request, response);
                break;
            case "patient_to_modify":
                modifyPatient(request, response);
                break;
            case "modify":
                modifyThatPatient(request, response);
                break;
            case "delete_form":
                showFormDelete(request, response);
                break;
            case "patient_to_delete":
                deletePatient(request, response);
                break;
            default:
                response.sendRedirect("index.jsp");
        }

    }

    /**
     * Lists all patients.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void listAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Patient> patients = patientDAO.listAll();
        request.setAttribute("patients", patients);
        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
        dispatcher.forward(request, response);

    }

    /**
     * Makes a graphic with the statistics of all patients.
     *
     * @param request
     * @param response
     */
    private void makeGraphic(HttpServletRequest request, HttpServletResponse response) {

        // Get all patients
        HashMap<String, Integer> ageGroups = patientDAO.countAgeGroups();

        // Create a simple barplot for AgeGroups
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> ageGroup : ageGroups.entrySet()) {
            dataset.setValue(ageGroup.getValue(), "AgeGroups", ageGroup.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart("Ages of Osteoporosis Patients", "Age Group", "Patients number", dataset, PlotOrientation.VERTICAL, false, true, false);

        try {
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            ChartUtilities.writeChartAsPNG(os, chart, 625, 500);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.err.println("An error occurred while creating the graphic.");
            request.setAttribute("error", "An error occurred while creating the graphic.");
        }

    }

    /**
     * Shows the form to add a new patient.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showFormFilter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        ArrayList<Patient> patients = patientDAO.listAll();
//        request.setAttribute("patients", patients);
//        response.sendRedirect("patient.jsp?showFormFilter");
        ArrayList<Patient> patients = patientDAO.listAll();
        request.setAttribute("patients", patients);
        request.setAttribute("showFormFilter", true);
        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Filters the patients list by any search criteria.
     *
     * @param request
     * @param response
     */
    private void filterPatient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // TODO global search bar on header -----------
//        String searchCriteria = request.getParameter("searchCriteria");
//        ArrayList<Patient> patientsFiltered = patientDAO.filter(searchCriteria);
//        request.setAttribute("patients", patientsFiltered);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
//        dispatcher.forward(request, response);
        //-------------
        // Get form fields from the row-inline-form
        String classification = request.getParameter("classification").toUpperCase();
        String menopause = request.getParameter("menopause").toUpperCase();
        String menopauseType = request.getParameter("menopauseType").toUpperCase();

        // Patient construction
        Patient patientAsFilter = new Patient();
        patientAsFilter.setClassification(classification);
        patientAsFilter.setMenopause(menopause.equals("YES"));
        patientAsFilter.setMenopauseType(menopauseType);

        // Filtering
        ArrayList<Patient> patients = patientDAO.filter(patientAsFilter);
        request.setAttribute("patients", patients);
        request.setAttribute("success", String.format("Filter result: %d rows", patients.size()));
        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
        dispatcher.forward(request, response);

    }

    /**
     * Shows the form to add a new patient.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showFormAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("patient.jsp?showFormAdd");

    }

    /**
     * Adds a new patient.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void addPatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form fields
        String age = request.getParameter("age");
        String weight = request.getParameter("weight");
        String height = request.getParameter("height");
        String classification = request.getParameter("classification");
        String menarche = request.getParameter("menarche");
        String menopause = request.getParameter("menopause");
        String menopauseType = request.getParameter("menopauseType");

        // Null input handling
        if (age == null
                || weight == null
                || height == null
                || classification == null
                || menarche == null
                || menopause == null
                || menopauseType == null) {
            response.sendRedirect("patient.jsp");
        }

        // Patient construction
        Patient newPatient = new Patient(
                Integer.parseInt(age),
                Integer.parseInt(weight),
                Integer.parseInt(height),
                classification.toUpperCase(),
                Integer.parseInt(menarche),
                menopause.equals("yes"),
                menopauseType.toUpperCase()
        );

        if (patientDAO.insert(newPatient) == 1) {
            request.setAttribute("success", "Patient successfully inserted :) !");
        } else {
            request.setAttribute("error", "Patient not inserted :( !");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
        dispatcher.forward(request, response);

    }

    /**
     * Gets all patients and sends them to the view.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showFormModify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Patient> patients = patientDAO.listAll();

        // patients list empty case
        if (patients.isEmpty()) {
            request.setAttribute("error", "There aren't patients");
        }

        request.setAttribute("patients", patients);
        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
        dispatcher.forward(request, response);

    }

    /**
     * Modifies an existing patient.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void modifyPatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get patient from form with fields separated by semicolon ';'
        String patient = request.getParameter("patient");
        String[] fields = patient.split(";");

        // Construct a patient
        Patient patientToBeModified = new Patient(
                Integer.parseInt(fields[0]),
                Integer.parseInt(fields[1]),
                fields[2],
                Integer.parseInt(fields[3]),
                Integer.parseInt(fields[4]),
                Double.parseDouble(fields[5]),
                fields[6],
                Integer.parseInt(fields[7]),
                fields[8].toUpperCase().equals("YES"),
                fields[9]
        );

        request.setAttribute("patient_to_modify", patientToBeModified);
        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
        dispatcher.forward(request, response);

    }

    private void modifyThatPatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form fields
        String registerId = request.getParameter("registerId");
        String age = request.getParameter("age");
        String weight = request.getParameter("weight");
        String height = request.getParameter("height");
        String classification = request.getParameter("classification").toUpperCase();
        String menarche = request.getParameter("menarche");
        String menopause = request.getParameter("menopause");
        String menopauseType = request.getParameter("menopauseType").toUpperCase();

        // Null input patient handling
        if (age == null
                || weight == null
                || height == null
                || classification == null
                || menarche == null
                || menopause == null
                || menopauseType == null) {
            response.sendRedirect("patient.jsp");
        }

        // Patient construction
        Patient patientModified = new Patient(
                Integer.parseInt(registerId),
                Integer.parseInt(age),
                Integer.parseInt(weight),
                Integer.parseInt(height),
                classification.toUpperCase(),
                Integer.parseInt(menarche),
                menopause.equals("YES"),
                menopauseType.toUpperCase()
        );

        // Modify the patient in database
        if (patientDAO.update(patientModified) > 0) {
            request.setAttribute("success", "Patient has been modified successfully.");
        } else {
            request.setAttribute("error", "Patient has not been modified.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Gets all patients and sends them to the view.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showFormDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Patient> patients = patientDAO.listAll();

        if (patients.isEmpty()) {
            request.setAttribute("error", "There aren't any patients");
        }

        request.setAttribute("patients", patients);
        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
        dispatcher.forward(request, response);

    }

    private void deletePatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get patient from form with fields separated by semicolon ';'
        String patient = request.getParameter("patient");
        String[] fields = patient.split(";");

        // 
        Patient patientToBeDeleted = new Patient();
        patientToBeDeleted.setRegisterId(Integer.parseInt(fields[0]));

        // Delete the patient from database
        int rowsAffected = patientDAO.delete(patientToBeDeleted);

        if (rowsAffected > 0) {
            request.setAttribute("success", "Patient has been deleted successfully.");
        } else {
            switch (rowsAffected) {
                case -1:
                    request.setAttribute("error", "Patient has not been deleted due to a constraint fail.");
                    break;
                case -2:
                    request.setAttribute("error", "Patient has not been deleted due to an error, contact administrator.");
                    break;
                default:
                    response.sendRedirect("patient.jsp");
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
        dispatcher.forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
