package dev.luiz.url_shortener;

import dev.luiz.url_shortener.dto.UrlRequest;
import dev.luiz.url_shortener.dto.UrlResponse;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlControllerTest {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    void deveCriarUrlEncurtada() {
        UrlRequest request = new UrlRequest();
        request.setUrl("https://www.google.com");

        ResponseEntity<UrlResponse> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/urls",
                request,
                UrlResponse.class
        );

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody().getCode());
    }

    @Test
    void deveRedirecionarParaUrlOriginal() {
        UrlRequest request = new UrlRequest();
        request.setUrl("https://www.google.com");
        ResponseEntity<UrlResponse> postResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/urls",
                request,
                UrlResponse.class
        );
        String code = postResponse.getBody().getCode();

        ResponseEntity<String> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/urls/" + code,
                String.class
        );

        assertEquals(302, getResponse.getStatusCode().value());
    }
}