package pl.belka.ecommerce.sales;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class SalesTest {
    @Test
    void itShowsOffer(){
        SalesFacade sales = thereIsSalesFacade();
        String customerId = thereIsExampleCustomer("Darya");

        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(0, offer.getItemsCount());
        assertEquals(BigDecimal.ZERO, offer.getTotal());
    }

    @Test
    void itAllowsToAddMultipleProductsToCart(){
        String productA = thereIsProduct("Example a", BigDecimal.valueOf(10));
        String productB = thereIsProduct("Example b", BigDecimal.valueOf(20));
        String customerA = thereIsExampleCustomer("Darya");
        String customerB = thereIsExampleCustomer("Michal");
        SalesFacade sales = thereIsSalesFacade();

        sales.addToCart(customerA, productA);
        sales.addToCart(customerB, productB);
        Offer offerA = sales.getCurrentOffer(customerA);
        Offer offerB = sales.getCurrentOffer(customerB);

        assertEquals(1, offerA.getItemsCount());
        assertEquals(BigDecimal.valueOf(10), offerA.getTotal());

        assertEquals(1, offerB.getItemsCount());
        assertEquals(BigDecimal.valueOf(10), offerB.getTotal());
}

    private String thereIsProduct(String exampleA, BigDecimal bigDecimal) {
        return exampleA;
    }

    private SalesFacade thereIsSalesFacade() {
        return new SalesFacade();
    }


    private String thereIsExampleCustomer(String id) {
        return id;
    }



}

