package com.antont.switterkeycloak.web.rest

import com.antont.switterkeycloak.service.UserService
import com.antont.switterkeycloak.web.dto.CreateUserDto
import com.antont.switterkeycloak.web.dto.PasswordDto
import jakarta.validation.Valid
import lombok.AllArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@AllArgsConstructor
class UserRestController {

    private final UserService userService

    UserRestController(UserService userService) {
        this.userService = userService
    }

    @PostMapping
    ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserDto dto) {
        try {
            ResponseEntity.status(userService.registerUser(dto)).build()
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PutMapping
    ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordDto dto, Authentication auth){
        try {
            ResponseEntity.ok(userService.updateUser(dto, auth.name))
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping
    ResponseEntity<?> deleteUserById(Authentication auth) {
        try {
            ResponseEntity.status(userService.deleteUser(auth.name)).build()
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}
