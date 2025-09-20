package com.example.irctc.irctc_backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "IRCTC API",
                version = "1.0.0",
                description = "API documentation for IRCTC Project",
                termsOfService = "https://www.irctc.co.in/terms-of-service",
                contact = @Contact(
                        name = "IRCTC API Support",
                        url = "https://www.irctc.co.in/contact-us",
                        email = "sagar201cse@gmail.com"
                )
        ), security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth", // Name referenced in @SecurityRequirement
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
    @Bean
    public OperationCustomizer autoSummaries() {
        return (operation, handlerMethod) -> {
            if (operation.getSummary() == null) {
                // Convert camelCase method name to words, e.g., "createTrain" → "Create Train"
                String name = handlerMethod.getMethod().getName()
                        .replaceAll("([a-z])([A-Z]+)", "$1 $2");
                operation.setSummary(Character.toUpperCase(name.charAt(0)) + name.substring(1));
            }
            // 2️⃣ Auto-generate default responses if none provided
            ApiResponses responses = operation.getResponses();
            if (responses == null || responses.isEmpty()) {
                ApiResponses apiResponses = new ApiResponses();

                // 200 OK
                apiResponses.addApiResponse("200", new ApiResponse().description("Successful operation"));

                // 400 Bad Request
                apiResponses.addApiResponse("400", new ApiResponse().description("Bad request"));

                // 401 Unauthorized
                apiResponses.addApiResponse("401", new ApiResponse().description("Unauthorized"));

                // 500 Internal Server Error
                apiResponses.addApiResponse("500", new ApiResponse().description("Internal server error"));

                operation.setResponses(apiResponses);
            }


            return operation;
        };
    }

    @Bean
    public OperationCustomizer addParamDescriptions() {
        return (operation, handlerMethod) -> {
            if (operation.getParameters() != null) {
                operation.getParameters().forEach(param -> {
                    if (param.getDescription() == null) {
                        param.setDescription("Parameter: " + param.getName());
                    }
                });
            }
            return operation;
        };
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/v3/api-docs/**")
                        .allowedOrigins("http://localhost:5173");
            }
        };
    }



//
//    @Bean
//    public OpenAPI openAPI(){
//        return  new OpenAPI().info(
//                new Info().title("IRCTC API")
//                        .version("1.0.0")
//                        .description("API documentation for IRCTC Project")
//                        .termsOfService("https:/www.irctc.co.in/terms-of-service")
//                        .contact(new io.swagger.v3.oas.models.info.Contact()
//                                .name("IRCTC API Support")
//                                .url("https://www.irctc.co.in/contact-us")
//                                .email("sagar201cse@gmail.com"))
//        );
//    }
}

