package model.persist;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import model.User;

public class UserDAO {

    private static DBConnect dataSource;
    private final Properties queries;
    private static String PROPS_FILE;

    public UserDAO(String path) throws IOException {

        // instanciamos la conexion a traves del metodo getInstance, patron Singleton
        dataSource = DBConnect.getInstance();

        // estructura ordenada en ficheros de clase Property para guardar las sentencias SQL
        queries = new Properties();
        PROPS_FILE = path + "/queries_on_categories.properties";
        queries.load(new FileInputStream(PROPS_FILE));

    }

    public String getQuery(String queryName) {
        return queries.getProperty(queryName);
    }

//    public ArrayList<User> listAll() {
//        ArrayList<User> list = new ArrayList<>();
//
//        // si usamos un try con parentesis, no hace falta que tengamos un finally para cerrar la conexion, 
//        // ya que los parentesis cierran la conexion automaticamente
//        try (
//                Connection conn = dataSource.getConnection();
//                Statement st = conn.createStatement();
//            ) {
//            ResultSet res = st.executeQuery(getQuery("FIND_ALL"));
//            while (res.next()) {
//                User cat = new User();
//                cat.setId(res.getInt("id"));
//                cat.setDescription(res.getString("description"));
//                list.add(cat);
//            }
//
//        } catch (SQLException e) {
//            list = new ArrayList<>();
//        }
//
//        return list;
//    }

//    public int insert(User category) {
//        int rowsAffected;
//
//        try (Connection conn = dataSource.getConnection();
//                PreparedStatement pst = conn.prepareStatement(getQuery("INSERT"));) {
//            pst.setString(1, category.getDescription());
//            rowsAffected = pst.executeUpdate();
//        } catch (SQLException e) {
//            rowsAffected = 0;
//        }
//
//        return rowsAffected;
//    }

//    public int update(User category) {
//        int rowsAffected;
//
//        try (Connection conn = dataSource.getConnection();
//                PreparedStatement pst = conn.prepareStatement(getQuery("UPDATE"));) {
//            pst.setString(1, category.getDescription());
//            pst.setInt(2, category.getId());
//            rowsAffected = pst.executeUpdate();
//        } catch (SQLException e) {
//            rowsAffected = 0;
//        }
//
//        return rowsAffected;
//    }

    /**
     * 
     * @param category
     * @return 1 if success, 
     */
//    public int delete(User category) {
//        int rowsAffected;
//
//        try (Connection conn = dataSource.getConnection();
//                PreparedStatement pst = conn.prepareStatement(getQuery("DELETE"));) {
//            pst.setInt(1, category.getId());
//            rowsAffected = pst.executeUpdate();
//        } catch (SQLException sqle) {
//            rowsAffected = -1;
//        } catch (Exception e) {
//            rowsAffected = -2;
//        }
//
//        return rowsAffected;
//    }
}
