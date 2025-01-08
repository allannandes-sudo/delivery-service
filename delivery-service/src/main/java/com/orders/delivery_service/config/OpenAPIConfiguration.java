package com.orders.delivery_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(info());
    }

    private Info info() {
        return new Info()
                .title("Delivery")
                .description("API Responsável receber e validar pedidos.")
                .version("1.0.0");
    }
}
