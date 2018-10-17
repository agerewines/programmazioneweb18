/*
 * AA 2017-2018
 * Introduction to Web Programming
 * Lab 07 - ShoppingList List
 * UniTN
 */
package it.unitn.shoppinglesto.db.daos.jdbc;

import it.unitn.shoppinglesto.db.daos.ShoppingListDAO;
import it.unitn.shoppinglesto.db.daos.UserDAO;
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
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM List WHERE list_id = ?")) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();
                ShoppingList shoppingList = new ShoppingList();
                shoppingList.setId(rs.getInt("list_id"));
                shoppingList.setName(rs.getString("name"));
                shoppingList.setDescription(rs.getString("description"));
                shoppingList.setCategoryId(rs.getInt("category_id"));
                shoppingList.setCategoryId(rs.getInt("user_id"));

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
                    shoppingList.setId(rs.getInt("list_id"));
                    shoppingList.setName(rs.getString("name"));
                    shoppingList.setDescription(rs.getString("description"));
                    shoppingList.setCategoryId(rs.getInt("category_id"));
                    shoppingList.setCategoryId(rs.getInt("user_id"));

                    shoppingLists.add(shoppingList);

                    String str = null;
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
            getLists(shoppingLists, stm);
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM List WHERE list_id IN (SELECT sharedListId FROM UserList WHERE invitedUser = ?) ORDER BY name")) {
            stm.setInt(1, userId);
            getLists(shoppingLists, stm);
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
        return shoppingLists;
    }

    private void getLists(List<ShoppingList> shoppingLists, PreparedStatement stm) throws SQLException, DAOException {
        try (ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                ShoppingList shoppingList = new ShoppingList();
                shoppingList.setId(rs.getInt("list_id"));
                shoppingList.setName(rs.getString("name"));
                shoppingList.setDescription(rs.getString("description"));
                shoppingList.setCategoryId(rs.getInt("category_id"));
                shoppingList.setUserId(rs.getInt("user_id"));
                shoppingList.setUser(getUserAssociatedToList(rs.getInt("user_id")));
                shoppingLists.add(shoppingList);
            }
        }
    }

    private User getUserAssociatedToList(Integer id) throws DAOException {
        if (id == null) {
            throw new DAOException("Id is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM User WHERE id= ?")) {
            stm.setInt(1, id);
            User user = new User();
            try (ResultSet rs = stm.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    user.setId(rs.getInt("id"));
                    user.setMail(rs.getString("mail"));
                    user.setPassword(rs.getString("password"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setUuid(rs.getString("uuid"));
                    user.setActive(rs.getBoolean("active"));
                    user.setAdmin(rs.getBoolean("admin"));
                }
            }
            return user;
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }

    @Override
    public ShoppingList getTemporaryList(String uuid) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public List<ShoppingList> getUserLists(User user) throws DAOException {
        if(user == null){
            throw new DAOException("parameter not valid", new IllegalArgumentException("The passed user is null."));
        }
        // Find all list listed in List table
        List<ShoppingList> shoppingLists = new ArrayList<>();
        shoppingLists.addAll(getByUserId(user.getId()));
        // Find all list listed in UserList table
        return shoppingLists;

    }

    @Override
    public Integer save(ShoppingList entity) throws DAOException {
        if(entity == null){
            throw new DAOException("parameter not valid", new IllegalArgumentException("The shopping list is null."));
        }
        String insert = "INSERT INTO `List`(`name`, `description`, `image`, `category_id`, `user_id`)"
                + "VALUES (?,?,?,?,?)";
        try(PreparedStatement preparedStatement  = CON.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setString(3, entity.getImage());
            preparedStatement.setInt(4, entity.getCategoryId());
            preparedStatement.setInt(5, entity.getUserId());
            preparedStatement.executeUpdate();
            try(ResultSet rs  = preparedStatement.getGeneratedKeys()){
                if(rs.next()){
                    entity.setId(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DAOException("Impossible to insert shopping list.", ex);
        }
        return entity.getId();
    }

    @Override
    public Integer delete(Integer primaryKey) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
