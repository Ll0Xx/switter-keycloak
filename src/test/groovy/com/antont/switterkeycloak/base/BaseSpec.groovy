package com.antont.switterkeycloak.base

import com.antont.switterkeycloak.db.repository.UsersRepository
import com.antont.switterkeycloak.service.AuthenticationService
import com.antont.switterkeycloak.service.UserService
import com.antont.switterkeycloak.web.dto.CreateUserDto
import com.antont.switterkeycloak.web.dto.LoginDto
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.EnableSharedInjection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
@EnableWebSecurity
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableSharedInjection
class BaseSpec extends Specification {
    static def username = "user1"
    static def pass = "pass123"
    static def newPass = "newPass"

    @Shared String accessToken

    @Shared @Autowired
    protected
    UsersRepository usersRepository
    @Shared @Autowired
    protected
    UserService userService
    @Autowired
    private AuthenticationService authenticationService

    @Autowired
    MockMvc mvc

    protected def createUser(String username, String password ){
        def createUserDto = new CreateUserDto(username: username, password: password, passwordConfirmation: password)
        userService.registerUser(createUserDto)
        username
    }

    protected def generateAccessToken(String username, String password ){
        def loginDto = new LoginDto()
        loginDto.username = username
        loginDto.password = password
        def response = authenticationService.generateAccessToken(loginDto)
        parseAccessToken(response)
    }

    protected def parseAccessToken(String response) {
        ObjectMapper mapper = new ObjectMapper()
        JsonNode rootNode = mapper.readTree(response)
        rootNode.get("access_token").asText()
    }
}
