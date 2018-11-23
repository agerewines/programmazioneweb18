package it.unitn.shoppinglesto.db.daos;

import it.unitn.shoppinglesto.db.entities.Category;
import it.unitn.shoppinglesto.db.entities.Photo;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;

/**
 * All concrete DAOs must implement this interface to handle the persistence
 * system that interact with {@link Category category}.
 *
 * @author alessandrogerevini
 */
public interface ListCategoryDAO extends DAO<Category, Integer> {

    public void addPhoto(Photo photo) throws DAOException;

    /**
     * Get path of a certain {@link Photo photo}
     * @param photoId id of the {@link Photo photo} that i want
     * @return path of the {@link Photo photo} that i need
     * @throws DAOException
     */
    public String getPhotoPath(Integer photoId) throws DAOException;

    /**
     * Update the @{link Category category} passed as parameter and returns it.
     * @param cat the @{link Category category} used to update the persistence system.
     * @return the updated category.
     * @throws DAOException if an error occurred during the action.
     */
    public Category update(Category cat) throws DAOException;

    /**
     * Update the @{link Category category} passed as parameter and returns it.
     * @param cat the @{link Category category} used to update the persistence system.
     * @return the updated category.
     * @throws DAOException if an error occurred during the action.
     */
    public Category simpleUpdate(Category cat) throws DAOException;

    public boolean deletePhoto(Integer photoId) throws DAOException;

    public String getCategoryNameByUser(User user) throws DAOException;
}
