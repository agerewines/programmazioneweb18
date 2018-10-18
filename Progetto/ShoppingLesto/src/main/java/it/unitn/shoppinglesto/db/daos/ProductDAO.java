package it.unitn.shoppinglesto.db.daos;

import it.unitn.shoppinglesto.db.entities.Product;
import it.unitn.shoppinglesto.db.entities.ShoppingList;
import it.unitn.shoppinglesto.db.exceptions.DAOException;

import java.util.List;

/**
 * All concrete DAOs must implement this interface to handle the persistence
 * system that interact with {@link Product product}.
 *
 * @author alessandrogerevini
 */
public interface ProductDAO extends DAO<Product, Integer> {

    /**
     * Retrieve all products associated to this list
     * @param shoppingList list that i wanna view
     * @return list of products insterted to this list
     * @throws DAOException if an error occurred during the operation.
     */
    public List<Product> getProductsByList(ShoppingList shoppingList) throws DAOException;

}
