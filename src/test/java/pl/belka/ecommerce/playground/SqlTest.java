package pl.belka.ecommerce.playground;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.belka.ecommerce.catalog.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class SqlTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setupDb(){
        jdbcTemplate.execute("DROP TABLE `product_catalog__products` IF EXISTS;");

        var createTableSql = """
             CREATE TABLE `product_catalog__products` (
                  `id` VARCHAR(255) NOT NULL,
                  `name` VARCHAR(100) NOT NULL,
                  `price` DECIMAL(12,2),
                  PRIMARY KEY(id)
             );
        """;
        jdbcTemplate.execute(createTableSql);
    }

    @Test
    void itSelectForCurrentDate(){
        var myDate = jdbcTemplate.queryForObject(
                "select now() myCurrentDate",
                String.class
        );

        assertThat(myDate).contains("2024");
    }

    @Test
    void itCreatesTable(){
        var countSql = "select count(*) from `product_catalog__products`;";
        var  results = jdbcTemplate.queryForObject(countSql, Integer.class);

        assertThat(results).isEqualTo(0);
    }

    @Test
    void itStoreProducts() {
        var myInsertSql = """
                INSERT INTO `product_catalog__products` (id, name, price)
                VALUES
                    ('product_id_1', 'My lego set', 20.20),
                    ('product_id_2', 'My cobi set', 10.20)
                ;
             """;
        jdbcTemplate.execute(myInsertSql);

        var countSql = "select count(*) from `product_catalog__products`";
        var results = jdbcTemplate.queryForObject(countSql, Integer.class);

        assertThat(results).isEqualTo(2);
    }

    @Test
    void itStoreDynamicProduct() {
        var product = new Product(UUID.randomUUID(), "My Lego Set", "Nice One");
        product.changePrice(BigDecimal.valueOf(10.10));

        var myInsertSql = """
                INSERT INTO `product_catalog__products` (id, name, price)
                VALUES
                    (?, ?, ?)
                ;
             """;
        jdbcTemplate.update(myInsertSql,product.getId(), product.getName(), product.getPrice());

        var countSql = "select count(*) from `product_catalog__products`";
        var results = jdbcTemplate.queryForObject(countSql, Integer.class);

        assertThat(results).isEqualTo(1);
    }

    @Test
    void loadProductById(){
        var product = new Product(UUID.randomUUID(), "My Lego Set", "Nice One");
        product.changePrice(BigDecimal.valueOf(10.10));

        var myInsertSql = """
                INSERT INTO `product_catalog__products` (id, name, price)
                VALUES
                    (?, ?, ?)
                ;
             """;
        jdbcTemplate.update(myInsertSql,product.getId(), product.getName(), product.getPrice());

        var productId = product.getId();
        var selectProductSql = "select * from `product_catalog__products` where id = ?";
        Product loadedProduct = jdbcTemplate.queryForObject(
                selectProductSql,
                new Object[]{productId},
                (rs, i) -> {
                    var myProduct = new Product(
                            UUID.fromString(rs.getString("id")),
                            rs.getString("name"),
                            rs.getString("name")
                    );
                    myProduct.changePrice(BigDecimal.valueOf(rs.getDouble("price")));
                    return myProduct;
                }
        );

        assertThat(loadedProduct.getId()).isEqualTo(productId);
        assertThat(loadedProduct.getName()).isEqualTo("My Lego Set");
    }

    @Test
    void itLoadsAllProductsAtOnce(){
        var myInsertSql = """
                INSERT INTO `product_catalog__products` (id, name, price)
                VALUES
                    ('product_id_1', 'My lego set', 20.20),
                    ('product_id_2', 'My cobi set', 10.20)
                ;
             """;
        jdbcTemplate.execute(myInsertSql);

        List<Map<String, Object>> products = jdbcTemplate.queryForList("select * from `product_catalog__products`");
    }
}