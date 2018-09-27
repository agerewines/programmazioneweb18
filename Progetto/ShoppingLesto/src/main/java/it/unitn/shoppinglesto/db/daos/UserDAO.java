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
 */
public interface UserDAO extends DAO<User, Integer> {
    /**
     * Returns the {@link User user} with the given {@code email} and
     * {@code password}.
     * @param email the email of the user to get.
     * @param password the password of the user to get.
     * @return the {@link User user} with the given {@code username} and
     * {@code password}..
     * @throws DAOException if an error occurred during the information
     * retrieving.
     * 
     */
    public User getByEmailAndPassword(String email, String password) throws DAOException;
}
