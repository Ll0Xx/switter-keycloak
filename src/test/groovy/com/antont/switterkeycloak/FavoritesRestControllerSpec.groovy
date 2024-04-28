package com.antont.switterkeycloak

import com.antont.switterkeycloak.base.BaseSpec
import com.antont.switterkeycloak.service.PostsService
import com.antont.switterkeycloak.web.dto.PostDto
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class FavoritesRestControllerSpec extends BaseSpec {

    @Shared @Autowired
    PostsService postsService

    @Shared
    String postId

    def "test try add post favorite with authentication. Should success"() {
        given:
        def user = usersRepository.findByUsername(createUser(username, pass)).orElseThrow {
            new RuntimeException("Failed to find user")
        }
        accessToken = generateAccessToken(username, pass)
        def createdPost = postsService.createPost(new PostDto(title: "test title", content: "test content"), user.keycloakId)
        postId = createdPost.id
        expect:
        mvc.perform(post("/posts/${postId}/add-favorite")
                .header("Authorization", "Bearer ${accessToken}"))
                .andExpect(status().isOk())
    }

    def "test try create post without authentication. Should fail"() {
        expect:
        mvc.perform(post("/posts/${postId}/add-favorite"))
                .andExpect(status().isUnauthorized())
    }

    def "test try remove post remove with authentication. Should success"() {
        expect:
        mvc.perform(post("/posts/${postId}/remove-favorite")
                .header("Authorization", "Bearer ${accessToken}"))
                .andExpect(status().isOk())
    }

    def "test try remove post remove without authentication. Should fail"() {
        expect:
        mvc.perform(post("/posts/${postId}/remove-favorite"))
                .andExpect(status().isUnauthorized())
    }

    def cleanupSpec() {
        def user = usersRepository.findByUsername(username).orElseThrow {
            throw new RuntimeException("Failed to find user with username: ${username}")
        }
        postsService.deletePost(postId, user.keycloakId)
        userService.deleteUser(user.keycloakId)
    }
}
