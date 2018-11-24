package it.unitn.shoppinglesto.db.daos.jdbc;

import it.unitn.shoppinglesto.db.daos.MessageDAO;
import it.unitn.shoppinglesto.db.entities.Message;
import it.unitn.shoppinglesto.db.entities.ShoppingList;
import it.unitn.shoppinglesto.db.entities.User;
import it.unitn.shoppinglesto.db.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCMessageDAO extends JDBCDAO<Message, Integer> implements MessageDAO {

    public JDBCMessageDAO(Connection con) {
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
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM Message");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count messages", ex);
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
    public Message getByPrimaryKey(Integer primaryKey) throws DAOException {
        if(primaryKey == null){
            throw new DAOException("primary key is null");
        }
        Message message = null;
        try(PreparedStatement preparedStatement = CON.prepareStatement("SELECT * FROM Message WHERE id = ?")){
            preparedStatement.setInt(1, primaryKey);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                message = new Message();
                message.setId(resultSet.getInt("id"));
                message.setListId(resultSet.getInt("listId"));
                message.setUserId(resultSet.getInt("userId"));
                message.setCreatedAt(resultSet.getTimestamp("createdAt").toString());
                message.setText(resultSet.getString("text"));
                message.setUser(getUser(message.getUserId()));
                message.setList(getList(message.getListId()));
            }

        } catch (SQLException e) {
            throw new DAOException("cannot retrieve message");
        }
        return message;
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
    public List<Message> getAll() throws DAOException {
        List<Message> messages = new ArrayList<>();
        try (Statement stm = CON.createStatement()) {
            try (ResultSet resultSet = stm.executeQuery("SELECT * FROM Message ORDER BY createdAT")) {
                while (resultSet.next()) {
                    Message message = new Message();
                    message.setId(resultSet.getInt("id"));
                    message.setListId(resultSet.getInt("listId"));
                    message.setUserId(resultSet.getInt("userId"));
                    message.setCreatedAt(resultSet.getTimestamp("createdAt").toString());
                    message.setText(resultSet.getString("text"));
                    message.setUser(getUser(message.getUserId()));
                    message.setList(getList(message.getListId()));
                    messages.add(message);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of messages", ex);
        }

        return messages;
    }

    /**
     * Saves the entity instance passed as parameter to the storage system
     *
     * @param entity the instance of type @{link ENTITY_CLASS}
     * @return the id of the entity stored in the database
     * @throws DAOException if an error occurred during the action
     */
    @Override
    public Integer save(Message entity) throws DAOException {
        if (entity == null) {
            throw new DAOException("parameter not valid", new IllegalArgumentException("The message is null."));
        }
        String insert = "INSERT INTO `Message`(`userId`, `listId`, `text`)"
                + "VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = CON.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, entity.getUserId());
            preparedStatement.setInt(2, entity.getListId());
            preparedStatement.setString(3, entity.getText());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DAOException("Impossible to insert message.", ex);
        }
        return entity.getId();
    }

    /**
     * Deletes the entity instance passed as parameter from the storage system.
     *
     * @param primaryKey the id of {@link Message} to delete.
     * @return the result of the delete operation
     * @throws DAOException if an error occurred during the action
     */
    @Override
    public Integer delete(Integer primaryKey) throws DAOException {
        if(primaryKey == null){
            throw new DAOException("primary key is null");
        }
        try(PreparedStatement preparedStatement = CON.prepareStatement("DELETE FROM Message WHERE id = ?")){
            preparedStatement.setInt(1, primaryKey);
            return preparedStatement.executeUpdate() == 1 ? 1 : 0;

        } catch (SQLException e) {
            throw new DAOException("Cannot delete message");
        }
    }

    public User getUser(Integer id) throws DAOException {
        if (id == null) {
            throw new DAOException("Id is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM User WHERE id = ?")) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setMail(rs.getString("mail"));
                    user.setPassword(rs.getString("password"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setUuid(rs.getString("uuid"));
                    user.setActive(rs.getBoolean("active"));
                    user.setAdmin(rs.getBoolean("admin"));
                    user.setAnonymous(Boolean.getBoolean(rs.getString("anonymous")));
                    return user;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the users by id", ex);
        }
    }

    public ShoppingList getList(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM List WHERE list_id = ?")) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {
                rs.next();
                ShoppingList shoppingList = new ShoppingList();
                shoppingList.setId(rs.getInt("list_id"));
                shoppingList.setName(rs.getString("name"));
                shoppingList.setDescription(rs.getString("description"));
                shoppingList.setCategoryId(rs.getInt("category_id"));
                shoppingList.setUserId(rs.getInt("user_id"));
                shoppingList.setImage(rs.getString("image"));
                return shoppingList;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the shopping_list for the passed primary key", ex);
        }
    }

    @Override
    public List<Message> getAllListMessages(Integer listId) throws DAOException {
        List<Message> messages = new ArrayList<>();
        try (PreparedStatement preparedStatement = CON.prepareStatement("SELECT * FROM Message WHERE listId = ? ORDER BY createdAT")) {
            preparedStatement.setInt(1, listId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Message message = new Message();
                    message.setId(resultSet.getInt("id"));
                    message.setListId(resultSet.getInt("listId"));
                    message.setUserId(resultSet.getInt("userId"));
                    message.setCreatedAt(resultSet.getTimestamp("createdAt").toString());
                    message.setText(resultSet.getString("text"));
                    message.setUser(getUser(message.getUserId()));
                    message.setList(getList(message.getListId()));
                    messages.add(message);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of messages", ex);
        }

        return messages;
    }
}
