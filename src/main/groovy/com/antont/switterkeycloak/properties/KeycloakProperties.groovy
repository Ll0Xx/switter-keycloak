package com.antont.switterkeycloak.properties

import jakarta.validation.constraints.NotBlank
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "keycloak")
class KeycloakProperties {
    @NotBlank
    String adminClientId
    @NotBlank
    String adminClientSecret
    @NotBlank
    String authClientId
    @NotBlank
    String authServerUrl
    @NotBlank
    String realm
}
