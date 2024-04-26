package com.antont.switterkeycloak.service.impl

import com.antont.switterkeycloak.properties.KeycloakProperties
import com.antont.switterkeycloak.service.UserService
import com.antont.switterkeycloak.web.dto.CreateUserDto
import jakarta.ws.rs.core.Response
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.resource.UsersResource
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Service

@Service
class UserServiceImpl implements UserService {

    private final Keycloak keycloak
    private final KeycloakProperties keycloakProperties

    UserServiceImpl(Keycloak keycloak, KeycloakProperties keycloakConfig) {
        this.keycloak = keycloak
        this.keycloakProperties = keycloakConfig
    }

    @Override
    String registerUser(CreateUserDto dto) {
        UserRepresentation user = new UserRepresentation()
        user.setEnabled(true)
        user.setUsername(dto.username)

        CredentialRepresentation representation = new CredentialRepresentation()
        representation.setValue(dto.password)
        representation.setTemporary(false)
        representation.setType(CredentialRepresentation.PASSWORD)

        List<CredentialRepresentation> list = new ArrayList<>()
        list.add(representation)
        user.setCredentials(list)

        UsersResource usersResource = getUsersResource()
        Response response = usersResource.create(user)

        return "yay"
    }

    private UsersResource getUsersResource() {
        RealmResource realmResource = keycloak.realm(keycloakProperties.realm)
        return realmResource.users()
    }
}
