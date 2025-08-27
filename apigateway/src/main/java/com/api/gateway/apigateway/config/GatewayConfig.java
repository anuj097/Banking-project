package com.api.gateway.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
    	return builder.routes()
    		    .route("BANKING", r -> r
    		        .path("/accounts/**")
    		        .uri("lb://banking"))
    		    .build();
    }
}
