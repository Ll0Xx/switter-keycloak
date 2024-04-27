package com.antont.switterkeycloak.service.impl

import com.antont.switterkeycloak.properties.KeycloakProperties
import com.antont.switterkeycloak.service.AuthenticationService
import com.antont.switterkeycloak.service.KeycloakService
import com.antont.switterkeycloak.web.dto.LoginDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

@Service
class AuthenticationServiceImpl implements AuthenticationService {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class)

    private final KeycloakService keycloakService
    private final KeycloakProperties keycloakProperties

    AuthenticationServiceImpl(KeycloakService keycloakService, KeycloakProperties keycloakProperties) {
        this.keycloakService = keycloakService
        this.keycloakProperties = keycloakProperties
    }

    @Override
    String generateAccessToken(LoginDto dto) {
        try {
            def restTemplate = new RestTemplate()
            def headers = new HttpHeaders()
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)
            def map = new LinkedMultiValueMap<>()
            map.add("client_id", keycloakProperties.authClientId)
            map.add("username", dto.username)
            map.add("password", dto.password)
            map.add("grant_type", "password")

            def entity = new HttpEntity<>(map, headers)
            def url = keycloakProperties.authServerUrl + "/realms/" + keycloakProperties.realm + "/protocol/openid-connect/token"
            restTemplate.postForEntity(url, entity, String.class).body
        } catch (Exception e) {
            def message = "Failed to login"
            LOGGER.error(message)
            throw new RuntimeException(message, e)
        }
    }

    @Override
    void logout(String userId) {
        try {
            keycloakService.usersResource.get(userId).logout()
            "You have successfully signed out of your account"
        } catch (Exception e) {
            def message = "Failed to logout user with id: " + userId
            LOGGER.error(message)
            throw new RuntimeException(message, e)
        }
    }
}
