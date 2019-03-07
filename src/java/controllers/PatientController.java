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

        this.path = getServletContext().getRealPath("/WEB-INF/resources");
        this.patientDAO = new PatientDAO(path);

        switch (action) {
            case "list_all":
                listAll(request, response);
                break;
            case "add_form":
                showFormAdd(request, response);
                break;
            case "add":
//                addPatient(request, response);
                break;
            case "modify_form":
                showFormModify(request, response);
                break;
            case "patient_to_modify":
//                modifyPatient(request, response);
                break;
            case "modify":
//                modifyThatPatient(request, response);
                break;
            case "delete_form":
                showFormDelete(request, response);
                break;
            case "patient_to_delete":
//                deletePatient(request, response);
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
//    private void addPatient(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        // get form fields: category, name, phone, age
//        String category = request.getParameter("category");
//        String name = request.getParameter("name");
//        String phone = request.getParameter("phone");
//        String age = request.getParameter("age");
//
//        // null input patient handling
//        if (category == null
//                || phone == null
//                || age == null
//                || name == null) {
//            response.sendRedirect("patient.jsp");
//        }
//
//        Patient newPatient = new Patient(category, name, phone, Integer.parseInt(age));
//
//        if (patientDAO.insert(newPatient) == 1) {
//            request.setAttribute("success", "Patient " + name + " successfully inserted :) !");
//        } else {
//            request.setAttribute("error", "Patient not inserted :( !");
//        }
//
//        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
//        dispatcher.forward(request, response);
//
//    }

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

        ArrayList<Patient> cats = patientDAO.listAll();

        // patients list empty case
        if (cats.isEmpty()) {
            request.setAttribute("error", "There aren't patients");
        }

        request.setAttribute("patients", cats);
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
//    private void modifyPatient(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        // get patient with fields separated by semicolon ';'
//        String patient = request.getParameter("patient");
//        String[] patientParams = patient.split(";");
//
//        // id, category, name, phone, age
//        Patient newPatient = new Patient(
//                Integer.parseInt(patientParams[0]),
//                patientParams[1],
//                patientParams[2],
//                patientParams[3],
//                Integer.parseInt(patientParams[4])
//        );
//
//        request.setAttribute("patient_to_modify", newPatient);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
//        dispatcher.forward(request, response);
//
//    }

//    private void modifyThatPatient(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        // get form fields: category, name, phone, age
//        String category = request.getParameter("category");
//        String name = request.getParameter("name");
//        String phone = request.getParameter("phone");
//        String age = request.getParameter("age");
//        String id = request.getParameter("id");
//
//        // null input patient handling
//        if (category == null
//                || phone == null
//                || age == null
//                || name == null) {
//            response.sendRedirect("patient.jsp");
//        }
//
//        Patient newPatient = new Patient(Integer.parseInt(id), category, name, phone, Integer.parseInt(age));
//
//        // Modify the patient in database
//        if (patientDAO.update(newPatient) > 0) {
//            request.setAttribute("success", "Patient " + name + " successfully modified :) !");
//        } else {
//            request.setAttribute("error", "Patient not modified :( !");
//        }
//
//        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
//        dispatcher.forward(request, response);
//    }

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

        ArrayList<Patient> cats = patientDAO.listAll();

        if (cats.isEmpty()) {
            request.setAttribute("error", "There aren't patients");
        }

        request.setAttribute("patients", cats);
        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
        dispatcher.forward(request, response);

    }

//    private void deletePatient(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        // get patient with fields separated by semicolon ';'
//        String patient = request.getParameter("patient");
//        String[] patientParams = patient.split(";");
//
//        // id, category, name, phone, age
//        Patient newPatient = new Patient(
//                Integer.parseInt(patientParams[0]),
//                patientParams[1],
//                patientParams[2],
//                patientParams[3],
//                Integer.parseInt(patientParams[4])
//        );
//
//        // Delete the patient from database
//        int rowsAffected = patientDAO.delete(newPatient);
//
//        if (rowsAffected > 0) {
//            request.setAttribute("success", "Patient " + newPatient.getName() + " DELETED ;) !");
//        } else {
//            switch (rowsAffected) {
//                case -1:
//                    request.setAttribute("error", "Patient not deleted due to a Constraint fail.");
//                    break;
//                case -2:
//                    request.setAttribute("error", "Patient not deleted due to an Error, contact administrator.");
//                    break;
//                default:
//                    response.sendRedirect("patient.jsp");
//            }
//        }
//
//        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
//        dispatcher.forward(request, response);
//        
//    }

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
