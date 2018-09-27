/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.shoppinglesto.db;

import it.unitn.shoppinglesto.db.entities.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author alessandrogerevini
 */
public class DBManager {

    private final transient Connection CON;
    private String ipaddress = "164.132.195.86:3306", databasename = "web_db", login = "root", pass = "Pasto_Lesto22";

    public DBManager() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver", true, getClass().getClassLoader());
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException(cnfe.getMessage(), cnfe.getCause());
        }
        System.out.println("Connesso al db");
        CON = (Connection) DriverManager.getConnection("jdbc:mysql://" + ipaddress + "/" + databasename, login, pass);
    }

    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:mysql:;shutdown=true");
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
                    user.setFirstName(rs.getString("name"));
                    user.setLastName(rs.getString("surname"));
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
