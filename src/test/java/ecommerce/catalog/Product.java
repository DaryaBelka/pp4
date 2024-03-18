package ecommerce.catalog;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private final String description;
    private final String id;
    private final String name;
    private BigDecimal price;


    public Product(UUID id, String name, String description) {
        this.description = description;
        this.id = id.toString();
        this.name = name;

    }

    public String getId() {
        return id;
    }

    public String getPrice() {
        return price.toString();
    }

    public void changePrice(BigDecimal newPrice) {
        this.price = newPrice;


    }
}
