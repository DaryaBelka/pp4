package pl.belka.ecommerce.sales;

import org.springframework.web.bind.annotation.*;
import pl.belka.ecommerce.sales.offering.Offer;
import pl.belka.ecommerce.sales.offering.AcceptOfferRequest;
import pl.belka.ecommerce.sales.reservation.ReservationDetails;

@RestController
public class SalesController {
    SalesFacade sales;

    public SalesController(SalesFacade sales){
        this.sales = sales;
    }

    @GetMapping("/api/current-offer")
    Offer getCurrentOffer(){
        String customerId = getCurrentCustomerId();
        return sales.getCurrentOffer(customerId);
    }

    @PostMapping("/api/accept-offer")
    ReservationDetails acceptOffer(@RequestBody AcceptOfferRequest acceptOfferRequest){
        String customerId = getCurrentCustomerId();
        ReservationDetails details = sales.acceptOffer(customerId, acceptOfferRequest);
        return details;
    }

    @PostMapping("/api/add-to-cart/{productId}")
    void addToCart(@PathVariable(name="productId") String productId){
        String customerId = getCurrentCustomerId();
        sales.addToCart(customerId, productId);
    }

    private String getCurrentCustomerId(){
        return "Darya";
    }
}