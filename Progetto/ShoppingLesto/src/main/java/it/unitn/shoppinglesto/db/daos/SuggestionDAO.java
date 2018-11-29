package it.unitn.shoppinglesto.db.daos;

import it.unitn.shoppinglesto.db.entities.Suggestion;
import it.unitn.shoppinglesto.db.exceptions.DAOException;

import java.util.List;

public interface SuggestionDAO extends DAO<Suggestion, Integer> {

    public List<Suggestion> getAllListSuggestion(Integer listId) throws DAOException;

    public Suggestion getByProdAndListId(Integer prodId, Integer listId) throws DAOException;

    public Suggestion update(Suggestion suggestion) throws DAOException;

    public boolean hasSuggestion(Integer prodId, Integer listId) throws DAOException;
}
