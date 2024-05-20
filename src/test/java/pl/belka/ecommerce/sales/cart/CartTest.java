package pl.belka.ecommerce.sales.cart;

import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.AbstractBooleanAssert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.annotations.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CartTest {

    @Test
    void itIsEmptyWhenCreated() {
        Cart cart = Cart.empty();

        assertThat(cart.isEmpty())
                .isTrue();
    }


    @Test
    void itIsNotEmptyWhenProductWasAdded() {
        Cart cart = Cart.empty();
        String productId = thereIsProduct("X");

        cart.addProduct(productId);

        assertThat(cart.isEmpty())
                .isFalse();
    }



    @Test
    void itExposeUniqueProductCountS1(){
        Cart cart = Cart.empty();
        String productX = thereIsProduct("X");

        cart.addProduct(productX);

        assertThat(cart.getLinesCount())
                .isEqualTo(1);
    }
    @Test
    void itExposeUniqueProductCountS3(){
        Cart cart = Cart.empty();
        String productX = thereIsProduct("X");
        String productY = thereIsProduct("Y");

        cart.addProduct(productX);
        cart.addProduct(productX);
        cart.addProduct(productY);

        assertThat(cart.getLinesCount())
                .isEqualTo(2);}

    private String thereIsProduct(String id) {
        return id;
    }

}
