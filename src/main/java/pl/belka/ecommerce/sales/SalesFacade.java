package pl.belka.ecommerce.sales;

import org.apache.el.stream.Optional;

public class SalesFacade {

    private InMemoryCartStorage cartStorage;


    public Offer getCurrentOffer(String customerId) {
        return new Offer();
    }

    public ReservationDetails acceptOffer(String customerId) {
        return new ReservationDetails();
    }

    public void addToCart(String customerId, String productId) {
        Cart cart = loadCartForCustomer(customerId);

        cart.addProduct(productId);
    }

    private Cart loadCartForCustomer(String customerId) {
        return cartStorage.findByCustomerId(customerId);
            .orElse(Cart.empty());
    }




}

