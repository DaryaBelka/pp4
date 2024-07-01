package pl.belka.ecommerce.infrastructure;

import org.springframework.web.client.RestTemplate;
import pl.belka.ecommerce.payu.*;
import pl.belka.ecommerce.sales.payment.PaymentDetails;
import pl.belka.ecommerce.sales.payment.PaymentGateway;
import pl.belka.ecommerce.sales.payment.RegisterPaymentRequest;

import java.util.Arrays;
import java.util.UUID;

public class PayUPaymentGateway implements PaymentGateway {
    PayU payU;

    public PayUPaymentGateway(PayU payU){
        this.payU = payU;
    }

    public PayUPaymentGateway(RestTemplate restTemplate, PayUCredentials sandbox) {
    }

    public PayUPaymentGateway() {
    }

    @Override
    public PaymentDetails registerPayment(RegisterPaymentRequest registerPaymentRequest){
        var request = new OrderCreateRequest();
        request.setNotifyUrl("https://your.eshop.com/notify")
                .setCustomerIp("127.0.0.1")
                .setMerchantPosId("300746")
                .setDescription("My ebook store")
                .setCurrencyCode("PLN")
                .setTotalAmount(registerPaymentRequest.getTotalAsPennies())
                .setExtOrderId(UUID.randomUUID().toString())
                .setBuyer(new Buyer()
                        .setEmail("darya.belka@example.com")
                        .setPhone("783484426")
                        .setFirstName("Darya")
                        .setLastName("Belka")
                        .setLanguage("pl")
                )
                .setProducts(Arrays.asList(
                        new Product()
                                .setName("Ebook x")
                                .setQuantity(1)
                                .setUnitPrice(21000)
                ));

        OrderCreateResponse response = payU.handle(request);

        return new PaymentDetails(response.getRedirectUri(), response.getOrderId());
    }
}