package com.antont.switterkeycloak.web.rest

import com.antont.switterkeycloak.db.repository.CommentsRepository
import com.antont.switterkeycloak.service.PostsService
import com.antont.switterkeycloak.web.dto.PostDto
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts")
class PostsRestController {

    private final PostsService postsService

    PostsRestController(PostsService postsService) {
        this.postsService = postsService
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getPost(@PathVariable("id") String postId) {
        try {
            ResponseEntity.ok().body(postsService.getPost(postId))
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping
    ResponseEntity<?> createPost(@Valid @RequestBody PostDto dto, Authentication auth) {
        try {
            ResponseEntity.ok().body(postsService.createPost(dto, auth.name))
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> updatePost(@PathVariable("id") String postId, @Valid @RequestBody PostDto dto, Authentication auth) {
        try {
            ResponseEntity.ok().body(postsService.editPost(dto, postId, auth.name))
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deletePost(@PathVariable("id") String postId, Authentication auth) {
        try {
            postsService.deletePost(postId, auth.name)
            ResponseEntity.ok().body("Post ${postId} deleted successfully")
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}
