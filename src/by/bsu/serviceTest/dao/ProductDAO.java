package by.bsu.serviceTest.dao;


import by.bsu.serviceTest.entity.Product;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ProductDAO implements DAO<Product>{

    private static DataSource dataSource;
    private static final String NAME = "jdbc/study";
    private static final String SELECT_ALL = "select * from products";
    private static final String SELECT_BY_ID = "select * from products WHERE id=?";
    private static final String INSERT_PRODUCT = "insert into products (name, price) values (?, ?)";
    private static final String UPDATE_PRODUCT_BY_ID = "UPDATE products SET name = ?, price = ? WHERE id = ?";
    private static final String DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE id = ?";
    private static final String DELETE_PRODUCTS = "DELETE FROM products";

    static {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" + NAME);
        } catch (NamingException e) {
            throw new IllegalStateException(NAME + " is missing in JNDI!", e);
        }
    }

    private static Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    }

    public ArrayList<Product> selectAll() {
        ArrayList<Product> products = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int price = resultSet.getInt(3);
                products.add(new Product(id, name, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public boolean exist(long id) {
        return selectOne(id) != null;
    }

    public void deleteAll() {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCTS);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product selectOne(long id) {
        Product product = null;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int productId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int price = resultSet.getInt(3);
                product = new Product(productId, name, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public void insert(Product product) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Product product, long id) {
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_BY_ID);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(long id) {
        try(Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_BY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

}

