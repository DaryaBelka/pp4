package pl.belka.ecommerce.sales.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.belka.ecommerce.catalog.ProductCatalog;
import pl.belka.ecommerce.sales.SalesFacade;
import pl.belka.ecommerce.sales.cart.InMemoryCartStorage;
import pl.belka.ecommerce.sales.offering.AcceptOfferRequest;
import pl.belka.ecommerce.sales.offering.OfferCalculator;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

public class OfferAcceptanceTest {
    @Autowired
    ProductCatalog catalog;

    private SpyPaymentGateway spyPaymentGateway;
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp(){
        spyPaymentGateway = new SpyPaymentGateway();
        reservationRepository = new ReservationRepository();
    }

    @Test
    void itAllowsToAcceptAnOffer(){
        SalesFacade sales = thereIsSales();
        String customerId = thereIsCustomer("Darya");
        String productId = thereIsProduct("X", BigDecimal.valueOf(10));

        sales.addToCart(customerId, productId);
        sales.addToCart(customerId, productId);

        var acceptOfferRequest = new AcceptOfferRequest();
        acceptOfferRequest
                .setFirstname("darya")
                .setLastname("belka")
                .setEmail("darya.belka@gmail.com");

        ReservationDetails reservationDetails = sales.acceptOffer(customerId, acceptOfferRequest);

        assertThat(reservationDetails.getPaymentUrl()).isNotBlank();
        assertThat(reservationDetails.getReservationId()).isNotBlank();

        assertPaymentHasBeenRegistered();
        assertThereIsReservationWithId(reservationDetails.getReservationId());
        assertReservationIsPending(reservationDetails.getReservationId());
        assertReservationIsDoneForCustomer(reservationDetails.getReservationId(), "darya", "belka", "darya.belka@gmail.com");
        assertReservationTotalMatchOffer(reservationDetails.getReservationId(), BigDecimal.valueOf(20));
    }
    private void assertReservationTotalMatchOffer(String reservationId, BigDecimal expectedTotal) {
        Reservation loaded = reservationRepository.load(reservationId)
                .get();

        assertThat(loaded.getTotal()).isEqualTo(expectedTotal);
    }

    private void assertReservationIsDoneForCustomer(String reservationId, String firstName, String lastName, String email) {
        Reservation loaded = reservationRepository.load(reservationId)
                .get();

        CustomerDetails clientData = loaded.getCustomerDetails();

        assertThat(clientData.getFirstName()).isEqualTo(firstName);
        assertThat(clientData.getLastName()).isEqualTo(lastName);
        assertThat(clientData.getEmail()).isEqualTo(email);
    }

    private void assertReservationIsPending(String reservationId) {
        Reservation loaded = reservationRepository.load(reservationId)
                .get();

        assertThat(loaded.isPending()).isTrue();
    }

    private void assertThereIsReservationWithId(String reservationId) {
        Optional<Reservation> loaded = reservationRepository.load(reservationId);

        assertThat(loaded).isPresent();
    }

    private void assertPaymentHasBeenRegistered() {
        assertThat(spyPaymentGateway.getRequestsCount()).isEqualTo(1);
    }

    private String thereIsProduct(String productId, BigDecimal price) {
        return productId;
    }

    private String thereIsCustomer(String id) {
        return id;
    }

    private SalesFacade thereIsSales() {
        return new SalesFacade(
                new InMemoryCartStorage(),
                new OfferCalculator(catalog),
                spyPaymentGateway,
                reservationRepository
        );
    }

}