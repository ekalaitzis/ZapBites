package com.example.zapbites.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Emmanouil",
                        email = "emmanouil@kalaitzis.pro",
                        url = "https://kalaitzis.com/zapbites"
                ),
                description = "Food Delivery Application API Documentation",
                title = "OpenApi specification",
                version = "1.0"
        ),
        servers = {@Server(
                description = "local ENV",
                url = "http://localhost:8080/api"
        )
        },security = {@SecurityRequirement(name = "basicAuth")})
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}
