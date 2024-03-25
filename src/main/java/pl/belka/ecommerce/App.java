package pl.belka.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.belka.ecommerce.catalog.ProductCatalog;
import pl.belka.ecommerce.catalog.ProductCatalogController;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    ProductCatalog createMyProductCatalog() {
        var catalog = new ProductCatalog();
        catalog.addProduct("Legoset 8083", "Nice");
        catalog.addProduct("Cobi blocks", "Nice one");

        return catalog;
    }
 }
