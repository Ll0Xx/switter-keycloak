package com.antont.switterkeycloak

import com.antont.switterkeycloak.base.BaseSpec
import com.antont.switterkeycloak.db.entity.Post
import com.antont.switterkeycloak.db.entity.User
import com.antont.switterkeycloak.db.repository.CommentsRepository
import com.antont.switterkeycloak.db.repository.PostsRepository
import com.antont.switterkeycloak.db.repository.SubscriptionRepository
import com.antont.switterkeycloak.service.PostsService
import com.antont.switterkeycloak.service.SubscribeService
import com.antont.switterkeycloak.web.dto.CommentDto
import com.antont.switterkeycloak.web.dto.PostDto
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class FeedRestControllerSpec extends BaseSpec {

    final def postComment = "post comment"
    final def subscribeToUsername = "subscribeToUser"

    @Shared
    User user
    @Shared
    User subscribeToUser
    @Shared
    Post post

    @Autowired
    PostsService postsService
    @Shared
    @Autowired
    PostsRepository postsRepository
    @Shared
    @Autowired
    CommentsRepository commentsRepository
    @Autowired
    SubscribeService subscribeService
    @Shared
    @Autowired
    SubscriptionRepository subscriptionRepository

    def "test try get user feed. Should success"() {
        given:
        user = usersRepository.findByUsername(createUser(username, pass)).orElseThrow {
            new RuntimeException("Failed to find user")
        }
        subscribeToUser = usersRepository.findByUsername(createUser(subscribeToUsername, pass)).orElseThrow {
            new RuntimeException("Failed to find user")
        }
        accessToken = generateAccessToken(username, pass)
        subscribeService.subscribeTo(user.keycloakId, subscribeToUser.id)

        post = postsService.createPost(new PostDto(title: "test title", content: "test content"), subscribeToUser.keycloakId)
        postsService.addComment(post.id, new CommentDto(comment: postComment), user.keycloakId)

        expect:
        mvc.perform(get("/feed")
                .header("Authorization", "Bearer ${accessToken}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.[0].postOwner').value(subscribeToUser.id))
                .andExpect(jsonPath('$.[0].comments').exists())
                .andExpect(jsonPath('$.[0].comments[0].author').value(user.id))
                .andExpect(jsonPath('$.[0].comments[0].comment').value(postComment))
    }

    def cleanupSpec() {
        commentsRepository.deleteAll()
        postsRepository.deleteAll()
        subscriptionRepository.deleteAll()
        usersRepository.deleteAll()
    }
}
