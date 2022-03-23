package com.github.klefstad_teaching.cs122b.gateway;

import com.github.klefstad_teaching.cs122b.core.spring.StackService;
import com.github.klefstad_teaching.cs122b.gateway.config.GatewayServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = {
    SecurityAutoConfiguration.class
})
@StackService
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
