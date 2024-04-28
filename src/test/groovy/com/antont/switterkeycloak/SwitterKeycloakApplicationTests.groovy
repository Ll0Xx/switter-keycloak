package com.antont.switterkeycloak

import com.antont.switterkeycloak.web.rest.AuthenticationController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SwitterKeycloakApplicationTests extends Specification {

    @Autowired (required = false)
    private AuthenticationController authenticationController

    def "when context is loaded then all expected beans are created"() {
        expect: "the WebController is created"
        authenticationController
    }
}