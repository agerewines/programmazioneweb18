package it.unitn.shoppinglesto.db.daos.jdbc;

import it.unitn.shoppinglesto.db.entities.Category;
import it.unitn.shoppinglesto.db.daos.ListCategoryDAO;
import it.unitn.shoppinglesto.db.entities.Photo;
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
                category.setPhoto(getPhotos(category.getId()));
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
                    category.setPhoto(getPhotos(category.getId()));
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
        String insert = "INSERT INTO `ListCategory`(`name`, `description`)"
                + "VALUES (?,?)";
        try(PreparedStatement preparedStatement  = CON.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, cat.getName());
            preparedStatement.setString(2, cat.getDescription());
            preparedStatement.executeUpdate();
            try(ResultSet rs  = preparedStatement.getGeneratedKeys()){
                if(rs.next()){
                    cat.setId(rs.getInt(1));
                }
            }
            setPhotos(cat);
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

    private List<Photo> getPhotos(Integer categoryId) throws DAOException {
        if(categoryId == null){
            throw new DAOException("category id is null");
        }
        List<Photo> photos = new ArrayList<>();
        try (PreparedStatement preparedStatement  = CON.prepareStatement("SELECT * FROM ListCategoryPhoto WHERE listCategoryId = ?")) {
            preparedStatement.setInt(1, categoryId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Photo p = new Photo();
                p.setId(rs.getInt("id"));
                p.setPath(rs.getString("path"));
                p.setCatId(rs.getInt("listCatId"));
                photos.add(p);
            }
        }catch (SQLException e){
            throw new DAOException("Impossible to get photos.", e);
        }
        return photos;
    }
    private void setPhotos(Category category) throws DAOException {
        if(category.getPhotos() == null){
            throw new DAOException("photos empty is null");
        }
        List<Photo> photos = category.getPhotos();
        String insert = "INSERT INTO `ListCategoryPhoto`(`path`, `listCategoryId`) VALUES (?,?)";
        try(PreparedStatement preparedStatement  = CON.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            for (Photo photo: photos) {
                preparedStatement.setString(1, photo.getPath());
                preparedStatement.setInt(2, photo.getCatId());
                try(ResultSet rs  = preparedStatement.getGeneratedKeys()){
                    if(rs.next()){
                        photo.setId(rs.getInt(1));
                    }
                }
                preparedStatement.executeUpdate();
            }
        }catch (SQLException e){
            throw new DAOException("Impossible to get photos.", e);
        }
    }
}
