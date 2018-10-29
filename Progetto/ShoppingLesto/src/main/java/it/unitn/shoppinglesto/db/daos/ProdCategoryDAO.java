package it.unitn.shoppinglesto.db.daos;

import it.unitn.shoppinglesto.db.entities.Category;
/**
 * All concrete DAOs must implement this interface to handle the persistence
 * system that interact with {@link Category category}.
 *
 * @author alessandrogerevini
 */
public interface ProdCategoryDAO extends DAO<Category, Integer> {
}
