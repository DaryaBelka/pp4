package pl.belka.ecommerce.sales.payment;

public class PaymentDetails {
    private final String url;

    public PaymentDetails(String url, String orderId) {
        this.url = url;
    }

    public String getPaymentUrl() {
        return url;
    }
}