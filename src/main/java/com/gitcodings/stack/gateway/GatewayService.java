package com.gitcodings.stack.gateway;

import com.gitcodings.stack.gateway.config.GatewayServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    GatewayServiceConfig.class
})
public class GatewayService
{
    public static void main(String[] args)
    {
        SpringApplication.run(GatewayService.class, args);
    }
}
