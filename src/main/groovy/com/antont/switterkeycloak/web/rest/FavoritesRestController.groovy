package com.antont.switterkeycloak.web.rest

import com.antont.switterkeycloak.service.PostsService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts/{id}")
class FavoritesRestController {

    private final PostsService postsService

    FavoritesRestController(PostsService postsService) {
        this.postsService = postsService
    }

    @PostMapping("/add-favorite")
    ResponseEntity<?> addToFavorite(@PathVariable("id") String postId, Authentication auth) {
        try {
            ResponseEntity.ok().body(postsService.addToFavorite(postId, auth.name))
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/remove-favorite")
    ResponseEntity<?> removeFromFavorite(@PathVariable("id") String postId, Authentication auth) {
        try {
            ResponseEntity.ok().body(postsService.removeFromFavorite(postId, auth.name))
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}
