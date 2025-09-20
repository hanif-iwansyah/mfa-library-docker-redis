package com.exam.rbac_jwt_mfa_library.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .addOpenApiCustomizer(
                        openApi -> openApi.setInfo(new io.swagger.v3.oas.models.OpenAPI().getInfo())
                )
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Library API")
                                .version("v1")
                                .description("API documentation for RBAC JWT MFA Library")
                );
    }
}
