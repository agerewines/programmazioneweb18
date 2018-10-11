/*
 * AA 2017-2018
 * Introduction to Web Programming
 * Lab 07 - ShoppingList List
 * UniTN
 */
package it.unitn.shoppinglesto.db.daos;

import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;

/**
 * All concrete DAOs must implement this interface to handle the persistence
 * system that interact with {@link User users}.
 *
 * @author alessandrogerevini
 */
public interface UserDAO extends DAO<User, Integer> {

    /**
     * Returns the {@link User user} with the given {@code email} and
     * {@code password}.
     *
     * @param email the email of the user to get.
     * @param password the password of the user to get.
     * @return the {@link User user} with the given {@code username} and
     * {@code password}..
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     */
    public User getByEmailAndPassword(String email, String password) throws DAOException;

    /**
     * Returns the {@link User user} with the given {@code email} and
     * {@code password}.
     *
     * @param email the email of the user to get.
     * @param password the password of the user to get.
     * @return the {@link User user} with the given {@code username} and
     * {@code password}..
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     */
    public User getByMail(String mail) throws DAOException;

    /**
     * Checks whether an {@code mail} exists in the database.
     *
     * @param email the {@code mail} to check.
     * @return true if the email passed as parameter exists in the database, if
     * not false.
     * @throws DAOException if an error occurred during the operation.
     */
    public boolean emailExists(String mail) throws DAOException;

    /**
     * Update the @{link User user} passed as parameter and returns it.
     *
     * @param user the @{link User user} used to update the persistence system.
     * @return the updated user.
     * @throws DAOException if an error occurred during the action.
     *
     */
    public User update(User user) throws DAOException;

    /**
     * This method returns the temporary activation code that the {@link User user} receives when registering or during password reset
     * @param user the {@link User user} that receives the activation code.
     * @return the activation key stored in the database
     * @throws DAOException if an error occurs during the operation
     */
    public String getActivationKey(User user) throws DAOException;
    /**
     * Returns the user id associated with the activation key
     * @param activationKey the activation key used to check for the userId.
     * @return the id of the {@code User} that has the activation key.
     * @throws DAOException if an error occurred during the operation.
     */
    public Integer getUserIdByActKey(String activationKey) throws DAOException;

    /**
     * Deletes the activation keys associated with this {@code User}.
     * @param user the {@link User user} whose activation key will be deleted.
     * @return the result of the delete operation.
     * @throws DAOException if an error occurs during the operation.
     */
    public Integer deleteActKey(User user) throws DAOException;
}
