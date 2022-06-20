package com.gitcodings.stack.gateway.filter;

import com.gitcodings.stack.gateway.config.GatewayServiceConfig;
import com.gitcodings.stack.gateway.repo.GatewayRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Component
public class GlobalLoggingFilter implements GlobalFilter, Ordered
{
    private static final Logger    LOG          = LoggerFactory.getLogger(GlobalLoggingFilter.class);
    private static final Scheduler DB_SCHEDULER = Schedulers.boundedElastic();

    private final GatewayRepo          gatewayRepo;
    private final GatewayServiceConfig config;

    @Autowired
    public GlobalLoggingFilter(GatewayRepo gatewayRepo, GatewayServiceConfig config)
    {
        this.gatewayRepo = gatewayRepo;
        this.config = config;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        return chain.filter(exchange);
    }

    @Override
    public int getOrder()
    {
        return -1;
    }

    public void drainRequests()
    {
    }
}
