package it.unitn.shoppinglesto.db.daos;

import it.unitn.shoppinglesto.db.entities.Category;
import it.unitn.shoppinglesto.db.entities.Photo;
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

    public void addPhoto(Photo photo) throws DAOException;

    /**
     * Retrieve all products associated to this list
     * @param shoppingList list that i wanna view
     * @return list of products insterted to this list
     * @throws DAOException if an error occurred during the operation.
     */
    public List<Product> getProductsByList(ShoppingList shoppingList) throws DAOException;

    /**
     * Retrieve all products that can be added into the list
     *
     * @param listId @return list of products able to be added to this list
     * @param search
     * @throws DAOException if an error occurred during the operation.
     */
    public List<Product> getAvailableProduct(Integer listId, String search) throws DAOException;

    /**
     * Retrieve photo path
     * @param photoId that im looking for
     * @return path of the photo
     * @throws DAOException if an error occurred during the operation.
     */
    public String getSinglePhoto(Integer photoId) throws DAOException;

    /**
     * Update product from edit servlet
     * @param product, the product to update
     * @return the updated product
     * @throws DAOException
     */
    public Product update(Product product) throws DAOException;

    /**
     * Get category of product
     * @param categoryId category id of the product
     * @return complete category
     * @throws DAOException
     */
    public Category getCategory(Integer categoryId) throws DAOException;

    public boolean deletePhoto(Integer photoId) throws DAOException;
}
