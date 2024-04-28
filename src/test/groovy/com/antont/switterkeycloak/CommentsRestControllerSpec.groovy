package com.antont.switterkeycloak

import com.antont.switterkeycloak.base.BaseSpec
import com.antont.switterkeycloak.db.entity.Post
import com.antont.switterkeycloak.db.entity.User
import com.antont.switterkeycloak.db.repository.CommentsRepository
import com.antont.switterkeycloak.service.PostsService
import com.antont.switterkeycloak.web.dto.PostDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import spock.lang.Shared

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class CommentsRestControllerSpec extends BaseSpec {

    final def postComment = "post comment"

    @Shared User user
    @Shared Post post

    @Shared @Autowired
    PostsService postsService
    @Shared @Autowired
    CommentsRepository commentsRepository

    def "test try add comment to post. Should success"() {
        given:
        user = usersRepository.findByUsername(createUser(username, pass)).orElseThrow {
            new RuntimeException("Failed to find user")
        }
        accessToken = generateAccessToken(username, pass)
        post = postsService.createPost(new PostDto(title: "test title", content: "test content"), user.keycloakId)

        expect:
        mvc.perform(post("/posts/${post.id}/add-comment")
                .header("Authorization", "Bearer ${accessToken}")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"comment":"' + postComment + '"}'))
                .andExpect(status().isOk())
                .andExpect(content().string("Your comment was sucessfully added to the post ${post.id}"))
    }

    def "test try get comment from post. Should success"() {
        expect:
        mvc.perform(get("/posts/${post.id}/get-comment")
                .header("Authorization", "Bearer ${accessToken}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.[0].author').value(user.id))
                .andExpect(jsonPath('$.[0].comment').value(postComment))
    }

    def cleanupSpec() {
        def user = usersRepository.findByUsername(username).orElseThrow {
            throw new RuntimeException("Failed to find user with username: ${username}")
        }
        commentsRepository.deleteAll()
        postsService.deletePost(post.id, user.keycloakId)
        userService.deleteUser(user.keycloakId)
    }
}
