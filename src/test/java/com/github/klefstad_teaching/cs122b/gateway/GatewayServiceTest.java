package com.github.klefstad_teaching.cs122b.gateway;

import com.github.klefstad_teaching.cs122b.core.result.Result;
import com.github.klefstad_teaching.cs122b.core.security.JWTAuthenticationFilter;
import com.github.klefstad_teaching.cs122b.gateway.helper.ExpectedModels;
import com.github.klefstad_teaching.cs122b.gateway.helper.MockServices;
import com.github.klefstad_teaching.cs122b.gateway.helper.TestConfig;
import com.github.klefstad_teaching.cs122b.gateway.helper.WebExchangeWithIP;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.ResultMatcher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql("/empty-gateway-test-data.sql")
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GatewayServiceTest
{
    private final WebTestClient              webTestClient;
    private final MockServices               mockServices;
    private final TestConfig                 testConfig;
    private final ExpectedModels             expectedModels;
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public GatewayServiceTest(ApplicationContext context,
                              MockServices mockServices,
                              TestConfig testConfig,
                              ExpectedModels expectedModels,
                              NamedParameterJdbcTemplate template)
    {
        this.webTestClient = WebTestClient.bindToApplicationContext(context)
                                          .webFilter(WebExchangeWithIP::filter)
                                          .configureClient()
                                          .build();

        this.mockServices = mockServices;
        this.testConfig = testConfig;
        this.expectedModels = expectedModels;
        this.template = template;
    }

    private ResultMatcher[] isResult(Result result)
    {
        return new ResultMatcher[]{
            status().is(result.status().value()),
            jsonPath("result.code").value(result.code()),
            jsonPath("result.message").value(result.message())
        };
    }

    public Integer checkRowCount()
    {
        return this.template.queryForObject(
            "SELECT COUNT(*) FROM gateway.request;",
            new MapSqlParameterSource(),
            Integer.class
        );
    }

    public String getLastRowPair()
    {
        return this.template.queryForObject(
            "SELECT CONCAT(ip_address, ',', path) " +
            "FROM gateway.request r " +
            "ORDER BY call_time DESC " +
            "LIMIT 1;",
            new MapSqlParameterSource(),
            String.class
        );
    }

    @Test
    public void applicationLoads()
        throws Exception
    {
    }

    @Test
    public void authenticateAdmin()
        throws Exception
    {
        webTestClient.post()
                     .uri("/idm/authenticate")
                     .bodyValue(testConfig.fromHeader(testConfig.getAdminAccessToken()))
                     .exchange().expectStatus().isEqualTo(expectedModels.getValidResponse().getResult().status())
                     .expectBody().json(expectedModels.getValidResponseString());

        assertEquals(checkRowCount(), 0);
    }

    @Test
    public void authenticateEmployee()
        throws Exception
    {
        webTestClient.post()
                     .uri("/idm/authenticate")
                     .bodyValue(testConfig.fromHeader(testConfig.getEmployeeAccessToken()))
                     .exchange().expectStatus().isEqualTo(expectedModels.getInvalidResponse().getResult().status())
                     .expectBody().json(expectedModels.getInvalidResponseString());

        assertEquals(checkRowCount(), 0);
    }

    @Test
    public void authenticatePremium()
        throws Exception
    {
        webTestClient.post()
                     .uri("/idm/authenticate")
                     .bodyValue(testConfig.fromHeader(testConfig.getPremiumAccessToken()))
                     .exchange().expectStatus().isEqualTo(expectedModels.getExpiredResponse().getResult().status())
                     .expectBody().json(expectedModels.getExpiredResponseString());

        assertEquals(checkRowCount(), 0);
    }

    @Test
    public void moviesWithAuthHeader()
        throws Exception
    {
        webTestClient.get()
                     .uri("/movies/movie/search")
                     .header(HttpHeaders.AUTHORIZATION,
                             JWTAuthenticationFilter.BEARER_PREFIX +
                             testConfig.getAdminAccessToken())
                     .exchange().expectStatus().isEqualTo(HttpStatus.OK);

        assertEquals(checkRowCount(), 0);
    }

    @Test
    public void moviesNoAuthHeader()
        throws Exception
    {
        webTestClient.post()
                     .uri("/movies/movie/search")
                     .exchange().expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);

        assertEquals(checkRowCount(), 0);
    }

    @Test
    public void moviesAuthHeaderWithNoValue()
        throws Exception
    {
        webTestClient.post()
                     .uri("/movies/movie/search")
                     .header(HttpHeaders.AUTHORIZATION, "")
                     .exchange().expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);

        assertEquals(checkRowCount(), 0);
    }

    @Test
    public void moviesAuthHeaderWithNoBearer()
        throws Exception
    {
        webTestClient.post()
                     .uri("/movies/movie/search")
                     .header(HttpHeaders.AUTHORIZATION,
                             testConfig.getAdminAccessToken())
                     .exchange().expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);

        assertEquals(checkRowCount(), 0);
    }

    @Test
    public void billingWithAuthHeader()
        throws Exception
    {
        webTestClient.get()
                     .uri("/billing/cart/retrieve")
                     .header(HttpHeaders.AUTHORIZATION,
                             JWTAuthenticationFilter.BEARER_PREFIX +
                             testConfig.getAdminAccessToken())
                     .exchange().expectStatus().isEqualTo(HttpStatus.OK);

        assertEquals(checkRowCount(), 0);
    }

    @Test
    public void billingNoAuthHeader()
        throws Exception
    {
        webTestClient.post()
                     .uri("/billing/cart/retrieve")
                     .exchange().expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);

        assertEquals(checkRowCount(), 0);
    }

    @Test
    public void billingAuthHeaderWithNoValue()
        throws Exception
    {
        webTestClient.post()
                     .uri("/billing/cart/retrieve")
                     .header(HttpHeaders.AUTHORIZATION, "")
                     .exchange().expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);

        assertEquals(checkRowCount(), 0);
    }

    @Test
    public void billingAuthHeaderWithNoBearer()
        throws Exception
    {
        webTestClient.post()
                     .uri("/billing/cart/retrieve")
                     .header(HttpHeaders.AUTHORIZATION,
                             testConfig.getAdminAccessToken())
                     .exchange().expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);

        assertEquals(checkRowCount(), 0);
    }

    @Test
    @Order(1)
    public void loggingMultipleRequests()
        throws Exception
    {
        Flux.range(0, 99)
            .map(num -> makeCall())
            .flatMap(get -> Mono.fromCallable(get::exchange))
            .blockLast();

        waitForDb();
        assertEquals(checkRowCount(), 0);

        makeCall().exchange();

        waitForDb();
        assertEquals(checkRowCount(), 100);

        Flux.range(0, 99)
            .map(num -> makeCall())
            .flatMap(get -> Mono.fromCallable(get::exchange))
            .blockLast();

        waitForDb();
        assertEquals(checkRowCount(), 100);

        makeCall().exchange();

        waitForDb();
        assertEquals(checkRowCount(), 200);

        String lastCallPair = getLastRowPair();

        String[] split = lastCallPair.split(",");

        waitForDb();
        assertEquals(split[0], "127.0.0.1");
        assertEquals(split[1], "/movies/movie/search");
    }

    private WebTestClient.RequestHeadersSpec<?> makeCall()
    {
        return webTestClient.get()
                            .uri("/movies/movie/search")
                            .header(HttpHeaders.AUTHORIZATION,
                                    JWTAuthenticationFilter.BEARER_PREFIX +
                                    testConfig.getAdminAccessToken());
    }

    private void waitForDb()
        throws Exception
    {
        Thread.sleep(1000);
    }
}
