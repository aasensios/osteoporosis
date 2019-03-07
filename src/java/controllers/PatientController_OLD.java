/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Patient;
import model.PatientDAO;

/**
 * PatientController
 *
 * @author Alejandro Asensio
 * @version 2019-02-07
 */
@WebServlet(name = "PatientController", urlPatterns = {"/patient_controller"})
public class PatientController_OLD extends HttpServlet {

    private String path;
    private PatientDAO pdao;

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
            throws ServletException, IOException {

        // calcula el ruta absoluta para llegar a WEB-INF 
        // Cuando hacemos Clean & Build, se genera otra estructura de directorios: LoginApplication/build/web/WEB-INF/
        path = getServletContext().getRealPath("/WEB-INF");
        pdao = new PatientDAO(path);

        if (request.getParameter("action") != null) {
            String action = request.getParameter("action");
            switch (action) {
//                case "load":
//                    load(request, response);
//                    break;
                case "form_filterpatient":
                    form_filterpatient(request, response);
                    break;
                case "filter":
                    filter(request, response);
                    break;
                case "form_addpatient":
                    form_addpatient(request, response);
                    break;
                case "add":
                    add(request, response);
                    break;
                default:
                    throw new AssertionError();
            }
        } else {
            response.sendRedirect("index.jsp");
        }

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
        processRequest(request, response);
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
        processRequest(request, response);
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

    /**
     * Loads the patients data.
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Call the DAO, which calls the DataBase to get the data from the file
        List<Patient> patients = pdao.listAll();
        request.setAttribute("patients", patients);
        request.setAttribute("users", null);
        RequestDispatcher rd = request.getRequestDispatcher("landing.jsp");
        rd.forward(request, response);
    }

    /**
     * Shows the form to filter among patients.
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void form_filterpatient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("form_filterpatient", "yes");
        RequestDispatcher rd = request.getRequestDispatcher("landing.jsp");
        rd.forward(request, response);
    }

    /**
     * Filters the patients by some search criteria.
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void filter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get form parameters
        String classification = request.getParameter("classfication");
        String menopause = request.getParameter("menopause");
        String menopauseType = request.getParameter("menopauseType");

        // Prepare the object to perform the query
        Patient p = new Patient();
        p.setClassification(classification);
        p.setMenopause(menopause.equals("si"));
        p.setMenopauseType(menopauseType);
        
        List<Patient> filteredPatients = pdao.listFiltered(p);
        request.setAttribute("patients", filteredPatients);
        RequestDispatcher rd = request.getRequestDispatcher("landing.jsp");
        rd.forward(request, response);
        
    }
    
    /**
     * Shows the form to add a patient.
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void form_addpatient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("addpatient.jsp");
        rd.forward(request, response);
    }

    /**
     * Adds a new patient to the database.
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Calculate the registerId for the new patient
        List allPatients = pdao.listAll();
//        Patient lastPatient = (Patient) allPatients.get(allPatients.size() - 1);
//        int registerId = lastPatient.getRegisterId() + 1;
        int registerId = allPatients.size() + 1;

        // Get the parameters from the form
        int age = Integer.parseInt(request.getParameter("age"));
        int weight = Integer.parseInt(request.getParameter("weight"));
        int height = Integer.parseInt(request.getParameter("height"));
        String classification = request.getParameter("classification").toUpperCase();
        int menarche = Integer.parseInt(request.getParameter("menarche"));
        boolean menopause = request.getParameter("menopause").equals("si");
        String menopauseType = request.getParameter("menopauseType").toUpperCase().replace("_", " ");
        
        // Prepare the new Patient
        Patient p = new Patient(registerId, age, weight, height, classification, menarche, menopause, menopauseType);
        
        // Add the new patient to database
        int patient_added = pdao.add(p);
        
        // Set patient_added as a flag to know if patient has been added or not
        // pdao.add returns: -1 if patient already exists, 0 if problems with file, 1 if success.
        request.setAttribute("patient_added", patient_added);
        
        // Reload the form to add another patient
        RequestDispatcher rd = request.getRequestDispatcher("addpatient.jsp");
        rd.forward(request, response);
    }

    

}
