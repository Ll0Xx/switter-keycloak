package com.antont.switterkeycloak.web.rest

import com.antont.switterkeycloak.service.SubscribeService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SubscriptionRestController {

    private final SubscribeService subscribeService

    SubscriptionRestController(SubscribeService subscribeService) {
        this.subscribeService = subscribeService
    }

    @PostMapping(["/subscribe", "/sub"])
    ResponseEntity<?> subscribeTo(@RequestParam(name = "to") String subscribeToId, Authentication auth) {
        try {
            ResponseEntity.ok().body(subscribeService.subscribeTo(auth.name, subscribeToId))
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping(["/unsubscribe", "/unsub"])
    ResponseEntity<?> unsubscribeFrom(@RequestParam(name = "from") String subscribeToId, Authentication auth) {
        try {
            ResponseEntity.ok().body(subscribeService.unsubscribeFrom(auth.name, subscribeToId))
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}
