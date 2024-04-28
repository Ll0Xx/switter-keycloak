package com.antont.switterkeycloak

import com.antont.switterkeycloak.base.BaseSpec
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserRestControllerSpec extends BaseSpec {
    def "test creating user. Should success"() {
        expect: "Status is 201"
        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"username":"' + username + '", "password": "' + pass + '", "passwordConfirmation":"' + pass + '" }'))
                .andExpect(status().isCreated())
        and: "user with username user1 created"
        usersRepository.findByUsername(username).isPresent()
    }

    def "test creating user with duplicated username. Should fail"() {
        expect: "Status is 209"
        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"username":"' + username + '", "password": "' + pass + '", "passwordConfirmation":"' + pass + '" }'))
                .andExpect(status().isConflict())
    }

    def "test creating user with not matching passwords. Should fail"() {
        expect: "Status is 400"
        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"username":"' + username + '", "password": "' + pass + '", "passwordConfirmation":"' + newPass + '" }'))
                .andExpect(status().isBadRequest())
    }

    def "test updating user password without authorization. Should fail"() {
        expect: "Status is 401"
        mvc.perform(patch("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"username":"' + username + '", "password": "' + newPass + '", "passwordConfirmation":"' + newPass + '" }'))
                .andExpect(status().isUnauthorized())
    }

    def "test updating user password. Should success"() {
        given:
        def accessCode = generateAccessToken(username, pass)
        expect: "Status is 200"
        mvc.perform(patch("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${accessCode}")
                .content('{"username":"' + username + '", "password": "' + newPass + '", "passwordConfirmation":"' + newPass + '" }'))
                .andExpect(status().isOk())
                .andExpect(content().string("Password successfully updated"))
    }

    def "test updating user password with not matching passwords. Should fail"() {
        given:
        def accessCode = generateAccessToken(username, newPass)
        expect: "Status is 400"
        mvc.perform(patch("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${accessCode}")
                .content('{"password": "'+ pass +'", "passwordConfirmation":"'+ newPass +'" }'))
                .andExpect(status().isBadRequest())
    }

    def "test deleting user without authorization. Should fail"() {
        expect: "Status is 401"
        mvc.perform(delete("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"username":"' + username + '", "password": "' + newPass + '", "passwordConfirmation":"' + newPass + '" }'))
                .andExpect(status().isUnauthorized())
    }

    def "test deleting user"() {
        given:
        def accessCode = generateAccessToken(username, newPass)
        expect: "Status is 204"
        mvc.perform(delete("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${accessCode}"))
                .andExpect(status().isNoContent())
    }
}
