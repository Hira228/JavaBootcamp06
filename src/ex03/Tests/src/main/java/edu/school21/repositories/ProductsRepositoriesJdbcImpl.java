package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;

public class ProductsRepositoriesJdbcImpl implements ProductsRepositories {
    private final DataSource dataSource;

    public ProductsRepositoriesJdbcImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll(){
        List<Product> productList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            connection.setTransactionIsolation(TRANSACTION_READ_COMMITTED);
            PreparedStatement prepareStatement = connection.prepareStatement("select * from mama.Store");
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(new Product(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getLong("price"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    @Override
    public Optional<Product> findById(Long id){
        Product product = null;
        try (Connection connection = dataSource.getConnection()) {
            connection.setTransactionIsolation(TRANSACTION_READ_COMMITTED);
            PreparedStatement prepareStatement = connection.prepareStatement("select * from mama.Store" +
                    " where id = ?");
            prepareStatement.setLong(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                product = new Product(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getLong("price"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(product);
    }

    @Override
    public void update(Product product){
        try (Connection connection = dataSource.getConnection()) {
            connection.setTransactionIsolation(TRANSACTION_READ_COMMITTED);
            PreparedStatement prepareStatement = connection.prepareStatement("update mama.Store"+
                    " set name = ?, price = ?" +
                    " where id = ?");
            prepareStatement.setString(1, product.getName());
            prepareStatement.setLong(2, product.getPrice());
            prepareStatement.setLong(3, product.getId());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Product product){
        try (Connection connection = dataSource.getConnection()) {
            connection.setTransactionIsolation(TRANSACTION_READ_COMMITTED);
            PreparedStatement prepareStatement = connection.prepareStatement("insert into mama.Store (name, price)" +
                    " values(?, ?)");
            prepareStatement.setString(1, product.getName());
            prepareStatement.setLong(2, product.getPrice());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id){
        try (Connection connection = dataSource.getConnection()) {
            connection.setTransactionIsolation(TRANSACTION_READ_COMMITTED);
            PreparedStatement prepareStatement = connection.prepareStatement("delete from mama.Store " +
                    " where id = ?");
            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
