package it.unitn.shoppinglesto.db.daos.jdbc;

import it.unitn.shoppinglesto.db.daos.ProductDAO;
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
        String insert = "INSERT INTO `Product`(`name`, `description`, `category_id`, `custom`)"
                + "VALUES (?,?,?,?)";
        try(PreparedStatement preparedStatement  = CON.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, prod.getName());
            preparedStatement.setString(2, prod.getDescription());
            preparedStatement.setInt(3, prod.getCategoryId());
            preparedStatement.setBoolean(4, prod.isCustom());
            preparedStatement.executeUpdate();
            try(ResultSet rs  = preparedStatement.getGeneratedKeys()){
                if(rs.next()){
                    prod.setId(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DAOException("Impossible to insert products.", ex);
        }
        return prod.getId();
    }

    @Override
    public Integer delete(Integer primaryKey) throws DAOException {
        return null;
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
     * @param shoppingList list that i wanna view
     * @return list of products insterted to this list
     * @throws DAOException if an error occurred during the operation.
     */
    @Override
    public List<Product> getAvailableProduct(ShoppingList shoppingList) throws DAOException {
        if(shoppingList == null){
            throw new DAOException("shoppingList is null");
        }
        List<Product> products = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM Product WHERE prod_id NOT IN (SELECT prodId FROM ListProduct WHERE listId = ?) AND custom = FALSE ORDER BY name")) {
            stm.setInt(1, shoppingList.getId());
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("prod_id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setPhotos(getPhotos(product.getId()));
                    product.setCustom(rs.getBoolean("custom"));
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
        String path = null;
        try(PreparedStatement preparedStatement = CON.prepareStatement("SELECT path FROM PhotoProduct WHERE id = ?")){
            preparedStatement.setInt(1, photoId);
            try(ResultSet rs  = preparedStatement.executeQuery()){
                if(rs.next()){
                    return rs.getString("path");
                }
            }
        }catch (SQLException e){
            throw new DAOException("Impossible to get single photo of the product.", e);
        }
        return path;
    }

    private List<Photo> getPhotos(Integer prodId) throws DAOException {
        if(prodId == null){
            throw new DAOException("Prod id is null");
        }
        List<Photo> photos = new ArrayList<>();
        try (PreparedStatement preparedStatement  = CON.prepareStatement("SELECT * FROM PhotoProduct WHERE prod_id = ?")) {
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
        String insert = "INSERT INTO `PhotoProduct`(`path`, `prod_id`) VALUES (?,?)";
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
}
