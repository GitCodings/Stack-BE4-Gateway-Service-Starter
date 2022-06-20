package com.gitcodings.stack.gateway.routes;

import com.gitcodings.stack.gateway.filter.AuthFilter;
import com.gitcodings.stack.gateway.config.GatewayServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GatewayRouteLocator
{
    private final GatewayServiceConfig config;
    private final AuthFilter           authFilter;

    @Autowired
    public GatewayRouteLocator(GatewayServiceConfig config,
                               AuthFilter authFilter)
    {
        this.config = config;
        this.authFilter = authFilter;
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder)
    {
        return builder.routes()
                      .route("idm",
                             r -> r.uri(config.getIdm()))
                      .route("movies",
                             r -> r.uri(config.getMovies()))
                      .route("billing",
                             r -> r.uri(config.getBilling()))
                      .build();
    }
}
