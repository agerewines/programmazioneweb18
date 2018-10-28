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
     *                      retrieving.
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
     *                      retrieving.
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
                shoppingList.setUserId(rs.getInt("user_id"));
                shoppingList.setImage(rs.getString("image"));
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
     *                      retrieving.
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
                    shoppingList.setImage(rs.getString("image"));
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
     *
     * @param shoppingList the new {@code shopping-list} to persist.
     * @return the id of the new persisted record.
     * @throws DAOException if an error occurred during the persist action.
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
     *
     * @param shoppingList the shopping_list to link.
     * @param user         the user to link.
     * @return {@code true} if the link has correctly persisted on the storage
     * system.
     * @throws DAOException if an error occurred during the persist action.
     * @author alessandrogerevini
     */
    @Override
    public boolean linkShoppingListToUser(ShoppingList shoppingList, User user) throws DAOException {
        if ((shoppingList == null) || (user == null)) {
            throw new DAOException("Shopping list and user are mandatory fields", new NullPointerException("shopping list or user are null"));
        }

        try (PreparedStatement ps = CON.prepareStatement("INSERT INTO UserList (invitedUser, sharedListId) VALUES (?, ?)")) {

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
     *               shopping-lists list.
     * @return the list of {@code shopping-list} with the user id equals to the
     * one passed as parameter or an empty list if user id is not linked to any
     * to-dos.
     * @throws DAOException if an error occurred during the information
     *                      retrieving.
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
                    shoppingList.setId(rs.getInt("list_id"));
                    shoppingList.setName(rs.getString("name"));
                    shoppingList.setImage(rs.getString("image"));
                    shoppingList.setDescription(rs.getString("description"));
                    shoppingList.setCategoryId(rs.getInt("category_id"));
                    shoppingList.setUserId(rs.getInt("user_id"));
                    shoppingList.setUser(getUserAssociatedToList(rs.getInt("user_id")));
                    shoppingList.setPermissions(true);
                    shoppingLists.add(shoppingList);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM List WHERE list_id IN (SELECT sharedListId FROM UserList WHERE invitedUser = ?) ORDER BY name")) {
            stm.setInt(1, userId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    ShoppingList shoppingList = new ShoppingList();
                    shoppingList.setId(rs.getInt("list_id"));
                    shoppingList.setName(rs.getString("name"));
                    shoppingList.setImage(rs.getString("image"));
                    shoppingList.setDescription(rs.getString("description"));
                    shoppingList.setCategoryId(rs.getInt("category_id"));
                    shoppingList.setUserId(rs.getInt("user_id"));
                    shoppingList.setUser(getUserAssociatedToList(rs.getInt("user_id")));
                    try (PreparedStatement stm2 = CON.prepareStatement("SELECT * FROM UserList WHERE invitedUser = ? AND sharedListId = ?")) {
                        stm2.setInt(1, userId);
                        stm2.setInt(2, shoppingList.getId());
                        try (ResultSet rs2 = stm2.executeQuery()) {
                            rs2.next();
                            shoppingList.setShare(rs2.getBoolean("share"));
                            shoppingList.setEdit(rs2.getBoolean("edit"));
                            shoppingList.setAdd(rs2.getBoolean("add"));
                        }
                    } catch (SQLException ex) {
                        throw new DAOException("Impossible to get the shopping_list shared with the user", ex);
                    }

                    shoppingLists.add(shoppingList);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of shoppinglist", ex);
        }
        return shoppingLists;
    }

    public User getUserAssociatedToList(Integer id) throws DAOException {
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
        if (user == null) {
            throw new DAOException("parameter not valid", new IllegalArgumentException("The passed user is null."));
        }
        // Find all list listed in List table
        // Find all list listed in UserList table
        return new ArrayList<>(getByUserId(user.getId()));

    }

    @Override
    public boolean isUserInList(User user, ShoppingList shoppingList) throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM UserList WHERE sharedListId = ? AND invitedUser = ?")) {
            stm.setInt(1, shoppingList.getId());
            stm.setInt(2, user.getId());
            try (ResultSet rs = stm.executeQuery()) {
                return (rs.next() || user.getId().equals(getListOwner(shoppingList)));
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to know if the user is in the list", ex);
        }
    }

    @Override
    public ShoppingList update(ShoppingList list) throws DAOException {
        if (list == null) {
            throw new DAOException("list is null, not valid", new IllegalArgumentException("User null."));
        }
        try (PreparedStatement prepStm = CON.prepareStatement("UPDATE `List` SET `name` = ?, `description` = ?, `image` = ?, `user_id` = ?, `category_id` = ?  WHERE `list_id` = ?")) {
            prepStm.setString(1, list.getName());
            prepStm.setString(2, list.getDescription());
            prepStm.setString(3, list.getImage());
            prepStm.setInt(4, list.getUserId());
            prepStm.setInt(5, list.getCategoryId());
            prepStm.setInt(6, list.getId());
            if (prepStm.executeUpdate() == 1) {
                return list;
            } else {
                throw new DAOException("Error while saving the list");
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while updating the shopping list!", ex);
        }
    }

    /**
     * Checks the {@link User user} permissions of the {@link ShoppingList list}
     *
     * @param user         the {@link User user} to check.
     * @param shoppingList the {@link ShoppingList list} to check.
     * @return {@link ShoppingList} updated with {@link User} permissions
     * @throws DAOException if an error occurs during the operation.
     */
    @Override
    public ShoppingList getPermissions(User user, ShoppingList shoppingList) throws DAOException {
        if (shoppingList == null || user == null) {
            throw new DAOException("Missing fields checking permissions", new IllegalArgumentException("User null."));
        }
        if (user.getId().equals(shoppingList.getUserId())) {
            shoppingList.setPermissions(true);
        } else {
            try (PreparedStatement stm2 = CON.prepareStatement("SELECT * FROM UserList WHERE invitedUser = ? AND sharedListId = ?")) {
                stm2.setInt(1, user.getId());
                stm2.setInt(2, shoppingList.getId());
                try (ResultSet rs2 = stm2.executeQuery()) {
                    rs2.next();
                    shoppingList.setShare(rs2.getBoolean("share"));
                    shoppingList.setEdit(rs2.getBoolean("edit"));
                    shoppingList.setAdd(rs2.getBoolean("add"));
                }
            } catch (SQLException ex) {
                throw new DAOException("Impossible to get the shopping_list shared with the user", ex);
            }

        }
        return shoppingList;
    }

    /**
     * Get a list of {@link User user} available to be shared with
     *
     * @param shoppingList the {@link ShoppingList list} to check.
     * @return List of {@link User user} available
     * @throws DAOException if an error occurs during the operation.
     */
    @Override
    public List<User> getSharableUsers(ShoppingList shoppingList) throws DAOException {
        List<User> users = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM User WHERE anonymous = 0 AND active = 1 AND admin = 0 AND id NOT IN ((SELECT user_id FROM list WHERE list_id = ?) UNION (SELECT inviteduser FROM UserList WHERE sharedListid = ?))")) {
            stm.setInt(1, shoppingList.getId());
            stm.setInt(2, shoppingList.getId());
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setMail(rs.getString("mail"));
                    user.setPassword(rs.getString("password"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setUuid(rs.getString("uuid"));
                    user.setActive(Boolean.getBoolean(rs.getString("active")));
                    user.setAdmin(Boolean.getBoolean(rs.getString("admin")));
                    users.add(user);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }

        return users;
    }

    /**
     * Share a list and permission to a given {@link User user}
     *
     * @param list        the {@link ShoppingList list} to share.
     * @param userToShare the {@link User user} shared with.
     * @param edit        edit list permission
     * @param add         add product into the list permission
     * @param share       share list to someone else permission
     * @throws DAOException if an error occurs during the operation.
     */
    public boolean shareListTo(ShoppingList list, User userToShare, boolean edit, boolean add, boolean share) throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("INSERT INTO UserList (`invitedUser`, `sharedListId`, `add`, `share`, `edit`) VALUES (?, ?, ?, ?, ?)")) {
            stm.setInt(1, userToShare.getId());
            stm.setInt(2, list.getId());
            stm.setBoolean(3, add);
            stm.setBoolean(4, share);
            stm.setBoolean(5, edit);
            return stm.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DAOException("Impossible to insert the shared user and permissions");
        }
    }


    public Integer getListOwner(ShoppingList shoppingList) throws DAOException {
        Integer owner = null;
        try (PreparedStatement preparedStatement = CON.prepareStatement("SELECT user_id FROM List WHERE list_id = ?")) {
            preparedStatement.setInt(1, shoppingList.getId());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next())
                    owner = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException("Impossible to get the owner", e);
        }
        return owner;
    }

    /**
     * Get a list of {@link User user} that the {@link ShoppingList shoppingList} is shared with
     *
     * @param shoppingList the {@link ShoppingList list} to check.
     * @return List of shared {@link User user}
     * @throws DAOException if an error occurs during the operation.
     */
    @Override
    public List<User> getSharedUser(ShoppingList shoppingList) throws DAOException {
        if(shoppingList == null)
            throw new DAOException("list not valid", new IllegalArgumentException("The shopping list is null"));
        List< User > sharedWith = new ArrayList<>();
        try(PreparedStatement preparedStatement = CON.prepareStatement("SELECT * FROM User WHERE id IN ( SELECT invitedUser FROM UserList WHERE sharedListId = ?)")){
            preparedStatement.setInt(1, shoppingList.getId());
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setMail(resultSet.getString("mail"));
                    user.setFirstName(resultSet.getString("firstName"));
                    user.setLastName(resultSet.getString("lastName"));
                    sharedWith.add(user);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Impossible to get the user that the list is shared with", e);
        }
        return sharedWith;
    }

    @Override
    public Integer save(ShoppingList entity) throws DAOException {
        if (entity == null) {
            throw new DAOException("parameter not valid", new IllegalArgumentException("The shopping list is null."));
        }
        String insert = "INSERT INTO `List`(`name`, `description`, `image`, `category_id`, `user_id`)"
                + "VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = CON.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setString(3, entity.getImage());
            preparedStatement.setInt(4, entity.getCategoryId());
            preparedStatement.setInt(5, entity.getUserId());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
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
