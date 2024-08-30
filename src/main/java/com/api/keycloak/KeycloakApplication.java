package com.api.keycloak;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Claims Service",
                version = "1.0",
                description = "Claims Information"
        ),
        security = {
                @SecurityRequirement(name = "oauth")
        }
)
@SecurityScheme(
        name = "oauth",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                clientCredentials = @OAuthFlow(
                        tokenUrl ="http://localhost:8080/realms/Vannthy/protocol/openid-connect/token"
                )
        ),
        in = SecuritySchemeIn.HEADER
)
public class KeycloakApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeycloakApplication.class, args);
    }

}
