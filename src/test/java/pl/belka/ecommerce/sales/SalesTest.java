package pl.belka.ecommerce.sales;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.belka.ecommerce.catalog.ProductCatalog;
import pl.belka.ecommerce.sales.cart.InMemoryCartStorage;
import pl.belka.ecommerce.sales.offering.Offer;
import pl.belka.ecommerce.sales.offering.OfferCalculator;
import pl.belka.ecommerce.sales.reservation.ReservationRepository;
import pl.belka.ecommerce.sales.reservation.SpyPaymentGateway;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class SalesTest {
    @Autowired
    ProductCatalog catalog;

    @Test
    void itShowsOffer(){
        SalesFacade sales = thereIsSalesFacade();
        String customerId = thereIsExampleCustomer("Darya");

        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(0, offer.getItemsCount());
        assertEquals(BigDecimal.ZERO, offer.getTotal());
    }

    private SalesFacade thereIsSalesFacade() {
        return new SalesFacade(
                new InMemoryCartStorage(),
                new OfferCalculator(catalog),
                new SpyPaymentGateway(),
                new ReservationRepository()
        );
    }

    private String thereIsExampleCustomer(String id){
        return id;
    }

    @Test
    void itAllowsToAddProductToCart(){
        String productId = thereIsProduct("Example", BigDecimal.valueOf(10));
        String customerId = thereIsExampleCustomer("Darya");
        SalesFacade sales = thereIsSalesFacade();

        sales.addToCart(customerId, productId);
        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(1, offer.getItemsCount());
        assertEquals(BigDecimal.valueOf(10), offer.getTotal());
    }

    @Test
    void itAllowsToAddMultipleProductsToCart(){
        String productA = thereIsProduct("Example", BigDecimal.valueOf(10));
        String productB = thereIsProduct("Example", BigDecimal.valueOf(20));
        String customerId = thereIsExampleCustomer("Darya");
        SalesFacade sales = thereIsSalesFacade();

        sales.addToCart(customerId, productA);
        sales.addToCart(customerId, productB);
        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(2, offer.getItemsCount());
        assertEquals(BigDecimal.valueOf(10), offer.getTotal());
    }

    @Test
    void itDistinguishCartByCustomer(){
        String productA = thereIsProduct("Example a", BigDecimal.valueOf(10));
        String productB = thereIsProduct("Example b", BigDecimal.valueOf(20));
        String customerA = thereIsExampleCustomer("Darya");
        String customerB = thereIsExampleCustomer("Pawel");
        SalesFacade sales = thereIsSalesFacade();

        sales.addToCart(customerA, productA);
        sales.addToCart(customerB, productB);
        Offer offerA = sales.getCurrentOffer(customerA);
        Offer offerB = sales.getCurrentOffer(customerB);

        assertEquals(1, offerA.getItemsCount());
        assertEquals(BigDecimal.valueOf(10), offerA.getTotal());

        assertEquals(1, offerB.getItemsCount());
        assertEquals(BigDecimal.valueOf(20), offerA.getTotal());
    }

    private String thereIsProduct(String name, BigDecimal price){
        return name;
    }
}