package model.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnect {

    // Change if needed
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String BD_URL = "jdbc:mysql://localhost/osteoporosis";
    private static final String USUARI = "osteoporosis";
    private static final String PASSWORD = "Osteoporosis1.";

    private static DBConnect instance = null;

    private DBConnect() {
        try {
            // Loads the driver
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * <strong>getInstance()</strong>
     * gets an unique instance of DBConnect.
     *
     * Patron Singleton
     *
     * @return an instance of DBConnect.
     */
    public static DBConnect getInstance() {
        if (instance == null) {
            instance = new DBConnect();
        }
        return instance;
    }

    /**
     * <strong>getConnection()</strong>
     * establishes a connection to the database.
     *
     * @return Connection to database.
     * @throws SQLException if connection error occurs.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(BD_URL, USUARI, PASSWORD);
    }

}
