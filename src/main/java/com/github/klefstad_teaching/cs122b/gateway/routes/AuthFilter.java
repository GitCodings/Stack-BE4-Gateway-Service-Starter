package com.github.klefstad_teaching.cs122b.gateway.routes;

import com.github.klefstad_teaching.cs122b.core.result.Result;
import com.github.klefstad_teaching.cs122b.gateway.config.GatewayServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class AuthFilter implements GatewayFilter
{
    private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);

    private final GatewayServiceConfig config;
    private final WebClient            webClient;

    @Autowired
    public AuthFilter(GatewayServiceConfig config)
    {
        this.config = config;
        this.webClient = WebClient.builder().build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        return chain.filter(exchange);
    }

    private Result authenticate(String encodedJWT)
    {
        return Result.NO_RESULT;
    }

    private Optional<String> encodedJWT(ServerWebExchange exchange)
    {
        return Optional.empty();
    }
}
