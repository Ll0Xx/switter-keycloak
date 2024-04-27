package com.antont.switterkeycloak.service.impl

import com.antont.switterkeycloak.properties.KeycloakProperties
import com.antont.switterkeycloak.service.UserService
import com.antont.switterkeycloak.web.dto.CreateUserDto
import com.antont.switterkeycloak.web.dto.UpdatePasswordDto
import jakarta.ws.rs.core.Response
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.resource.UsersResource
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

import java.util.concurrent.ExecutionException


@Service
class UserServiceImpl implements UserService {

    private Logger LOGGER = LoggerFactory.getLogger(UserService.class)

    private final Keycloak keycloak
    private final KeycloakProperties keycloakProperties

    UserServiceImpl(Keycloak keycloak, KeycloakProperties keycloakConfig) {
        this.keycloak = keycloak
        this.keycloakProperties = keycloakConfig
    }

    @Override
    String registerUser(CreateUserDto dto) {
        try {

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
            usersResource.create(user)
        } catch (Exception e) {
            String message = "Failed to create user"
            LOGGER.error(message, e)
            throw new RuntimeException(message)
        }
    }

    @Override
    String updateUser(UpdatePasswordDto dto) {
        try {
            CredentialRepresentation cred = new CredentialRepresentation()
            cred.setType(CredentialRepresentation.PASSWORD)
            cred.setValue(dto.newPassword)
            cred.setTemporary(false)
            realm.users().get(id).resetPassword(cred)
        } catch (Exception e) {
            String message = "Failed to update password for user: " + id
            LOGGER.error(message, e)
            throw new RuntimeException(message)
        }
    }

    @Override
    String deleteUser(String id) {
        try {
            getUsersResource().delete(id)
        } catch (Exception e) {
            String message = "Failed to delete user with id: " + id
            LOGGER.error(message, e)
            throw new RuntimeException(message)
        }
    }

    private UsersResource getUsersResource() {
        RealmResource realmResource = keycloak.realm(keycloakProperties.realm)
        return realmResource.users()
    }
}
