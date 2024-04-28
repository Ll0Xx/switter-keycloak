package com.antont.switterkeycloak

import com.antont.switterkeycloak.base.BaseSpec
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import spock.lang.Shared

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class PostsRestControllerSpec extends BaseSpec {
    static def title = "test title"
    static def content = "test content"

    static def updatedTitle = "test title updated"
    static def updatedContent = "test content updated"

    @Shared
    String postId

    def "test try create post without authentication. Should fail"() {
        expect:
        mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"title":"' + title + '", "content": "' + content + '"}'))
                .andExpect(status().isUnauthorized())
    }

    def "test try create post with authentication. Should success"() {
        given:
        createUser(username, pass)
        accessToken = generateAccessToken(username, pass)
        expect:
        def result = mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${accessToken}")
                .content('{"title":"' + title + '", "content": "' + content + '"}'))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath('$.title').value(title))
                .andExpect(jsonPath('$.content').value(content))
                .andReturn()
        cleanup:
        postId = parseCreatedPostId(result.response.contentAsString)
    }

    def "test try create post with authentication. Should success"() {
        expect:
        mvc.perform(get("/posts/${postId}")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${accessToken}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath('$.title').value(title))
                .andExpect(jsonPath('$.content').value(content))
    }

    def "test try update post with authentication. Should success"() {
        expect:
        mvc.perform(patch("/posts/${postId}")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${accessToken}")
                .content('{"title":"' + updatedTitle + '", "content": "' + updatedContent + '"}'))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath('$.title').value(updatedTitle))
                .andExpect(jsonPath('$.content').value(updatedContent))
    }

    def "test try delete post without authentication. Should fail"() {
        expect:
        mvc.perform(patch("/posts/${postId}")
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"title":"' + updatedTitle + '", "content": "' + updatedContent + '"}'))
                .andExpect(status().isUnauthorized())
    }

    def "test try delete post with authentication. Should fail"() {
        expect:
        mvc.perform(delete("/posts/${postId}")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${accessToken}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Post ${postId} deleted successfully"))
    }

    private def parseCreatedPostId(String response) {
        ObjectMapper mapper = new ObjectMapper()
        JsonNode rootNode = mapper.readTree(response)
        rootNode.get("id").asText()
    }

    def cleanupSpec() {
        def user = usersRepository.findByUsername(username).orElseThrow {
            throw new RuntimeException("Failed to find user with username: ${username}")
        }
        userService.deleteUser(user.keycloakId)
    }

}
