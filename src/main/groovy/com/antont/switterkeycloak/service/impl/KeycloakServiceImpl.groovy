package com.antont.switterkeycloak.service.impl

import com.antont.switterkeycloak.properties.KeycloakProperties
import com.antont.switterkeycloak.service.KeycloakService
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.resource.UsersResource
import org.springframework.stereotype.Service

@Service
class KeycloakServiceImpl implements KeycloakService {

    private final Keycloak keycloak
    private final KeycloakProperties keycloakProperties

    KeycloakServiceImpl(Keycloak keycloak, KeycloakProperties keycloakProperties) {
        this.keycloak = keycloak
        this.keycloakProperties = keycloakProperties
    }

    @Override
    UsersResource getUsersResource() {
        RealmResource realmResource = keycloak.realm(keycloakProperties.realm)
        return realmResource.users()
    }
}
