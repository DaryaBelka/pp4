package pl.belka.ecommerce.catalog;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class HttpProductCatalogTest {
    @LocalServerPort
    private int localPort;

    @Autowired
    TestRestTemplate http;
    @Test
    void itLoadsHomepage(){
        var url = String.format("http://localhost:%s/%s", localPort, "");

        ResponseEntity<String> response=http.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Welcome");

    }

    @Autowired
    ProductCatalog catalog;

    @Test
    void itLoadsProducts() {
        catalog.addProduct("Example product", "ex descr");

        var url = String.format("http://localhost:%s/%s", localPort, "api/products");
        ResponseEntity<Product[]> response = http.getForEntity(url,Product[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .hasSizeGreaterThan(0)
                .extracting("name")
                .contains("Example product");
    }
}