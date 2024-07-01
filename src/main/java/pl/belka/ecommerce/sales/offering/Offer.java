package pl.belka.ecommerce.sales.offering;

import java.math.BigDecimal;

public class Offer {
    private int itemsCount;
    private BigDecimal total;

    public Offer(BigDecimal total, int itemsCount) {
        this.total = total;
        this.itemsCount = itemsCount;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public BigDecimal getTotal() {
        return total;
    }
}