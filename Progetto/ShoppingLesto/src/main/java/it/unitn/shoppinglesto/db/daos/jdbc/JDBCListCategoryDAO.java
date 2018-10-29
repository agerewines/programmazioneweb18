package it.unitn.shoppinglesto.db.daos.jdbc;

import it.unitn.shoppinglesto.db.entities.Category;
import it.unitn.shoppinglesto.db.daos.ListCategoryDAO;
import it.unitn.shoppinglesto.db.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCListCategoryDAO extends JDBCDAO<Category, Integer> implements ListCategoryDAO {

    public JDBCListCategoryDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM ListCategory");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count list category", ex);
        }

        return 0L;
    }

    @Override
    public Category getByPrimaryKey(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM ListCategory WHERE category_id = ?")) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {
                rs.next();
                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setPhoto(rs.getString("photo"));

                return category;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list category for the passed primary key", ex);
        }
    }

    @Override
    public List<Category> getAll() throws DAOException {
        List<Category> categories = new ArrayList<>();

        try (Statement stm = CON.createStatement()) {
            try (ResultSet rs = stm.executeQuery("SELECT * FROM ListCategory ORDER BY name")) {
                while (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt("category_id"));
                    category.setName(rs.getString("name"));
                    category.setDescription(rs.getString("description"));
                    category.setPhoto(rs.getString("photo"));
                    categories.add(category);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get all the list category", ex);
        }

        return categories;
    }

    @Override
    public Integer save(Category cat) throws DAOException {
        if(cat == null){
            throw new DAOException("parameter not valid", new IllegalArgumentException("The shopping list is null."));
        }
        String insert = "INSERT INTO `ListCategory`(`name`, `description`, `photo`)"
                + "VALUES (?,?,?)";
        try(PreparedStatement preparedStatement  = CON.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, cat.getName());
            preparedStatement.setString(2, cat.getDescription());
            preparedStatement.setString(3, cat.getPhoto());
            preparedStatement.executeUpdate();
            try(ResultSet rs  = preparedStatement.getGeneratedKeys()){
                if(rs.next()){
                    cat.setId(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DAOException("Impossible to insert list category.", ex);
        }
        return cat.getId();
    }

    @Override
    public Integer delete(Integer primaryKey) throws DAOException {
        return null;
    }
}
