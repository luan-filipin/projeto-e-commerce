package br.com.e_commerce.gateway_service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("produto-service", r -> r
                .path("/api/produtos/**")
                .uri("lb://produto-service"))
            .route("user-service", r -> r
                    .path("/api/users/**")
                    .uri("lb://user-service"))
            .route("pedidos-server", r -> r
                    .path("/api/pedidos/**")
                    .uri("lb://pedidos-server"))
            .build();
    }
}
