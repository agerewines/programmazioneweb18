package it.unitn.shoppinglesto.db.daos.jdbc;

import it.unitn.shoppinglesto.db.daos.ProdCategoryDAO;
import it.unitn.shoppinglesto.db.entities.Category;
import it.unitn.shoppinglesto.db.entities.Photo;
import it.unitn.shoppinglesto.db.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCProdCategoryDAO extends JDBCDAO<Category, Integer> implements ProdCategoryDAO {

    public JDBCProdCategoryDAO(Connection con) {
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
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM ProductCategory");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count prod category", ex);
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
    public Category getByPrimaryKey(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM ProductCategory WHERE prodCategoryId = ?")) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {
                rs.next();
                Category category = new Category();
                category.setId(rs.getInt("prodCategoryId"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setPhoto(getPhotos(category.getId()));

                return category;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the product category for the passed primary key", ex);
        }
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
    public List<Category> getAll() throws DAOException {
        List<Category> categories = new ArrayList<>();

        try (Statement stm = CON.createStatement()) {
            try (ResultSet rs = stm.executeQuery("SELECT * FROM ProductCategory ORDER BY name")) {
                while (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt("prodCategoryId"));
                    category.setName(rs.getString("name"));
                    category.setDescription(rs.getString("description"));
                    category.setPhoto(getPhotos(category.getId()));
                    categories.add(category);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get all the product category", ex);
        }

        return categories;
    }

    /**
     * Saves the entity instance passed as parameter to the storage system
     *
     * @param cat the instance of type {@code ENTITY_CLASS}
     * @return the id of the entity stored in the database
     * @throws DAOException if an error occurred during the action
     */
    @Override
    public Integer save(Category cat) throws DAOException {
        if(cat == null){
            throw new DAOException("parameter not valid", new IllegalArgumentException("The product category is null."));
        }
        String insert = "INSERT INTO `ProductCategory`(`name`, `description`) VALUES (?,?)";
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
            throw new DAOException("Impossible to insert product category.", ex);
        }
        return cat.getId();
    }

    /**
     * Deletes the entity instance passed as parameter from the storage system.
     *
     * @param primaryKey the id of {@code ENTITY_CLASS} to delete.
     * @return the result of the delete operation
     * @throws DAOException if an error occurred during the action
     */
    @Override
    public Integer delete(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primary key is null");
        }
        try(PreparedStatement stm = CON.prepareStatement("DELETE FROM ProductCategory WHERE prodCategoryId = ?")) {
            stm.setInt(1, primaryKey);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting product category");
        }
        return 1;
    }

    private List<Photo> getPhotos(Integer categoryId) throws DAOException {
        if(categoryId == null){
            throw new DAOException("category id is null");
        }
        List<Photo> photos = new ArrayList<>();
        try (PreparedStatement preparedStatement  = CON.prepareStatement("SELECT * FROM ProductCategoryPhoto WHERE prodCat = ?")) {
            preparedStatement.setInt(1, categoryId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Photo p = new Photo();
                p.setId(rs.getInt("prodCatPhotoId"));
                p.setPath(rs.getString("path"));
                p.setItemId(rs.getInt("prodCat"));
                photos.add(p);
            }
        }catch (SQLException e){
            throw new DAOException("Impossible to get product category photos.", e);
        }
        return photos;
    }

    private void setPhotos(Category category) throws DAOException {
        if(category.getPhotos() == null){
            throw new DAOException("photos empty is null");
        }
        List<Photo> photos = category.getPhotos();
        String insert = "INSERT INTO `ProductCategoryPhoto`(`path`, `prodCat`) VALUES (?,?)";
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
        String insert = "INSERT INTO `ProductCategoryPhoto`(`path`, `prodCat`) VALUES (?,?)";
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
        try(PreparedStatement preparedStatement = CON.prepareStatement("SELECT path FROM ProductCategoryPhoto WHERE prodCatPhotoId = ?")){
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
        try (PreparedStatement prepStm = CON.prepareStatement("UPDATE `ProductCategory` SET name = ?, description = ? WHERE `prodCategoryId` = ?")) {
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
            throw new DAOException("Impossible to update product category.", ex);
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
        try (PreparedStatement prepStm = CON.prepareStatement("UPDATE `ProductCategory` SET name = ?, description = ? WHERE `prodCategoryId` = ?")) {
            prepStm.setString(1, cat.getName());
            prepStm.setString(2, cat.getDescription());
            prepStm.setInt(3, cat.getId());
            prepStm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DAOException("Impossible to simple update product category.", ex);
        }
        return cat;
    }

    @Override
    public boolean deletePhoto(Integer photoId) throws DAOException {
        if(photoId == null){
            throw new DAOException("photoid is null");
        }
        try(PreparedStatement preparedStatement = CON.prepareStatement("DELETE FROM ProductCategoryPhoto WHERE prodCatPhotoId = ?")){
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
        try (PreparedStatement prepStm = CON.prepareStatement("DELETE FROM ProductCategoryPhoto WHERE prodCat = ?")) {
            prepStm.setInt(1, category.getId());
            prepStm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting prodcategoryphoto");
        }
    }
}
