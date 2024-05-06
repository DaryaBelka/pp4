package pl.belka.ecommerce.sales;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalesController {
    SalesFacade sales;

    public SalesController(SalesFacade sales) {
        this.sales = sales;
    }

    @GetMapping("/api/current-offer")
    Offer getCurrentOffer() {
        String customerId = getCurrentCustomerId();
        return sales.getCurrentOffer(customerId);
    }

    private String getCurrentCustomerId() {
        return "Darya";
    }
}
