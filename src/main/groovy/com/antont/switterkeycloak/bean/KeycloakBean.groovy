package com.antont.switterkeycloak.bean

import com.antont.switterkeycloak.properties.KeycloakProperties
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class KeycloakBean {

    private final KeycloakProperties properties

    KeycloakBean(KeycloakProperties properties) {
        this.properties = properties
    }

    @Bean
    Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(properties.authServerUrl)
                .realm(properties.realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(properties.adminClientId)
                .clientSecret(properties.adminClientSecret)
                .build()
    }
}
