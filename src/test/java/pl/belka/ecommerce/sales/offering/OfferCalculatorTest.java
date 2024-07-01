package pl.belka.ecommerce.sales.offering;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.belka.ecommerce.catalog.ProductCatalog;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Collections;

@SpringBootTest
public class OfferCalculatorTest {
    @Autowired
    ProductCatalog catalog;

    @Test
    void zeroOfferForEmptyItems(){
        OfferCalculator calculator = new OfferCalculator(catalog);

        Offer offer = calculator.calculate(Collections.emptyList());

        assertThat(offer.getTotal()).isEqualTo(BigDecimal.ZERO);
    }
}