package com.antont.switterkeycloak.service.impl

import com.antont.switterkeycloak.properties.KeycloakProperties
import com.antont.switterkeycloak.service.UserService
import com.antont.switterkeycloak.web.dto.CreateUserDto
import com.antont.switterkeycloak.web.dto.PasswordDto
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.resource.UsersResource
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

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
    Integer registerUser(CreateUserDto dto) {
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
            usersResource.create(user).status
        } catch (Exception e) {
            String message = "Failed to create user"
            LOGGER.error(message, e)
            throw new RuntimeException(message)
        }
    }

    @Override
    String updateUser(PasswordDto dto, String userId) {
        try {
            CredentialRepresentation cred = new CredentialRepresentation()
            cred.setType(CredentialRepresentation.PASSWORD)
            cred.setValue(dto.password)
            cred.setTemporary(false)
            getUsersResource().get(userId).resetPassword(cred)
            "Password successfully updated"
        } catch (Exception e) {
            String message = "Failed to update password for user: " + userId
            LOGGER.error(message, e)
            throw new RuntimeException(message)
        }
    }

    @Override
    Integer deleteUser(String userId) {
        try {
            getUsersResource().get(userId).logout()
            getUsersResource().delete(userId).status
        } catch (Exception e) {
            String message = "Failed to delete user with id: " + userId
            LOGGER.error(message, e)
            throw new RuntimeException(message)
        }
    }

    private UsersResource getUsersResource() {
        RealmResource realmResource = keycloak.realm(keycloakProperties.realm)
        return realmResource.users()
    }
}
