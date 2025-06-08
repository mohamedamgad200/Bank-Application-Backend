package com.example.Bank.Application.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "mohamed amgad",
                        email="mohamedamgadofficial@gmail.com"
                ),
                description = "OpenApi Documentation For Bank Application With SpringBoot",
                title="Bank Application SpringBoot",
                version = "v1.0"
        ),
        servers ={
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        },
        externalDocs = @ExternalDocumentation(
                description = "OpenApi Documentation For Bank Application With SpringBoot",
                url="https://github.com/mohamedamgad200/Bank-Application-Backend"
        )
)
public class OpenApiConfig {
}
