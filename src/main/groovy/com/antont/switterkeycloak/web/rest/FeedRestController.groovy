package com.antont.switterkeycloak.web.rest

import com.antont.switterkeycloak.service.FeedService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/feed")
class FeedRestController {

    private final FeedService feedService

    FeedRestController(FeedService feedService) {
        this.feedService = feedService
    }

    @GetMapping
    ResponseEntity<?> getUserFeedPost(Authentication auth) {
        try {
            ResponseEntity.ok().body(feedService.getUsersFeedByKeycloakId(auth.name))
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping("/{user}")
    ResponseEntity<?> getUserFeedPost(@PathVariable("user") String userId) {
        try {
            ResponseEntity.ok().body(feedService.getUserFeed(userId))
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}
