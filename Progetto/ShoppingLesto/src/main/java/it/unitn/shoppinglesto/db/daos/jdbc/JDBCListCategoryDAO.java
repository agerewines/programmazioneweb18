package it.unitn.shoppinglesto.db.daos.jdbc;

import it.unitn.shoppinglesto.db.daos.DAO;
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
            throw new DAOException("parameter not valid", new IllegalArgumentException("The is null."));
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
            if(cat.getPhotos() != null && !cat.getPhotos().isEmpty())
                setPhotos(cat);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DAOException("Impossible to insert list category.", ex);
        }
        return cat.getId();
    }

    @Override
    public Integer delete(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primary key is null");
        }
        // delete all list with this category
        // delete all photos
        // delete category
        try(PreparedStatement stm = CON.prepareStatement("DELETE FROM ListCategory WHERE category_id = ?")) {
            stm.setInt(1, primaryKey);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting list category");
        }
        return 1;
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
                p.setId(rs.getInt("listCategoryPhotoId"));
                p.setPath(rs.getString("path"));
                p.setItemId(rs.getInt("listCategoryId"));
                photos.add(p);
            }
        }catch (SQLException e){
            throw new DAOException("Impossible to get list category photos.", e);
        }
        return photos;
    }

    private void setPhotos(Category category) throws DAOException {
        if(category.getPhotos() == null){
            throw new DAOException("photos empty");
        }
        List<Photo> photos = category.getPhotos();
        String insert = "INSERT INTO `ListCategoryPhoto`(`path`, `listCategoryId`) VALUES (?,?)";
        try(PreparedStatement preparedStatement  = CON.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            for (Photo photo: photos) {
                preparedStatement.setString(1, photo.getPath());
                preparedStatement.setInt(2, photo.getItemId());
                try(ResultSet rs  = preparedStatement.getGeneratedKeys()){
                    if(rs.next()){
                        photo.setId(rs.getInt(1));
                    }
                }
                preparedStatement.executeUpdate();
            }
        }catch (SQLException e){
            throw new DAOException("Impossible to set photos.", e);
        }
    }

    @Override
    public void addPhoto(Photo photo) throws DAOException{
        if(photo == null){
            throw new DAOException("photo is null");
        }
        String insert = "INSERT INTO `ListCategoryPhoto`(`path`, `listCategoryId`) VALUES (?,?)";
        try(PreparedStatement preparedStatement  = CON.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, photo.getPath());
            preparedStatement.setInt(2, photo.getItemId());
            try(ResultSet rs  = preparedStatement.getGeneratedKeys()){
                if(rs.next()){
                    photo.setId(rs.getInt(1));
                }
            }
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Impossible to add photo", e);
        }

    }

    /**
     * Get path of a certain {@link Photo photo}
     *
     * @param photoId id of the {@link Photo photo} that i want
     * @return path of the {@link Photo photo} that i need
     * @throws DAOException
     */
    @Override
    public String getPhotoPath(Integer photoId) throws DAOException {
        if(photoId == null){
            throw new DAOException("photoId is null");
        }
        try(PreparedStatement preparedStatement = CON.prepareStatement("SELECT path FROM ListCategoryPhoto WHERE listCategoryPhotoId = ?")){
            preparedStatement.setInt(1, photoId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString("path");
            }else
                return null;
        }catch (SQLException e){
            throw new DAOException("Error retrieving path of photo");
        }
    }

    /**
     * Update the @{link Category category} passed as parameter and returns it.
     *
     * @param cat the @{link Category category} used to update the persistence system.
     * @return the updated category.
     * @throws DAOException if an error occurred during the action.
     */
    @Override
    public Category update(Category cat) throws DAOException {
        if(cat == null){
            throw new DAOException("parameter not valid", new IllegalArgumentException("The category is null."));
        }
        try (PreparedStatement prepStm = CON.prepareStatement("UPDATE `ListCategory` SET name = ?, description = ? WHERE `category_id` = ?")) {
            prepStm.setString(1, cat.getName());
            prepStm.setString(2, cat.getDescription());
            prepStm.setInt(3, cat.getId());
            prepStm.executeUpdate();
            if(cat.getPhotos() != null && !cat.getPhotos().isEmpty()){
                removeAllPhotos(cat);
                setPhotos(cat);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DAOException("Impossible to update list category.", ex);
        }
        return cat;
    }

    /**
     * Update the @{link Category category} passed as parameter and returns it.
     *
     * @param cat the @{link Category category} used to update the persistence system.
     * @return the updated category.
     * @throws DAOException if an error occurred during the action.
     */
    @Override
    public Category simpleUpdate(Category cat) throws DAOException {
        if(cat == null){
            throw new DAOException("parameter not valid", new IllegalArgumentException("The category is null."));
        }
        try (PreparedStatement prepStm = CON.prepareStatement("UPDATE `ListCategory` SET name = ?, description = ? WHERE `category_id` = ?")) {
            prepStm.setString(1, cat.getName());
            prepStm.setString(2, cat.getDescription());
            prepStm.setInt(3, cat.getId());
            prepStm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DAOException("Impossible to simple update list category.", ex);
        }
        return cat;
    }

    @Override
    public boolean deletePhoto(Integer photoId) throws DAOException {
        if(photoId == null){
            throw new DAOException("photoid is null");
        }
        try(PreparedStatement preparedStatement = CON.prepareStatement("DELETE FROM ListCategoryPhoto WHERE listCategoryPhotoId = ?")){
            preparedStatement.setInt(1, photoId);
            if(preparedStatement.executeUpdate() == 1)
                return true;
        }catch (SQLException e){
            throw new DAOException("Impossible to delete photo", e);
        }
        return false;
    }

    private void removeAllPhotos(Category category) throws DAOException{
        if(category == null){
            throw new DAOException("parameter not valid", new IllegalArgumentException("The category is null."));
        }
        try (PreparedStatement prepStm = CON.prepareStatement("DELETE FROM ListCategoryPhoto WHERE listCategoryId = ?")) {
            prepStm.setInt(1, category.getId());
            prepStm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting listcategoryphoto");
        }
    }
}
