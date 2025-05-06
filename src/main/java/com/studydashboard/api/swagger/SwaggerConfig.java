package com.studydashboard.api.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Study DashBoard"
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "local Server")
        },
        security = {
                @SecurityRequirement(name = "BearerAuth")
        }
)
@SecuritySchemes({
        @SecurityScheme(
                name = "BearerAuth",
                type = SecuritySchemeType.HTTP,
                paramName = "Authorization",
                in = SecuritySchemeIn.HEADER,
                scheme = "Bearer",
                bearerFormat = "JWT",
                description = "Bearer 뒷 부분 입력"
        )
})
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/api/**")
                .build();
    }
}