/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
                case "add_form":
                    showFormAdd(request, response);
                    break;
                case "add":
                    addUser(request, response);
                    break;
                case "modify_form":
                    showFormModify(request, response);
                    break;
                case "user_to_modify":
                    modifyUser(request, response);
                    break;
                case "modify":
                    modifyThatUser(request, response);
                    break;
                case "delete_form":
//                    showFormDelete(request, response);
                    break;
                case "user_to_delete":
//                    deleteUser(request, response);
                    break;
                case "filter":
//                    filterUser(request, response);
                    break;
                default:
                    response.sendRedirect("index.jsp");
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
            // Redirect to list all users
            response.sendRedirect("patient_controller?action=list_all");
        } else {
            // Credentials are not valid
            response.sendRedirect("login.jsp?error");
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

        ArrayList<User> users = userDAO.listAll();
        request.setAttribute("users", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
        dispatcher.forward(request, response);

    }

    /**
     * Shows the form to add a new user.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showFormAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("user.jsp?showFormAdd");

    }

    /**
     * Adds a new user.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form fields
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");
//        String role = request.getParameter("role");
        String role = "basic"; // basic role by default

        // Null input handling
        if (username == null
                || password == null
                || passwordRepeat == null) {
            request.setAttribute("error", "None of the fields can be empty!");
            response.sendRedirect("user.jsp");
        } else if (!password.equals(passwordRepeat)) {
            request.setAttribute("error", "Passwords doesn't match!");
            response.sendRedirect("user.jsp");
        }

        // User construction
        User newUser = new User(
                username.toLowerCase(),
                password,
                role
        );

        if (userDAO.insert(newUser) == 1) {
            request.setAttribute("success", "User successfully inserted :) !");
        } else {
            request.setAttribute("error", "User not inserted :( !");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
        dispatcher.forward(request, response);

    }

    /**
     * Gets all users and sends them to the view.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showFormModify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<User> users = userDAO.listAll();

        // users list empty case
        if (users.isEmpty()) {
            request.setAttribute("error", "There aren't users");
        }

        request.setAttribute("users", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
        dispatcher.forward(request, response);

    }

    /**
     * Modifies an existing user.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void modifyUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get user from form with fields separated by semicolon ';'
        String user = request.getParameter("user");
        String[] fields = user.split(";");

        // Construct a user
        User userToBeModified = new User(
                fields[0],
                fields[1],
                fields[2]
        );

        request.setAttribute("user_to_modify", userToBeModified);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
        dispatcher.forward(request, response);

    }

    private void modifyThatUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form fields
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");
        String role = request.getParameter("role");

        // Null input handling
        if (username == null
                || password == null
                || passwordRepeat == null
                || role == null) {
            request.setAttribute("error", "None of the fields can be empty!");
            response.sendRedirect("user.jsp");
        }

        // User construction
        User newUser = new User(
                username,
                password,
                role
        );

        // Modify the user in database
        if (userDAO.update(newUser) > 0) {
            request.setAttribute("success", "User successfully modified :) !");
        } else {
            request.setAttribute("error", "User not modified :( !");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
        dispatcher.forward(request, response);
    }

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
//        List<User> users = udao.listAll();
//        request.setAttribute("users", null);
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


}
