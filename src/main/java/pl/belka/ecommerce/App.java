package pl.belka.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import pl.belka.ecommerce.catalog.HashMapProductStorage;
import pl.belka.ecommerce.catalog.ProductCatalog;
import pl.belka.ecommerce.infrastructure.PayUPaymentGateway;
import pl.belka.ecommerce.payu.PayUCredentials;
import pl.belka.ecommerce.sales.offering.OfferCalculator;
import pl.belka.ecommerce.sales.SalesFacade;
import pl.belka.ecommerce.sales.cart.InMemoryCartStorage;
import pl.belka.ecommerce.sales.payment.PaymentGateway;
import pl.belka.ecommerce.sales.reservation.ReservationRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class App {
    public static void main(String [] args){
        System.out.println("Here we go!");
        SpringApplication.run(App.class,args);
    }
    @Bean
    ProductCatalog createMyProductCatalog(){
        var catalog = new ProductCatalog(new HashMapProductStorage());
        var pid1 = catalog.addProduct("Lego set 8083", "Nice done");
        catalog.changePrice(pid1, BigDecimal.valueOf(100.10));

        var pid2 = catalog.addProduct("Cobi blocks", "Nice one");
        catalog.changePrice(pid2, BigDecimal.valueOf(50.10));

        return catalog;
    }


    @Bean
    PaymentGateway createPaymentGw(){
        return new PayUPaymentGateway(
                new RestTemplate(),
                PayUCredentials.sandbox(
                        "300746",
                        "2ee86a66e5d97e3fadc400c9f19b065d"
                )
        );
    }

    @Bean
    SalesFacade createSales(ProductCatalog catalog){
        return new SalesFacade(
                new InMemoryCartStorage(),
                new OfferCalculator(catalog),
                createPaymentGw(),
                new ReservationRepository()
        );
    }
}