package pl.belka.creditcard;

import java.math.BigDecimal;

public class CreditCard {
    private BigDecimal creditLimit;

    public void assignCredit(BigDecimal creditLimit) {

        this.creditLimit = creditLimit;
    }

    public BigDecimal getBalance() {
        return creditLimit;
    }
}
