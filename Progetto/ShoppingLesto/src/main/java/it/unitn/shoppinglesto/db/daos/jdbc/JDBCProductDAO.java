package it.unitn.shoppinglesto.db.daos.jdbc;

import it.unitn.shoppinglesto.db.daos.ProductDAO;
import it.unitn.shoppinglesto.db.entities.Category;
import it.unitn.shoppinglesto.db.entities.Photo;
import it.unitn.shoppinglesto.db.entities.Product;
import it.unitn.shoppinglesto.db.entities.ShoppingList;
import it.unitn.shoppinglesto.db.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCProductDAO extends JDBCDAO<Product, Integer> implements ProductDAO {

    public JDBCProductDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        try (Statement stmt = CON.createStatement()) {
            ResultSet counter = stmt.executeQuery("SELECT COUNT(*) FROM Product");
            if (counter.next()) {
                return counter.getLong(1);
            }

        } catch (SQLException ex) {
            throw new DAOException("Impossible to count products", ex);
        }

        return 0L;
    }

    @Override
    public Product getByPrimaryKey(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Product WHERE prod_id = ?")) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {
                rs.next();
                Product product = new Product();
                product.setId(rs.getInt("prod_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPhotos(getPhotos(product.getId()));
                product.setCustom(rs.getBoolean("custom"));
                product.setPrice(rs.getDouble("prezzo"));
                product.setCategory(getCategory(product.getCategoryId()));
                return product;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the product for the passed primary key", ex);
        }
    }

    @Override
    public List<Product> getAll() throws DAOException {
        List<Product> products = new ArrayList<>();

        try (Statement stm = CON.createStatement()) {
            try (ResultSet rs = stm.executeQuery("SELECT * FROM Product ORDER BY name")) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("prod_id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setPhotos(getPhotos(product.getId()));
                    product.setCustom(rs.getBoolean("custom"));
                    product.setCategoryId(rs.getInt("category_id"));
                    product.setPrice(rs.getDouble("prezzo"));
                    product.setCategory(getCategory(product.getCategoryId()));
                    products.add(product);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get all the products", ex);
        }

        return products;
    }

    @Override
    public Integer save(Product prod) throws DAOException {
        if(prod == null){
            throw new DAOException("parameter not valid", new IllegalArgumentException("The shopping list is null."));
        }
        String insert = "INSERT INTO `Product`(`name`, `description`, `category_id`, `custom`, `prezzo`)"
                + "VALUES (?,?,?,?,?)";
        try(PreparedStatement preparedStatement  = CON.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, prod.getName());
            preparedStatement.setString(2, prod.getDescription());
            preparedStatement.setInt(3, prod.getCategoryId());
            preparedStatement.setBoolean(4, prod.isCustom());
            preparedStatement.setDouble(4, prod.getPrice());
            preparedStatement.executeUpdate();
            try(ResultSet rs  = preparedStatement.getGeneratedKeys()){
                if(rs.next()){
                    prod.setId(rs.getInt(1));
                }
            }
            if(prod.getPhotos() != null && !prod.getPhotos().isEmpty())
                setPhotos(prod);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DAOException("Impossible to insert products.", ex);
        }
        return prod.getId();
    }

    @Override
    public Integer delete(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primary key is null");
        }
        try(PreparedStatement stm = CON.prepareStatement("DELETE FROM Product WHERE prod_id = ?")) {
            stm.setInt(1, primaryKey);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting product");
        }
        return 1;
    }

    @Override
    public List<Product> getProductsByList(ShoppingList shoppingList) throws DAOException {
        List<Product> products = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Product WHERE prod_id IN (SELECT prodId FROM ListProduct WHERE listId = ?) ORDER BY name")) {
            stm.setInt(1, shoppingList.getId());
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("prod_id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setPhotos(getPhotos(product.getId()));
                    product.setCustom(rs.getBoolean("custom"));
                    product.setPrice(rs.getDouble("prezzo"));
                    product.setCategory(getCategory(product.getCategoryId()));
                    products.add(product);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error getting products associated to the list", ex);
        }

        return products;
    }

    /**
     * Retrieve all products that can be added into the list
     *
     *
     * @param listId @return list of products insterted to this list
     * @param search
     * @throws DAOException if an error occurred during the operation.
     */
    @Override
    public List<Product> getAvailableProduct(Integer listId, String search) throws DAOException {
        if(listId == null){
            throw new DAOException("shoppingList is null");
        }
        List<Product> products = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Product WHERE name LIKE ? AND prod_id NOT IN (SELECT prodId FROM ListProduct WHERE listId = ?) AND custom = FALSE ORDER BY name LIMIT 20")) {
            stm.setString(1, "%" + search + "%");
            stm.setInt(2, listId);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("prod_id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setPhotos(getPhotos(product.getId()));
                    product.setCustom(rs.getBoolean("custom"));
                    product.setPrice(rs.getDouble("prezzo"));
                    product.setCategoryId(rs.getInt("category_id"));
                    product.setCategory(getCategory(product.getCategoryId()));
                    products.add(product);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error getting products associated to the list", ex);
        }
        return products;
    }

    /**
     * Retrieve all products that can be added into the list
     *
     * @param listId @return list of products able to be added to this list
     * @throws DAOException if an error occurred during the operation.
     */
    @Override
    public List<Product> getAvailableProduct(Integer listId) throws DAOException {
        if(listId == null){
            throw new DAOException("shoppingList is null");
        }
        List<Product> products = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Product WHERE prod_id NOT IN (SELECT prodId FROM ListProduct WHERE listId = ?) AND custom = FALSE ORDER BY name")) {
            stm.setInt(1, listId);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("prod_id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setPhotos(getPhotos(product.getId()));
                    product.setCustom(rs.getBoolean("custom"));
                    product.setPrice(rs.getDouble("prezzo"));
                    product.setCategoryId(rs.getInt("category_id"));
                    product.setCategory(getCategory(product.getCategoryId()));
                    products.add(product);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error getting products associated to the list", ex);
        }
        return products;
    }

    /**
     * Retrieve photo path
     *
     * @param photoId that im looking for
     * @return path of the photo
     * @throws DAOException if an error occurred during the operation.
     */
    @Override
    public String getSinglePhoto(Integer photoId) throws DAOException {
        if(photoId == null){
            throw new DAOException("Photo id is null");
        }
        try(PreparedStatement preparedStatement = CON.prepareStatement("SELECT path FROM ProductPhoto WHERE id = ?")){
            preparedStatement.setInt(1, photoId);
            try(ResultSet rs  = preparedStatement.executeQuery()){
                if(rs.next()){
                    return rs.getString("path");
                }
            }
        }catch (SQLException e){
            throw new DAOException("Impossible to get single photo of the product.", e);
        }
        return null;
    }

    /**
     * Update product from edit servlet
     *
     * @param product@return the updated product
     * @throws DAOException
     */
    @Override
    public Product update(Product product) throws DAOException {
        if(product == null){
            throw new DAOException("Product to update is null");
        }
        try (PreparedStatement preparedStatement = CON.prepareStatement("UPDATE `Product` SET name = ?, description = ?, category_id = ?, custom = ?, prezzo = ? WHERE prod_id = ?")){
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3,product.getCategoryId());
            preparedStatement.setBoolean(4, product.isCustom());
            preparedStatement.setDouble(5, product.getPrice());
            preparedStatement.setInt(6, product.getId());
            if (preparedStatement.executeUpdate() == 1) {
                return product;
            } else {
                throw new DAOException("Error while saving the product");
            }
        } catch (SQLException e) {
            throw new DAOException("Impossible to update product");
        }
    }

    /**
     * Get category of product
     *
     * @param categoryId category id of the product
     * @return complete category
     * @throws DAOException
     */
    @Override
    public Category getCategory(Integer categoryId) throws DAOException {
        if(categoryId == null)
            throw new DAOException("categoryId is null");
        Category category = null;
        try(PreparedStatement preparedStatement = CON.prepareStatement("SELECT * FROM ProductCategory WHERE prodCategoryId = ?")){
            preparedStatement.setInt(1, categoryId);
            try(ResultSet rs = preparedStatement.executeQuery()){
              if(rs.next()){
                  category = new Category();
                  category.setId(rs.getInt("prodCategoryId"));
                  category.setName(rs.getString("name"));
                  category.setDescription(rs.getString("description"));
                  category.setPhoto(getCategoryPhotos(categoryId));
              }
            }
            return category;
        } catch (SQLException e) {
            throw new DAOException("Error getting category of the product");
        }
    }

    @Override
    public boolean deletePhoto(Integer photoId) throws DAOException {
        if(photoId == null){
            throw new DAOException("photoid is null");
        }
        try(PreparedStatement preparedStatement = CON.prepareStatement("DELETE FROM ProductPhoto WHERE id = ?")){
            preparedStatement.setInt(1, photoId);
            if(preparedStatement.executeUpdate() == 1)
                return true;
        }catch (SQLException e){
            throw new DAOException("Impossible to delete photo", e);
        }
        return false;
    }

    private List<Photo> getCategoryPhotos(Integer categoryId) throws DAOException {
        if(categoryId == null)
            throw new DAOException("categoryId is null");
        List<Photo> photos = new ArrayList<>();
        try(PreparedStatement preparedStatement = CON.prepareStatement("SELECT * FROM ProductCategoryPhoto WHERE prodCat = ?")){
            preparedStatement.setInt(1, categoryId);
            try(ResultSet rs = preparedStatement.executeQuery()){
                while(rs.next()){
                    Photo photo = new Photo();
                    photo.setId(rs.getInt("prodCatPhotoId"));
                    photo.setPath(rs.getString("path"));
                    photo.setItemId(rs.getInt("prodCat"));
                    photos.add(photo);
                }
            }
            return photos;
        } catch (SQLException e) {
            throw new DAOException("Error getting photo of the product category");
        }
    }

    private List<Photo> getPhotos(Integer prodId) throws DAOException {
        if(prodId == null){
            throw new DAOException("Prod id is null");
        }
        List<Photo> photos = new ArrayList<>();
        try (PreparedStatement preparedStatement  = CON.prepareStatement("SELECT * FROM ProductPhoto WHERE prod_id = ?")) {
            preparedStatement.setInt(1, prodId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Photo p = new Photo();
                p.setId(rs.getInt("id"));
                p.setPath(rs.getString("path"));
                p.setItemId(rs.getInt("prod_id"));
                photos.add(p);
            }
        }catch (SQLException e){
            throw new DAOException("Impossible to get photos of the product.", e);
        }
        return photos;
    }

    private void setPhotos(Product product) throws DAOException {
        if(product.getPhotos() == null){
            throw new DAOException("photos empty is null");
        }
        List<Photo> photos = product.getPhotos();
        String insert = "INSERT INTO ProductPhoto(`path`, `prod_id`) VALUES (?,?)";
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
        String insert = "INSERT INTO `ProductPhoto`(`path`, `prod_id`) VALUES (?,?)";
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
}
