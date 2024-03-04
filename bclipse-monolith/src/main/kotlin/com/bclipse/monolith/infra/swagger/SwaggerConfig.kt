package com.bclipse.monolith.infra.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val info: Info = Info()
            .title("Bclipse application API Specification")
            .version("v0.0.1")
            .description("for better minecraft server development experience")
        return OpenAPI()
            .addSecurityItem(SecurityRequirement()
                .addList("jwt")
                .addList("base64uuid")
                .addList("accesstype")
            ).components(Components()
                .addSecuritySchemes("jwt", SecurityScheme().apply {
                    name = "jwt"
                    type = SecurityScheme.Type.HTTP
                    scheme = "Bearer"
                    bearerFormat = "JWT"
                }).addSecuritySchemes("base64uuid", SecurityScheme().apply {
                    name = "base64uuid"
                    type = SecurityScheme.Type.HTTP
                    scheme = ""
                }).addSecuritySchemes("", SecurityScheme().apply {
                    name = "accesstype"
                    type = SecurityScheme.Type.HTTP
                    scheme = "Bearer"
                    bearerFormat = "JWT"
                })
            ).info(info)
    }
}