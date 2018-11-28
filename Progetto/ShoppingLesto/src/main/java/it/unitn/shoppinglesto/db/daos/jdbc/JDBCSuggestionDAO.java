package it.unitn.shoppinglesto.db.daos.jdbc;

import it.unitn.shoppinglesto.db.daos.SuggestionDAO;
import it.unitn.shoppinglesto.db.entities.Suggestion;
import it.unitn.shoppinglesto.db.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSuggestionDAO extends JDBCDAO<Suggestion, Integer> implements SuggestionDAO {

    public JDBCSuggestionDAO(Connection con) {
        super(con);
    }

    /**
     * Returns the number of records of {@code ENTITY_CLASS} stored on the
     * persistence system of the application.
     *
     * @return the number of records present into the storage system.
     * @throws DAOException if an error occurred during the information
     *                      retrieving.
     */
    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM Suggestion");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count suggestions", ex);
        }

        return 0L;
    }

    /**
     * Returns the {@code ENTITY_CLASS} instance of the storage system record
     * with the primary key equals to the one passed as parameter.
     *
     * @param primaryKey the primary key used to obtain the entity instance.
     * @return the {@code ENTITY_CLASS} instance of the storage system record
     * with the primary key equals to the one passed as parameter or
     * {@code null} if no entities with that primary key is present into the
     * storage system.
     * @throws DAOException if an error occurred during the information
     *                      retrieving.
     */
    @Override
    public Suggestion getByPrimaryKey(Integer primaryKey) throws DAOException {
        if(primaryKey == null){
            throw new DAOException("primary key is null");
        }
        Suggestion suggestion = null;
        try(PreparedStatement preparedStatement = CON.prepareStatement("SELECT * FROM Suggestion WHERE id = ?")){
            preparedStatement.setInt(1, primaryKey);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                suggestion = new Suggestion();
                suggestion.setId(resultSet.getInt("id"));
                suggestion.setIdProd(resultSet.getInt("idProd"));
                suggestion.setIdList(resultSet.getInt("idList"));
                suggestion.setAverage(resultSet.getInt("average"));
                suggestion.setCounter(resultSet.getInt("counter"));
                suggestion.setFirst(resultSet.getTime("first"));
                suggestion.setLast(resultSet.getTime("last"));
                suggestion.setSeen(resultSet.getBoolean("seen"));
            }

        } catch (SQLException e) {
            throw new DAOException("cannot retrieve suggestion");
        }
        return suggestion;
    }

    /**
     * Returns the list of all the valid entities of type {@code ENTITY_CLASS}
     * stored by the storage system.
     *
     * @return the list of all the valid entities of type {@code ENTITY_CLASS}.
     * @throws DAOException if an error occurred during the information
     *                      retrieving.
     */
    @Override
    public List<Suggestion> getAll() throws DAOException {
        List<Suggestion> suggestions = new ArrayList<>();
        try(PreparedStatement preparedStatement = CON.prepareStatement("SELECT * FROM Suggestion")){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                Suggestion suggestion = new Suggestion();
                suggestion.setId(resultSet.getInt("id"));
                suggestion.setIdProd(resultSet.getInt("idProd"));
                suggestion.setIdList(resultSet.getInt("idList"));
                suggestion.setAverage(resultSet.getInt("average"));
                suggestion.setCounter(resultSet.getInt("counter"));
                suggestion.setFirst(resultSet.getTime("first"));
                suggestion.setLast(resultSet.getTime("last"));
                suggestion.setSeen(resultSet.getBoolean("seen"));
                suggestions.add(suggestion);
            }

        } catch (SQLException e) {
            throw new DAOException("cannot retrieve suggestion");
        }
        return suggestions;
    }

    /**
     * Saves the entity instance passed as parameter to the storage system
     *
     * @param entity the instance of type @{link ENTITY_CLASS}
     * @return the id of the entity stored in the database
     * @throws DAOException if an error occurred during the action
     */
    @Override
    public Integer save(Suggestion entity) throws DAOException {
        if (entity == null) {
            throw new DAOException("parameter not valid", new IllegalArgumentException("The suggestion is null."));
        }
        String insert = "INSERT INTO `Suggestion`(`idProd`, `idList`, `counter`, `average`, `first`, `last`, `seen`)"
                + "VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = CON.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, entity.getIdProd());
            preparedStatement.setInt(2, entity.getIdList());
            preparedStatement.setInt(3, entity.getCounter());
            preparedStatement.setInt(4, entity.getAverage());
            preparedStatement.setTime(5, entity.getFirst());
            preparedStatement.setTime(6, entity.getLast());
            preparedStatement.setBoolean(7, entity.isSeen());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DAOException("Impossible to insert suggestion.", ex);
        }
        return entity.getId();
    }

    /**
     * Deletes the entity instance passed as parameter from the storage system.
     *
     * @param primaryKey the id of {@link Suggestion} to delete.
     * @return the result of the delete operation
     * @throws DAOException if an error occurred during the action
     */
    @Override
    public Integer delete(Integer primaryKey) throws DAOException {
        if(primaryKey == null){
            throw new DAOException("primary key is null");
        }
        try(PreparedStatement preparedStatement = CON.prepareStatement("DELETE FROM Suggestion WHERE id = ?")){
            preparedStatement.setInt(1, primaryKey);
            return preparedStatement.executeUpdate() == 1 ? 1 : 0;

        } catch (SQLException e) {
            throw new DAOException("Cannot delete suggestion");
        }
    }

    @Override
    public List<Suggestion> getAllListSuggestion(Integer listId) throws DAOException {
        if(listId == null){
            throw new DAOException("list id is null");
        }
        List<Suggestion> suggestions = new ArrayList<>();
        try(PreparedStatement preparedStatement = CON.prepareStatement("SELECT * FROM Suggestion WHERE idList = ?")){
            preparedStatement.setInt(1, listId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                Suggestion suggestion = new Suggestion();
                suggestion.setId(resultSet.getInt("id"));
                suggestion.setIdProd(resultSet.getInt("idProd"));
                suggestion.setIdList(resultSet.getInt("idList"));
                suggestion.setAverage(resultSet.getInt("average"));
                suggestion.setCounter(resultSet.getInt("counter"));
                suggestion.setFirst(resultSet.getTime("first"));
                suggestion.setLast(resultSet.getTime("last"));
                suggestion.setSeen(resultSet.getBoolean("seen"));
                suggestions.add(suggestion);
            }

        } catch (SQLException e) {
            throw new DAOException("cannot retrieve suggestion");
        }
        return suggestions;
    }

    @Override
    public Suggestion getByProdAndListId(Integer prodId, Integer listId) throws DAOException {
        if(prodId == null || listId == null){
            throw new DAOException("primary key is null");
        }
        Suggestion suggestion = null;
        try(PreparedStatement preparedStatement = CON.prepareStatement("SELECT * FROM Suggestion WHERE idProd = ? AND idList = ?")){
            preparedStatement.setInt(1, prodId);
            preparedStatement.setInt(2, listId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                suggestion = new Suggestion();
                suggestion.setId(resultSet.getInt("id"));
                suggestion.setIdProd(resultSet.getInt("idProd"));
                suggestion.setIdList(resultSet.getInt("idList"));
                suggestion.setAverage(resultSet.getInt("average"));
                suggestion.setCounter(resultSet.getInt("counter"));
                suggestion.setFirst(resultSet.getTime("first"));
                suggestion.setLast(resultSet.getTime("last"));
                suggestion.setSeen(resultSet.getBoolean("seen"));
            }

        } catch (SQLException e) {
            throw new DAOException("cannot retrieve suggestion");
        }
        return suggestion;
    }

    @Override
    public Suggestion update(Suggestion suggestion) throws DAOException {
        if (suggestion == null) {
            throw new DAOException("Suggestion null, not valid.", new IllegalArgumentException("Suggestion null."));
        }

        try (PreparedStatement prepStm = CON.prepareStatement("UPDATE `Suggestion` SET `idProd` = ?, `idList` = ?, `counter` = ?, `average` = ?, `first` = ?, `last` = ?, `seen` = ? WHERE `id` = ?")) {
            prepStm.setInt(1, suggestion.getIdProd());
            prepStm.setInt(2, suggestion.getIdList());
            prepStm.setInt(3, suggestion.getCounter());
            prepStm.setInt(4, suggestion.getAverage());
            prepStm.setTime(5, suggestion.getFirst());
            prepStm.setTime(6, suggestion.getLast());
            prepStm.setBoolean(7, suggestion.isSeen());
            if (prepStm.executeUpdate() == 1) {
                return suggestion;
            } else {
                throw new DAOException("Error while saving the suggestion");
            }
        } catch (SQLException ex) {
            throw new DAOException("Error while updating the suggestion!", ex);
        }
    }


}
