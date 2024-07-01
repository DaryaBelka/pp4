package pl.belka.ecommerce.sales.reservation;

import pl.belka.ecommerce.payu.OrderCreateResponse;
import pl.belka.ecommerce.sales.payment.PaymentDetails;
import pl.belka.ecommerce.sales.payment.PaymentGateway;
import pl.belka.ecommerce.sales.payment.RegisterPaymentRequest;

public class SpyPaymentGateway implements PaymentGateway {
    Integer requestCount = 0;
    public RegisterPaymentRequest lastRequest;
    private OrderCreateResponse response;

    public Integer getRequestsCount(){
        return requestCount;
    }

    @Override
    public PaymentDetails registerPayment(RegisterPaymentRequest registerPaymentRequest){
        this.requestCount++;
        lastRequest = registerPaymentRequest;
        return new PaymentDetails("http://spy-gateway", response.getOrderId());
    }
}