package com.wildfit.server.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        final var info = new io.swagger.v3.oas.models.info.Info();
        info.setTitle("WILDFIT API");
        info.setVersion("0.1.4");
        info.setDescription("REST API for WILDFIT application");
        return new OpenAPI().info(info);
    }

}

