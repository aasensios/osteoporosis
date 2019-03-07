/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 * User class
 *
 * @author Alejandro Asensio
 * @version 1.1, 2019-02-13
 */
public class User {

    // Atributos
    private String username;
    private String password;
    private String role;

    // Constructors
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "basic"; // default role
    }

    public User(String username) {
        this.username = username;
    }

    public User() {
    }

    // Accessors: setters and getters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Overridden method to compare two User instances only by their usernames.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.username, other.username);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.username);
        return hash;
    }

    /**
     * Translate a User object to a String. Username and password are separated
     * by a colon ':'.
     *
     * @return String representation of a User object
     */
    @Override
    public String toString() {
        return String.format("%s;%s;%s;\n", username, password, role);
    }

}
