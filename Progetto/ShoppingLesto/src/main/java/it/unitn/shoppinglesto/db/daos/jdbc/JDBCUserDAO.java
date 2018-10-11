/*
 * AA 2017-2018
 * Introduction to Web Programming
 * Lab 07 - ShoppingList List
 * UniTN
 */
package it.unitn.shoppinglesto.db.daos.jdbc;

import it.unitn.shoppinglesto.db.daos.UserDAO;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.codec.digest.DigestUtils;

import javax.sound.midi.Soundbank;

/**
 * The JDBC implementation of the {@link UserDAO} interface.
 *
 * @author alessandrogerevini
 */
public class JDBCUserDAO extends JDBCDAO<User, Integer> implements UserDAO {

    public JDBCUserDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM User");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count users", ex);
        }

        return 0L;
    }

    /**
     * Returns the {@link User user} with the primary key equals to the one
     * passed as parameter.
     *
     * @param primaryKey the {@code id} of the {@code user} to get.
     * @return the {@code user} with the id equals to the one passed as
     * parameter or {@code null} if no entities with that id are present into
     * the storage system.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author alessandrogerevini
     */
    @Override
    public User getByPrimaryKey(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM User WHERE id = ?")) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setMail(rs.getString("mail"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setAvatar(rs.getString("avatar"));
                user.setActive(Boolean.getBoolean(rs.getString("active")));
                user.setAdmin(Boolean.getBoolean(rs.getString("admin")));
                return user;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the user for the passed primary key", ex);
        }
    }

    /**
     * Returns the {@link User user} with the given {@code email} and
     * {@code password}.
     *
     * @param email the email of the user to get.
     * @param password the password of the user to get.
     * @return the {@link User user} with the given {@code email} and
     * {@code password}..
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author alessandrogerevini
     */
    @Override
    public User getByEmailAndPassword(String email, String password) throws DAOException {
        if ((email == null) || (password == null)) {
            throw new DAOException("Email and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM User WHERE mail = ? AND password = ?")) {
            stm.setString(1, email);
            stm.setString(2, password);
            return getUser(stm);
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }

    /**
     * Returns the list of all the valid {@link User users} stored by the
     * storage system.
     *
     * @return the list of all the valid {@code users}.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author alessandrogerevini
     */
    @Override
    public List<User> getAll() throws DAOException {
        List<User> users = new ArrayList<>();

        try (Statement stm = CON.createStatement()) {
            try (ResultSet rs = stm.executeQuery("SELECT * FROM User ORDER BY lastName")) {

                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setMail(rs.getString("mail"));
                    user.setPassword(rs.getString("password"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setAvatar(rs.getString("avatar"));
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

    @Override
    public User getByMail(String mail) throws DAOException {
        if (mail == null) {
            throw new DAOException("Email and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM User WHERE mail = ?")) {
            stm.setString(1, mail);
            return getUser(stm);
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }

    /**
     * Duplicated code
     * Retrieve user information from sql query
     * @param stm sql code to run
     * @return The {@code user} object got from the query
     * @throws SQLException error with the sql code
     * @throws DAOException error completing the UserDao
     */
    private User getUser(PreparedStatement stm) throws SQLException, DAOException {
        try (ResultSet rs = stm.executeQuery()) {
            int count = 0;
            while (rs.next()) {
                count++;
                if (count > 1) {
                    throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                }
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setMail(rs.getString("mail"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setAvatar(rs.getString("avatar"));
                user.setActive(Boolean.getBoolean(rs.getString("active")));
                user.setAdmin(Boolean.getBoolean(rs.getString("admin")));

                return user;
            }
            return null;
        }
    }

    /**
     * Checks whether an {@code mail} exists in the database.
     *
     * @param mail the {@code mail} to check.
     * @return true if the mail passed as parameter exists in the database, if
     * not false.
     * @throws DAOException if an error occurred during the operation.
     */
    @Override
    public boolean emailExists(String mail) throws DAOException {
        boolean exists = false;
        if (mail == null) {
            throw new DAOException("Email cannot be null!");
        }

        String checkQuery = "SELECT * FROM User WHERE mail=?";
        try {
            PreparedStatement prepStm = CON.prepareStatement(checkQuery);
            prepStm.setNString(1, mail);
            try (ResultSet rs = prepStm.executeQuery()) {
                if (rs.next()) {
                    exists = true;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error on checking email's parameters", ex);
        }
        return exists;
    }

    @Override
    public Integer save(User user) throws DAOException {
        if (user == null) {
            throw new DAOException("parameter not valid", new IllegalArgumentException("The passed user is null"));
        }
        if (!emailExists(user.getMail())) {
            String insert = "INSERT INTO `User`(`firstName`, `lastName`, `mail`, `password`, `admin`, `active`)"
                    + " VALUES (?,?,?,?,?,?)";
            try (PreparedStatement prepStm = CON.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
                prepStm.setString(1, user.getFirstName());
                prepStm.setString(2, user.getLastName());
                prepStm.setString(3, user.getMail());
                prepStm.setString(4, user.getPassword());
                prepStm.setBoolean(5, user.isAdmin());
                prepStm.setBoolean(6, user.isActive());
                prepStm.executeUpdate();
                try (ResultSet rs = prepStm.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt(1));
                        String insertActivation = "INSERT INTO UserActivationKeys (activationKey, userId)"
                                + "VALUES(?,?)";
                        PreparedStatement pstm2 = CON.prepareStatement(insertActivation);
                        pstm2.setString(1, DigestUtils.md5Hex(user.getMail()));
                        pstm2.setInt(2, user.getId());
                        pstm2.executeUpdate();
                    }
                }
            } catch (SQLException ex) {
                throw new DAOException("There was a problem saving the user into db", ex);
            }
        } else {
            throw new DAOException("Violation of email constraint");
        }
        return user.getId();
    }

    /**
     * Update the @{link User user} passed as parameter and returns it.
     *
     * @param user the @{link User user} used to update the persistence system.
     * @return the updated user.
     * @throws DAOException if an error occurred during the action.
     *
     */
    @Override
    public User update(User user) throws DAOException {
        if (user == null) {
            throw new DAOException("User null, not valid.", new IllegalArgumentException("User null."));
        }

        try (PreparedStatement prepStm = CON.prepareStatement("UPDATE `User` SET `mail` = ?, `password` = ?, `firstName` = ?, `lastName` = ?, `avatar` = ?, `active` = ? WHERE `id` = ?")) {
            prepStm.setString(1, user.getMail());
            prepStm.setString(2, user.getPassword());
            prepStm.setString(3, user.getFirstName());
            prepStm.setString(4, user.getLastName());
            prepStm.setString(5, user.getAvatar());
            prepStm.setBoolean(6, user.isActive());
            prepStm.setInt(7, user.getId());
            if (prepStm.executeUpdate() == 1) {
                return user;
            } else {
                throw new DAOException("Error while updating the user");
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while updating the user!", ex);
        }
    }

    @Override
    public String getActivationKey(User user) throws DAOException {
        if(user == null){
            throw new DAOException("parameter not valid", new IllegalArgumentException("The passed user is null"));
        }
        String activationKey = null;
        String query = "SELECT activationKey FROM UserActivationKeys WHERE userId = ?";
        try(PreparedStatement pstm = CON.prepareStatement(query)){
            pstm.setInt(1, user.getId());
            try(ResultSet rs = pstm.executeQuery()){
                if(rs.next())
                    activationKey = rs.getString(1);
            }
        } catch (SQLException ex) {
            throw new DAOException("Could not retrieve activation key", ex);
        }
        return activationKey;
    }

    @Override
    public Integer getUserIdByActKey(String activationKey) throws DAOException {
        if(activationKey == null){
            throw new DAOException("The parameter is wrong", new IllegalArgumentException("The passed key is null"));
        }
        Integer id = null;
        String query = "SELECT userId FROM UserActivationKeys WHERE activationKey = ?";
        try(PreparedStatement preparedStatement = CON.prepareStatement(query)){
            preparedStatement.setString(1, activationKey);
            try(ResultSet rs = preparedStatement.executeQuery()){
                if(rs.next())
                    id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error retrieving user id", ex);
        }
        return id;
    }

    /**
     * Deletes the activation keys associated with this {@code User}.
     * @param user the {@link User user} whose activation key will be deleted.
     * @return the result of the delete operation.
     * @throws DAOException if an error occurs during the operation.
     */
    @Override
    public Integer deleteActKey(User user) throws DAOException {
        if(user == null)
            throw new DAOException("User not valid", new IllegalArgumentException("The passed user is null"));

        Integer ret = null;
        String query = "DELETE FROM UserActivationKeys WHERE userId = ?";
        try(PreparedStatement preparedStatement = CON.prepareStatement(query)){
            preparedStatement.setInt(1, user.getId());
            ret = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Could not delete record.", ex);
        }

        return ret;
    }

    @Override
    public Integer delete(Integer primaryKey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
