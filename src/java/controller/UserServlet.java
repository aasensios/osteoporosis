/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import model.persist.UserDAO;
import util.Validator;

/**
 * UserServlet
 *
 * @author Alejandro Asensio
 * @version 2019-02-07
 */
//@WebServlet(name = "UserServlet", urlPatterns = {"/users"})
@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private String path;
    private UserDAO userDAO;
    private Map<String, String> messages;
    private String defaultRole;

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

        // Prepare messages.
        messages = new HashMap<>();
        request.setAttribute("messages", messages);

        defaultRole = "basic";

        String action = request.getParameter("action");

        // Switch block does NOT accept null values.
        if (action == null) {
            action = "";
        }

        // Decide which method has to be called
        switch (action) {
            case "login":
                login(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            case "list":
                list(request, response);
                break;
            case "add_form":
                showFormAdd(request, response);
                break;
            case "add":
                addUser(request, response);
                break;
            case "modify_form":
                showModifyButtons(request, response);
                break;
            case "user_to_modify":
                modifyUser(request, response);
                break;
            case "modify":
                modifyThatUser(request, response);
                break;
            case "delete_form":
                showDeleteButtons(request, response);
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

        // Refresh the users JSP page
//        request.getRequestDispatcher("users.jsp").forward(request, response);
    }

    /**
     * Logs in an existing user by its username and password.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Get user credentials from form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Instantiate a user this the previous credentials
        User searchedUser = new User(username, password);

        // Validate those credentials against the database
        User foundUser = userDAO.find(searchedUser);
        if (foundUser != null) {
            // Create a session
            HttpSession session = request.getSession();
            session.setAttribute("logged_in", true);
            session.setAttribute("username", foundUser.getUsername());
            session.setAttribute("role", foundUser.getRole());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            messages.put("error", "Username and/or password are invalid.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

    }

    /**
     * Logs out the current active user.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Get the session
        HttpSession session = request.getSession();

        // Destroy the session
        session.invalidate();

        // Redirect to the initial view
        request.getRequestDispatcher("index.jsp").forward(request, response);

    }

    /**
     * Lists all users.
     *
     * @param request
     * @param response
     */
    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<User> users = userDAO.list();
        request.setAttribute("users", users);

        if (users.isEmpty()) {
            messages.put("error", "Users list is empty");
        } else {
            messages.put("success", String.format("Total users: %d", users.size()));
        }

        request.getRequestDispatcher("users.jsp").forward(request, response);

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

        request.setAttribute("showFormAdd", true);

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
        // Get and validate username.
        String username = request.getParameter("username");
        if (username == null || username.trim().isEmpty()) {
            messages.put("name", "Please enter username");
        } else if (!username.matches("\\p{Alnum}+")) {
            messages.put("username", "Please enter alphanumeric characters only");
        }

        // Get and validate password.
        String password = request.getParameter("password");
        if (password == null || password.trim().isEmpty()) {
            messages.put("password", "Please enter password");
        } else if (password.length() < 3) {
            messages.put("password", "Password must be at least 3 characters long");
        }

        // Get and validate password.
        String passwordRepeat = request.getParameter("passwordRepeat");
        if (passwordRepeat == null || passwordRepeat.trim().isEmpty()) {
            messages.put("passwordRepeat", "Please repeat password");
        } else if (!passwordRepeat.equals(password)) {
            messages.put("passwordRepeat", "Passwords does not match");
        }

        String role = defaultRole;

        // User construction
        User newUser = new User(
                username.toLowerCase(),
                password,
                role
        );

        // Insert user into database
        if (userDAO.insert(newUser) == 1) {
            messages.put("success", "User has been inserted successfully");
        } else {
            messages.put("error", "User has not been inserted");
        }

        request.getRequestDispatcher("users.jsp").forward(request, response);

    }

    /**
     * Gets all users and sends them to the view.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showModifyButtons(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<User> users = userDAO.list();
        request.setAttribute("users", users);

        if (users.isEmpty()) {
            messages.put("error", "Users list is empty");
        } else {
            messages.put("success", String.format("Total users: %d", users.size()));
        }

        request.setAttribute("showModifyButtons", true);

        request.getRequestDispatcher("users.jsp").forward(request, response);

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
        RequestDispatcher dispatcher = request.getRequestDispatcher("users.jsp");
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
            response.sendRedirect("users.jsp");
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

        RequestDispatcher dispatcher = request.getRequestDispatcher("users.jsp");
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
    private void showDeleteButtons(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<User> users = userDAO.list();
        request.setAttribute("users", users);

        if (users.isEmpty()) {
            messages.put("error", "Users list is empty");
        } else {
            messages.put("success", String.format("Total users: %d", users.size()));
        }

        request.setAttribute("showDeleteButtons", true);

        request.getRequestDispatcher("users.jsp").forward(request, response);

    }

    /**
     * Deletes an existing user.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get patient from form with fields separated by semicolon ';'.
        String userCSV = request.getParameter("user");
        String[] fields = userCSV.split(";");

        // Prapare the patient to be deleted
        User userToBeDeleted = new User();
        userToBeDeleted.setUsername(fields[0]);

        // Delete the patient from database
        int rowsAffected = userDAO.delete(userToBeDeleted);

        if (rowsAffected > 0) {
            messages.put("success", "User has been deleted successfully");
        } else {
            switch (rowsAffected) {
                case -1:
                    messages.put("error", "User has not been deleted due to a constraint fail");
                    break;
                case -2:
                    messages.put("error", "User has not been deleted due to an error, contact administrator");
                    break;
                default:
                    showDeleteButtons(request, response);
            }
        }

        showDeleteButtons(request, response);
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
