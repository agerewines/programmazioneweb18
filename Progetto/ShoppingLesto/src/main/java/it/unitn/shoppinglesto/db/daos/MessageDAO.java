package it.unitn.shoppinglesto.db.daos;

import it.unitn.shoppinglesto.db.entities.Message;
import it.unitn.shoppinglesto.db.exceptions.DAOException;

import java.sql.Timestamp;
import java.util.List;

public interface MessageDAO extends DAO<Message, Integer> {

    public List<Message> getAllListMessages(Integer listId) throws DAOException;

    public List<Message> getNewMessages(Integer listId, Timestamp lastMessage) throws DAOException;
}
