package ecommerce.catalog;

import org.junit.jupiter.api.Test;
import pl.belka.ecommerce.catalog.ProductStorage;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
public class HasMapProductStorageTest {

    public static final String TEST_PRODUCT_NAME = "test product";
    @Test
    void isStoreNewProduct() {
        ProductStorage storage = thereIsProductStorage() ;
        Product product = thereIsExampleProduct();

        storage.add(product);

        List<Product> products = storage.allProducts();
        assertThat(products)
                .hasSize(1)
                .extracting(Product::getName)
                .contains(TEST_PRODUCT_NAME)

    }

    private Product thereIsExampleProduct() {
        return new Product(UUID.randomUUID(),"test product", "my des");
    }

    private ProductStorage thereIsProductStorage() {
        return new HasMapProductStorage();
    }
    @Test
    void itLoadsAllProducts(){
        HashMap<String, Product> products;


    }
}
