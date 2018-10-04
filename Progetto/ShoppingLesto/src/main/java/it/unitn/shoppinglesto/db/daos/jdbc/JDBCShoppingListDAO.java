/*
 * AA 2017-2018
 * Introduction to Web Programming
 * Lab 07 - ShoppingList List
 * UniTN
 */
package it.unitn.shoppinglesto.db.daos.jdbc;

import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.entities.ShoppingList;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The JDBC implementation of the {@link ShoppingListDAO} interface.
 *
 * @author alessandrogerevini
 */
public class JDBCShoppingListDAO extends JDBCDAO<ShoppingList, Integer> implements ShoppingListDAO {
    

    public JDBCShoppingListDAO(Connection con) {
        super(con);
    }

    /**
     * Returns the number of {@link ShoppingList shopping_lists} stored on the
     * persistence system of the application.
     *
     * @return the number of records present into the storage system.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author alessandrogerevini
     */
    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM List");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count lists", ex);
        }

        return 0L;
    }

    /**
     * Returns the {@link ShoppingList shopping_lists} with the primary key
     * equals to the one passed as parameter.
     *
     * @param primaryKey the {@code id} of the {@code shopping_list} to get.
     * @return the {@code shopping_list} with the id equals to the one passed as
     * parameter or {@code null} if no entities with that id are present into
     * the storage system.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author alessandrogerevini
     */
    @Override
    public ShoppingList getByPrimaryKey(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM List WHERE id = ?")) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();
                ShoppingList shoppingList = new ShoppingList();
                shoppingList.setId(rs.getInt("id"));
                shoppingList.setName(rs.getString("name"));
                shoppingList.setDescription(rs.getString("description"));

                return shoppingList;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the shopping_list for the passed primary key", ex);
        }
    }

    /**
     * Returns the list of all the valid {@link ShoppingList shopping_lists}
     * stored by the storage system.
     *
     * @return the list of all the valid {@code shopping_lists}.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author alessandrogerevini
     */
    @Override
    public List<ShoppingList> getAll() throws DAOException {
        List<ShoppingList> shoppingLists = new ArrayList<>();

        try (Statement stm = CON.createStatement()) {
            try (ResultSet rs = stm.executeQuery("SELECT * FROM List ORDER BY name")) {

                while (rs.next()) {
                    ShoppingList shoppingList = new ShoppingList();
                    shoppingList.setId(rs.getInt("id"));
                    shoppingList.setName(rs.getString("name"));
                    shoppingList.setDescription(rs.getString("description"));

                    shoppingLists.add(shoppingList);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of shopping_lists", ex);
        }

        return shoppingLists;
    }

    /**
     * Persists the new {@link ShoppingList shopping-list} passed as parameter
     * to the storage system.
     * @param shoppingList the new {@code shopping-list} to persist.
     * @return the id of the new persisted record.
     * @throws DAOException if an error occurred during the persist action.
     * 
     * @author alessandrogerevini
     */
    @Override
    public Integer insert(ShoppingList shoppingList) throws DAOException {
        try (PreparedStatement ps = CON.prepareStatement("INSERT INTO List (name, description) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, shoppingList.getName());
            ps.setString(2, shoppingList.getDescription());

            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                shoppingList.setId(rs.getInt(1));
            }
            
            return shoppingList.getId();
        } catch (SQLException ex) {
            throw new DAOException("Impossible to insert the new shopping_list", ex);
        }
    }
    
    /**
     * Links the passed {@code shopping_list} with the passed {@code user}.
     * @param shoppingList the shopping_list to link.
     * @param user the user to link.
     * @return {@code true} if the link has correctly persisted on the storage
     * system.
     * @throws DAOException if an error occurred during the persist action.
     * 
     * @author alessandrogerevini
     */
    @Override
    public boolean linkShoppingListToUser(ShoppingList shoppingList, User user) throws DAOException {
        if ((shoppingList == null) || (user == null)) {
            throw new DAOException("Shopping list and user are mandatory fields", new NullPointerException("shopping list or user are null"));
        }
        
        try (PreparedStatement ps = CON.prepareStatement("INSERT INTO UserList (invitedUser, sharedList) VALUES (?, ?)")) {

            ps.setInt(1, user.getId());
            ps.setInt(2, shoppingList.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to link the passed shopping_list with the passed user", ex);
        }
    }

    /**
     * Returns the list of {@link ShoppingList shopping-lists} with the
     * {@code id_user} is the one passed as parameter.
     *
     * @param userId the {@code id} of the {@code user} for which retrieve the
     * shopping-lists list.
     * @return the list of {@code shopping-list} with the user id equals to the
     * one passed as parameter or an empty list if user id is not linked to any
     * to-dos.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author alessandrogerevini
     */
    @Override
    public List<ShoppingList> getByUserId(Integer userId) throws DAOException {
        if (userId == null) {
            throw new DAOException("userId is mandatory field", new NullPointerException("userId is null"));
        }
        List<ShoppingList> shoppingLists = new ArrayList<>();
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM List WHERE user_id = ? ORDER BY name")) {
            stm.setInt(1, userId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    ShoppingList shoppingList = new ShoppingList();
                    shoppingList.setId(rs.getInt("id"));
                    shoppingList.setName(rs.getString("name"));
                    shoppingList.setDescription(rs.getString("description"));

                    shoppingLists.add(shoppingList);
                }

                
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM List WHERE list_id IN (SELECT sharedList FROM UserList WHERE invitedUser = ?) ORDER BY name")) {
            stm.setInt(1, userId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    ShoppingList shoppingList = new ShoppingList();
                    shoppingList.setId(rs.getInt("id"));
                    shoppingList.setName(rs.getString("name"));
                    shoppingList.setDescription(rs.getString("description"));

                    shoppingLists.add(shoppingList);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
        return shoppingLists;
    }

    @Override
    public Integer save(ShoppingList entity) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer delete(Integer primaryKey) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
