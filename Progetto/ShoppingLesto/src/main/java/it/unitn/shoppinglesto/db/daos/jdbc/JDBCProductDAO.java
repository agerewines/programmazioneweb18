package it.unitn.shoppinglesto.db.daos.jdbc;

import it.unitn.shoppinglesto.db.daos.ProductDAO;
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
            throw new DAOException("Impossible to count lists", ex);
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
                product.setProdId(rs.getInt("prod_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setLogo(rs.getString("logo"));
                product.setCustom(rs.getBoolean("custom"));
                return product;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the shopping_list for the passed primary key", ex);
        }
    }

    @Override
    public List<Product> getAll() throws DAOException {
        List<Product> products = new ArrayList<>();

        try (Statement stm = CON.createStatement()) {
            try (ResultSet rs = stm.executeQuery("SELECT * FROM List ORDER BY name")) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProdId(rs.getInt("prod_id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setLogo(rs.getString("logo"));
                    product.setCustom(rs.getBoolean("custom"));
                    products.add(product);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of shopping_lists", ex);
        }

        return products;
    }

    @Override
    public Integer save(Product prod) throws DAOException {
        if(prod == null){
            throw new DAOException("parameter not valid", new IllegalArgumentException("The shopping list is null."));
        }
        String insert = "INSERT INTO `Product`(`name`, `description`, `logo`, `category_id`, `custom`)"
                + "VALUES (?,?,?,?,?)";
        try(PreparedStatement preparedStatement  = CON.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, prod.getName());
            preparedStatement.setString(2, prod.getDescription());
            preparedStatement.setString(3, prod.getLogo());
            preparedStatement.setInt(4, prod.getCategoryId());
            preparedStatement.setBoolean(5, prod.isCustom());
            preparedStatement.executeUpdate();
            try(ResultSet rs  = preparedStatement.getGeneratedKeys()){
                if(rs.next()){
                    prod.setProdId(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DAOException("Impossible to insert shopping list.", ex);
        }
        return prod.getProdId();
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
                    product.setProdId(rs.getInt("prod_id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setLogo(rs.getString("logo"));
                    product.setCustom(rs.getBoolean("custom"));
                    products.add(product);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of shopping_lists", ex);
        }

        return products;
    }
}
