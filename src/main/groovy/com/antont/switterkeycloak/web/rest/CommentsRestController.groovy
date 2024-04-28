package com.antont.switterkeycloak.web.rest

import com.antont.switterkeycloak.service.PostsService
import com.antont.switterkeycloak.web.dto.CommentDto
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts/{id}")
class CommentsRestController {

    private final PostsService postsService

    CommentsRestController(PostsService postsService) {
        this.postsService = postsService
    }

    @GetMapping("/get-comment")
    ResponseEntity<?> getComments(@PathVariable("id") String postId) {
        try {
            ResponseEntity.ok().body(postsService.getComments(postId))
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/add-comment")
    ResponseEntity<?> addCommentToPost(@PathVariable("id") String postId, @Valid @RequestBody CommentDto dto, Authentication auth) {
        try {
            ResponseEntity.ok().body(postsService.addComment(postId, dto, auth.name))
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}
