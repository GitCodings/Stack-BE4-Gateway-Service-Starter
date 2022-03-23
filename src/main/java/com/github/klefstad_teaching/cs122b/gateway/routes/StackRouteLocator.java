package com.github.klefstad_teaching.cs122b.gateway.routes;

import com.github.klefstad_teaching.cs122b.gateway.config.GatewayServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class StackRouteLocator
{
    private final GatewayServiceConfig config;
    private final AuthFilter           authFilter;

    @Autowired
    public StackRouteLocator(GatewayServiceConfig config, AuthFilter authFilter)
    {
        this.config = config;
        this.authFilter = authFilter;
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder)
    {
        return builder.routes()
                      .build();
    }
}
