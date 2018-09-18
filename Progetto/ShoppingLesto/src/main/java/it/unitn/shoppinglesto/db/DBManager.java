/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.shoppinglesto.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import it.unitn.shoppinglesto.db.beans.*;

/**
 *
 * @author alessandrogerevini
 */
public class DBManager {

    private final transient Connection CON;
    private String ipaddress = "164.132.195.86", databasename = "web_db", login = "root", pass = "PastoLesto22";

    public DBManager() throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver", true, getClass().getClassLoader());
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException(cnfe.getMessage(), cnfe.getCause());
        }
        System.out.println("Connesso al db");
        CON = (Connection) DriverManager.getConnection("jdbc:derby://" + ipaddress + "/" + databasename, login, pass);
    }

    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException sqle) {
            Logger.getLogger(DBManager.class.getName()).info(sqle.getMessage());
        }
    }

    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM User ORDER BY surname")) {
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    User user = new User();
                    user.setMail(rs.getString("mail"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    user.setSurname(rs.getString("surname"));
                    user.setAvatar("avatar");

                    /*try (PreparedStatement slstm = CON.prepareStatement("SELECT count(id_shopping_list) FROM users_shopping_lists WHERE id_user = ?")) {
                        slstm.setInt(1, user.getId());
                        try (ResultSet slrs = slstm.executeQuery()) {
                            slrs.next();
                            user.setShoppingListsCount(slrs.getInt(1));
                        }
                    }*/
                    users.add(user);
                }
            }
        }

        return users;
    }

}
