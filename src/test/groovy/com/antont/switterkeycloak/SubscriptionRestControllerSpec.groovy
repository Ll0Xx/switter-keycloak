package com.antont.switterkeycloak

import com.antont.switterkeycloak.base.BaseSpec
import com.antont.switterkeycloak.db.entity.User
import com.antont.switterkeycloak.db.repository.SubscriptionRepository
import com.antont.switterkeycloak.service.SubscribeService
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class SubscriptionRestControllerSpec extends BaseSpec {

    static subscribeToUsername = "user2"
    static subscribeToPassword = "pass321"

    @Shared User user
    @Shared User subscribeToUser

    @Autowired
    SubscribeService subscribeService

    @Autowired
    @Shared
    SubscriptionRepository subscriptionRepository

    def "test try subscribe to user by username. Should success"() {
        given:
        user = usersRepository.findByUsername(createUser(username, pass)).orElseThrow {
            new RuntimeException("Failed to find user")
        }
        subscribeToUser = usersRepository.findByUsername(createUser(subscribeToUsername, subscribeToPassword)).orElseThrow {
            new RuntimeException("Failed to find user")
        }
        accessToken = generateAccessToken(username, pass)
        expect:
        mvc.perform(post("/sub?to=${subscribeToUser.username}")
                .header("Authorization", "Bearer ${accessToken}"))
                .andExpect(status().isOk())
    }

    def "test try unsubscribe from user by user username. Should success"() {
        given:
        user = usersRepository.findByUsername(createUser(username, pass)).orElseThrow {
            new RuntimeException("Failed to find user")
        }
        subscribeToUser = usersRepository.findByUsername(createUser(subscribeToUsername, subscribeToPassword)).orElseThrow {
            new RuntimeException("Failed to find user")
        }
        accessToken = generateAccessToken(username, pass)
        expect:
        mvc.perform(post("/unsub?from=${subscribeToUser.username}")
                .header("Authorization", "Bearer ${accessToken}"))
                .andExpect(status().isOk())
    }

    def "test try subscribe to user by user id. Should success"() {
        given:
        user = usersRepository.findByUsername(createUser(username, pass)).orElseThrow {
            new RuntimeException("Failed to find user")
        }
        subscribeToUser = usersRepository.findByUsername(createUser(subscribeToUsername, subscribeToPassword)).orElseThrow {
            new RuntimeException("Failed to find user")
        }
        accessToken = generateAccessToken(username, pass)
        expect:
        mvc.perform(post("/sub?to=${subscribeToUser.id}")
                .header("Authorization", "Bearer ${accessToken}"))
                .andExpect(status().isOk())
    }

    def "test try unsubscribe from user by user id. Should success"() {
        given:
        user = usersRepository.findByUsername(createUser(username, pass)).orElseThrow {
            new RuntimeException("Failed to find user")
        }
        subscribeToUser = usersRepository.findByUsername(createUser(subscribeToUsername, subscribeToPassword)).orElseThrow {
            new RuntimeException("Failed to find user")
        }
        accessToken = generateAccessToken(username, pass)
        expect:
        mvc.perform(post("/unsub?from=${subscribeToUser.id}")
                .header("Authorization", "Bearer ${accessToken}"))
                .andExpect(status().isOk())
    }

    def cleanupSpec() {
        def subscription = subscriptionRepository.findByUserId(user.id).orElseThrow {
            throw new RuntimeException("Failed to find sunscription")
        }
        subscriptionRepository.delete(subscription)

        userService.deleteUser(user.keycloakId)
        userService.deleteUser(subscribeToUser.keycloakId)
    }
}
