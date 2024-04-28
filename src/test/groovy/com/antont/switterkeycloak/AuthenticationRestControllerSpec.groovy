package com.antont.switterkeycloak

import com.antont.switterkeycloak.base.BaseSpec
import org.spockframework.spring.EnableSharedInjection
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@EnableSharedInjection
class AuthenticationRestControllerSpec extends BaseSpec {
    def "test try login to existing user. Should success"() {
        given:
        createUser(username, pass)
        expect: "Status is 200"
        def result = mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"username":"' + username + '", "password": "' + pass + '"}'))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath('$.access_token').exists())
                .andReturn()
        cleanup:
        accessToken = parseAccessToken(result.response.getContentAsString())
    }

    def "test try login to existing user with invalid credentials. Should fail"() {
        expect: "Status is 400"
        mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"username":"' + username + '", "password": "' + newPass + '"}'))
                .andExpect(status().isBadRequest())
    }

    def "test try logout. Should fail"() {
        expect: "Status is 200"
        mvc.perform(post("/auth/logout")
                .header("Authorization", "Bearer ${accessToken}"))
                .andExpect(status().isOk())
        and: "check if the access token is invalidated"
        mvc.perform(get("/feed")
                .header("Authorization", "Bearer ${accessToken}"))
                .andExpect(status().isBadRequest())
    }

    def cleanupSpec() {
        def user = usersRepository.findByUsername(username).orElseThrow {
            throw new RuntimeException("Failed to find user with username: ${username}")
        }
        userService.deleteUser(user.keycloakId)
    }
}
