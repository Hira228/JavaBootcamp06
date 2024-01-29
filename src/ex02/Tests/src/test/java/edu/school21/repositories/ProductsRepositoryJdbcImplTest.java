package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {
    private DataSource dataSource = null;
    @BeforeEach
    public void init() {
        dataSource =  new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();

    }
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>() {
        {add(new Product(1L, "mama", 99999L));
        add(new Product(2L, "antoinco",999999L));
        add(new Product(3L, "gradyzan", 9123L));
        add(new Product(4L, "kaka", 99123L));
        add(new Product(5L, "lala", 43421L));}
    };

    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(2L, "antoinco",999999L);

    final Product EXPECTED_UPDATED_PRODUCT = new Product(2L, "antoin",9123L);

    final Product EXPECTED_SAVED_PRODUCT = new Product(6L, "aNTONINA",91L);


    @Test
    public void findAllTest() {
        ProductsRepositoriesJdbcImpl productsRepositoriesJdbc = new ProductsRepositoriesJdbcImpl(
                dataSource
        );

        List<Product> list = productsRepositoriesJdbc.findAll();
        Assertions.assertIterableEquals(list, EXPECTED_FIND_ALL_PRODUCTS);
    }

    @Test
    public void findById() {
        ProductsRepositoriesJdbcImpl productsRepositoriesJdbc = new ProductsRepositoriesJdbcImpl(
                dataSource
        );

        Optional<Product> product = productsRepositoriesJdbc.findById(EXPECTED_FIND_BY_ID_PRODUCT.getId());
        Assertions.assertEquals(product.get(), EXPECTED_FIND_BY_ID_PRODUCT);
    }

    @Test
    public void saveTest() {
        ProductsRepositoriesJdbcImpl productsRepositoriesJdbc = new ProductsRepositoriesJdbcImpl(
                dataSource
        );

        productsRepositoriesJdbc.save(EXPECTED_SAVED_PRODUCT);
        Optional<Product> insertProduct = productsRepositoriesJdbc.findById(EXPECTED_SAVED_PRODUCT.getId());
        Assertions.assertEquals(EXPECTED_SAVED_PRODUCT, insertProduct.get());
    }

    @Test
    public void updateTest() {
        ProductsRepositoriesJdbcImpl productsRepositoriesJdbc = new ProductsRepositoriesJdbcImpl(
                dataSource
        );

        productsRepositoriesJdbc.update(EXPECTED_UPDATED_PRODUCT);
        Optional<Product> product = productsRepositoriesJdbc.findById(EXPECTED_UPDATED_PRODUCT.getId());
        Assertions.assertEquals(product.get(), EXPECTED_UPDATED_PRODUCT);
    }

}
