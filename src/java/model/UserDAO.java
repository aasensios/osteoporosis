package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que accedeix al recurs: el fitxer users.txt
 *
 * @author Profes de DAW M07; modified by Alejandro Asensio
 * @version 2019-02-07
 */
public class UserDAO {

    // atributo que será un objeto
    private DataBase d;

    public UserDAO() {
    }

    public UserDAO(String path) {
        d = new DataBase(path + "/files/users.txt");
    }

    /**
     * Check if a given user is registered in the application.
     *
     * @param u User to be found
     * @return boolean true if username+password exists in file; false otherwise
     */
    public boolean validUserAndPassword(User u) {
        boolean flag = false;

        List<String> all = d.listAllLines();

        for (String s : all) {
            String[] pieces = s.split(";");
            if (pieces[0].equals(u.getUsername()) && pieces[1].equals(u.getPassword())) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    /**
     * Lists all users in database.
     *
     * @return a List of User objects
     */
    public List listAll() {
        List<User> users = new ArrayList<>();
        List<String> all = d.listAllLines();

        for (String s : all) {
            String[] pieces = s.split(";");
            User u = new User();

            String username = pieces[0];
            String password = pieces[1];
            String role = pieces[2];

            users.add(new User(username, password, role));
        }

        return users;
    }

    /**
     * Creates a new user in the application.
     *
     * @param u User to be created
     * @return
     */
    public int add(User u) {

        int inserted;
        //Paso 1: comprueba que u.getUsername() sea único
        if (findUsername(u.getUsername())) {
            inserted = -1;//ya existe el usuario
        } else {
            //es cuando inserto
            inserted = d.insertToFile(u.toString());//0->problemas, 1-> ok
        }

        return inserted;
    }

    private boolean findUsername(String username) {
        boolean flag = false;

        List<String> all = d.listAllLines();

        for (String s : all) {
            String[] pieces = s.split(";");
            if (pieces[0].equals(username)) {
                flag = true;
                break;
            }
        }

        return flag;

    }

    /**
     * Searches by username and returns a complete User object.
     *
     * @param username String
     * @return User object if exists; null otherwise
     */
    public User find(String username) {
        User user = null;

        List<String> all = d.listAllLines();

        for (String s : all) {
            String[] pieces = s.split(";");
            if (username.equals(pieces[0])) {
                user = new User(pieces[0], pieces[1], pieces[2]);
            }
        }

        return user;

    }

    /**
     * Deletes an existing user in the application.
     *
     * @param u User to be deleted
     * @return 1 if success; 0 otherwise
     * @throws java.io.IOException
     */
    public int delete(User u) throws IOException {

        int result = 0;
        List<User> users = listAll();

        // To use the remove method of ArrayLists, before we have override equals method in User class
        boolean deleted = users.remove(u);
        
        // Delete all the content of the file and then write all the users after the removal
        if (deleted) {
            d.deleteContentFile();
            for (User user : users) {
                result = d.insertToFile(user.toString());
            }
        }

        return result;

    }
    
}
