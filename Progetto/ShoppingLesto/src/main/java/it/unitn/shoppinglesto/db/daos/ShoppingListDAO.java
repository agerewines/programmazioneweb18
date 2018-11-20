/*
 * AA 2017-2018
 * Introduction to Web Programming
 * Lab 07 - ShoppingList List
 * UniTN
 */
package it.unitn.shoppinglesto.db.daos;

import it.unitn.shoppinglesto.db.entities.Product;
import it.unitn.shoppinglesto.db.entities.ShoppingList;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;
import java.util.List;

/**
 * All concrete DAOs must implement this interface to handle the persistence 
 * system that interact with {@link ShoppingList shopping-lists}.
 * 
 */
public interface ShoppingListDAO extends DAO<ShoppingList, Integer> {
    /**
     * Persists the new {@link ShoppingList shopping-list} passed as parameter
     * to the storage system.
     * @param shoppingList the new {@code shopping-list} to persist.
     * @return the id of the new persisted record.
     * @throws DAOException if an error occurred during the persist action.
     * 
     */
    public Integer insert(ShoppingList shoppingList) throws DAOException;
    
    /**
     * Links the passed {@code shopping_list} with the passed {@code user}.
     * @param shoppingList the shopping_list to link.
     * @param user the user to link.
     * @return {@code true} if the link has correctly persisted on the storage
     * system.
     * @throws DAOException if an error occurred during the persist action.
     * 
     */
    public boolean linkShoppingListToUser(ShoppingList shoppingList, User user) throws DAOException;
    
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
     */
    public List<ShoppingList> getByUserId(Integer userId) throws DAOException;

    /**
     * Returns the temporary {@link ShoppingList list}  of an anonymous user identified by the unique {@link String uuid}.
     * @param uuid the {@link String uuid} stored in the cookie and database which identifies the inactive {@link ShoppingList list}
     * @return the inactive {@link ShoppingList list}
     * @throws DAOException if an error occurs during the retrieving of the list.
     */
    public ShoppingList getTemporaryList(String uuid) throws DAOException;

    /**
     * Returns all entities of type {@code ShoppingList} belonging or shared with specific {@link User user}.
     * @param user the {@link User user} who owns or shares the lists.
     * @return all entities of type {@code ShoppingList} belonging to the user.
     * @throws DAOException if an error occurs during the retrieving of the lists.
     */
    public List<ShoppingList> getUserLists(User user) throws DAOException;

    /**
     * Checks if {@link User user} is allowed to view the {@link ShoppingList list}
     * @param user the {@link User user} to check.
     * @param shoppingList the {@link ShoppingList list} where to check.
     * @return {@code true} if {@link User user} is in {@link ShoppingList list}.
     * @throws DAOException if an error occurs during the operation.
     */
    public boolean isUserInList(User user, ShoppingList shoppingList) throws DAOException;

    /**
     * Update the @{link ShoppingList list} passed as parameter and returns it.
     *
     * @param list the @{link ShoppingList list} used to update the persistence system.
     * @return the updated list.
     * @throws DAOException if an error occurred during the action.
     *
     */
    public ShoppingList update(ShoppingList list) throws DAOException;

    /**
     * Checks the {@link User user} permissions of the {@link ShoppingList list}
     * @param user the {@link User user} to check.
     * @param shoppingList the {@link ShoppingList list} to check.
     * @return {@link ShoppingList} updated with {@link User} permissions
     * @throws DAOException if an error occurs during the operation.
     */
    public ShoppingList getPermissions(User user, ShoppingList shoppingList) throws DAOException;

    /**
     * Get a list of {@link User user} available to be shared with
     * @param shoppingList the {@link ShoppingList list} to check.
     * @return List of {@link User user} available
     * @throws DAOException if an error occurs during the operation.
     */
    public List<User> getSharableUsers(ShoppingList shoppingList) throws DAOException;

    /**
     * Share a list and permission to a given {@link User user}
     * @param list the {@link ShoppingList list} to share.
     * @param userToShare the {@link User user} shared with.
     * @param edit edit list permission
     * @param add add product into the list permission
     * @param share share list to someone else permission
     * @throws DAOException if an error occurs during the operation.
     */
    public boolean shareListTo(ShoppingList list, User userToShare, boolean edit, boolean add, boolean share) throws DAOException;


    /**
     * Edit permit of {@link User user} in {@link ShoppingList list}
     * @param list the {@link ShoppingList list} to share.
     * @param userToShare the {@link User user} shared with.
     * @param edit edit list permission
     * @param add add product into the list permission
     * @param share share list to someone else permission
     * @throws DAOException if an error occurs during the operation.
     */
    public boolean editPermit(ShoppingList list, User userToShare, boolean edit, boolean add, boolean share) throws DAOException;


    public Integer getListOwner(ShoppingList shoppingList) throws DAOException;

    /**
     * Get a list of {@link User user} that the {@link ShoppingList shoppingList} is shared with
     * @param shoppingList the {@link ShoppingList list} to check.
     * @return List of shared {@link User user}
     * @throws DAOException if an error occurs during the operation.
     */
    public List<User> getSharedUser(ShoppingList shoppingList) throws DAOException;

    /**
     * Add product to certain list
     * @param listId shoppinglist thats gonna be modified
     * @param productId product to add
     * @throws DAOException if an error occurs during the operation.
     */
    public void addProductToList(Integer listId, Integer productId) throws DAOException;

    /**
     * Delete product to certain list
     * @param listId shoppinglist thats gonna be modified
     * @param productId product to delete
     * @throws DAOException if an error occurs during the operation.
     */
    public void deleteProductFromList(Integer listId, Integer productId) throws DAOException;

    /**
     * Remove user list link
     * @param listId shoppinglist thats gonna be unshared
     * @param userId user to delete
     * @return 1 if executed
     * @throws DAOException if an error occurs during the operation.
     */
    public Integer removePermit(Integer listId, Integer userId) throws DAOException;

    /**
     * Saves the entity instance passed as parameter to the storage system
     *
     * @param list the instance of type @{link ShoppingList}
     * @return the id of the entity stored in the database
     * @throws DAOException if an error occurred during the action
     */
    public Integer saveAnon(ShoppingList list) throws DAOException;

    public ShoppingList updateAnon(ShoppingList list) throws DAOException;

}
