package com.antont.switterkeycloak.service

import org.keycloak.admin.client.resource.UsersResource

interface KeycloakService {

    UsersResource getUsersResource()
}
