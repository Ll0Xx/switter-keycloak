package com.antont.switterkeycloak.web.rest

import com.antont.switterkeycloak.service.AuthenticationService
import com.antont.switterkeycloak.web.dto.LoginDto
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthenticationController {

    private final AuthenticationService authenticationService

    AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> generateAccessToken(@Valid @RequestBody LoginDto dto) {
        try {
            ResponseEntity.ok().body(authenticationService.generateAccessToken(dto))
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/logout")
    ResponseEntity<?> logout(Authentication auth){
        try {
            ResponseEntity.ok().body(authenticationService.logout(auth.name))
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}
