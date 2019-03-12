/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Patient;
import model.User;
import model.persist.UserDAO;
import utils.Validator;

/**
 * UserController
 *
 * @author Alejandro Asensio
 * @version 2019-02-07
 */
@WebServlet(name = "UserController", urlPatterns = {"/user_controller"})
public class UserController extends HttpServlet {

    private String path;
    private UserDAO userDAO;

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
        path = getServletContext().getRealPath("/WEB-INF/");
        userDAO = new UserDAO(path);

        if (request.getParameter("action") != null) {
            String action = request.getParameter("action");
            switch (action) {
                case "login":
                    login(request, response);
                    break;
                case "logout":
                    logout(request, response);
                    break;
                case "list_all":
                    listAll(request, response);
                    break;
                case "form_adduser":
                    form_adduser(request, response);
                    break;
                case "add":
                    addUser(request, response);
                    break;
//                case "modify":
//                    modify(request, response);
//                    break;
                case "form_deleteuser":
                    form_deleteuser(request, response);
                    break;
                case "delete":
                    delete(request, response);
                    break;
            }
        } else {
            response.sendRedirect("login.jsp");
        }

    }

    /**
     * Logs in an existing user by its username and password.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Get the user credentials from form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Instantiate a user this the previous credentials
        User searchedUser = new User(username, password);

        // Validate those credentials against the database
        User foundUser = userDAO.find(searchedUser);
        if (foundUser != null) {
            // Create a session variable and assign this user to it
            HttpSession session = request.getSession();
            session.setAttribute("logged_in", true);
            session.setAttribute("username", foundUser.getUsername());
            session.setAttribute("role", foundUser.getRole());
            // Redirect to list all patients
            response.sendRedirect("patient_controller?action=list_all");
        } else {
            // Credentials are not valid
            response.sendRedirect("login.jsp?error=true");
        }

    }

    /**
     * Logs out the current active user.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the session
        HttpSession session = request.getSession();
        // Destroy the session
        session.invalidate();
        // Redirect to the initial view
        response.sendRedirect("patient_controller?action=list_all");
    }

    /**
     * Lists all users.
     *
     * @param request
     * @param response
     */
    private void listAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<User> patients = userDAO.listAll();
        request.setAttribute("patients", patients);
        RequestDispatcher dispatcher = request.getRequestDispatcher("patient.jsp");
        dispatcher.forward(request, response);

    }

    /**
     * Shows the form to add a new user.
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void form_adduser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("adduser.jsp");
        rd.forward(request, response);
    }

    /**
     * Creates a new user.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String passwordConfirm = request.getParameter("password_confirm");
//
//        // Error (message) handling
//        String message = "";
//        if (username.equals("") || password.equals("") || passwordConfirm.equals("")) {
//            message += "<p>All fields must be filled up.</p>";
//        }
//        if (!Validator.hasMinimumLength(password, 6)) {
//            message += "<p>Password must be 6 or more characters long.</p>";
//        }
//        if (!password.equals(passwordConfirm)) {
//            message += "<p>Password does not match with its repetition.</p>";
//        }
//
//        // Check if some error occurred
//        if (message.equals("")) {
//            User u = new User(username, password);
//            int user_added = udao.insert(u);
//            switch (user_added) {
//                case -1:
//                    message = "<p>This username already exists. Try another one.</p>";
//                    break;
//                case 0:
//                    message = "<p>Some problem with file has occurred.</p>";
//                    break;
//                case 1:
//                    message = "<p>New user has been registered successfully.</p>";
//                    break;
//                default:
//                    throw new AssertionError();
//            }
//        }
//
//        // Otra manera de enviar errores via POST
//        request.setAttribute("message", message);
//        RequestDispatcher rd = request.getRequestDispatcher("adduser.jsp");
//        rd.forward(request, response);
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
     * Modifies an existing user.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
//    private void modify(HttpServletRequest request, HttpServletResponse response) {
//
//        String newUsername = request.getParameter("new-username");
//        String currentPassword = request.getParameter("current-password");
//        String newPassword = request.getParameter("new-password");
//        String newPasswordRepeat = request.getParameter("new-password-repeat");
//
//        HttpSession session = request.getSession();
//
//        String path = getServletContext().getRealPath("/WEB-INF");
//        
//        //TODO
//    }
    /**
     * Shows the form to delete an existing user.
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void form_deleteuser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Call the DAO, which calls the DataBase to get the data from the file
//        List<Patient> users = udao.listAll();
//        request.setAttribute("patients", null);
//        request.setAttribute("users", users);
//        RequestDispatcher rd = request.getRequestDispatcher("landing.jsp");
//        rd.forward(request, response);
    }

    /**
     * Deletes an existing user.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        // Get the selected user by the radio input 
//        String selectedUsername = request.getParameter("selected");
//        
//        if (Validator.isNotEmpty(selectedUsername)) {
//            User userToBeDeleted = new User(selectedUsername);
//            int user_deleted = udao.delete(userToBeDeleted);
//            request.setAttribute("user_deleted", user_deleted);
//        }
//        
//        RequestDispatcher rd = request.getRequestDispatcher("landing.jsp");
//        rd.forward(request, response);
    }

}
