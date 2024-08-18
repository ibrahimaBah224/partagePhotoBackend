package SPA.dev.Stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI swaggerAPI() {
        String securitySchemeName = "Auth JWT";
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()).info(new Info().title("Management System")
                        .description("API service for developers").version("v0.1.0"));
    }
}
