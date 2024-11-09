package com.filmupia.backend.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Filmupia REST API",
                version = "1.0",
                description = "Comprehensive documentation for the Filmupia REST API," +
                        " including endpoint descriptions and usage instructions.",
                contact = @Contact(
                        name = "Tarkhan Gurbanli",
                        email = "filmupia@gmail.com",
                        url = "https://www.bookwave.az"
                )
        ),
        security = {@SecurityRequirement(name = "bearerToken")},
        externalDocs = @ExternalDocumentation(
                description = "Access the full API documentation",
                url = "http://localhost:9000/swagger-ui/index.html"
        )
)
@SecuritySchemes({
        @SecurityScheme(
                name = "bearerToken",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT",
                description = "JWT-based authentication scheme for securing API endpoints." +
                        " Pass the token in the Authorization header as 'Bearer <token>'"
        )
})
public class SwaggerConfig {
}
