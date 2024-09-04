package com.example.market.global.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;


import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

// http://localhost:8080/swagger-ui/index.html
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Swagger UI",
                version = "1.0",
                description = "쇼핑몰 API 명세서",
                contact = @Contact(name = "민선익")
        ),
        security = {
                @SecurityRequirement(name = "bearerToken"),
                @SecurityRequirement(name = "cookie")
        }
)
@SecuritySchemes({
        @SecurityScheme(
                name = "bearerToken",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT"
        ),
        @SecurityScheme(
                name = "cookie",
                type = SecuritySchemeType.APIKEY,
                in = SecuritySchemeIn.HEADER,
                paramName = "Cookie"
        )
})
public class SwaggerConfig {
//    @Bean
//    public OpenAPI openAPI() {
//        //  JWT 인증을 사용하는 API에 대해 Swagger UI에서 자동으로 JWT 토큰을 입력할 수 있는 기능
//        String jwtSchemeName = "jwtAuth";
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
//        Components components = new Components()
//                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
//                        .name(jwtSchemeName)
//                        .type(SecurityScheme.Type.HTTP)
//                        .scheme("Bearer")
//                        .bearerFormat("JWT"));
//
//        return new OpenAPI()
//                .components(components)
//                .addSecurityItem(securityRequirement)
//                .info(apiInfo());
//    }
//
//    private Info apiInfo() {
//        return new Info()
//                .title("Swagger UI")
//                .description("쇼핑몰 API 명세서")
//                .version("1.0.0");
//    }
}
