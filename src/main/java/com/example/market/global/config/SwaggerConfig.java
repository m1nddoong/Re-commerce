package com.example.market.global.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

// http://localhost:8080/swagger-ui/index.html
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Swagger UI",
                version = "1.0",
                description = "쇼핑몰 API 명세서",
                contact = @Contact(name = "민선익")
        ))
public class SwaggerConfig {
}
