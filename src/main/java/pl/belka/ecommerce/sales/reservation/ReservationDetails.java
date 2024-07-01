package pl.belka.ecommerce.sales.reservation;

import java.math.BigDecimal;

public class ReservationDetails {
    private String reservationId;
    private String paymentUrl;

    public ReservationDetails(String reservationId, String paymentUrl) {
        this.reservationId = reservationId;
        this.paymentUrl = paymentUrl;
    }

    public BigDecimal getTotal(){
        return BigDecimal.valueOf(0);
    }

    public String getReservationId(){
        return reservationId;
    }

    public ReservationDetails setReservationId(String reservationId) {
        this.reservationId = reservationId;
        return this;
    }

    public String getPaymentUrl(){
        return paymentUrl;
    }

    public ReservationDetails setPaymentURL(String paymentUrl) {
        this.paymentUrl = paymentUrl;
        return this;
    }
}