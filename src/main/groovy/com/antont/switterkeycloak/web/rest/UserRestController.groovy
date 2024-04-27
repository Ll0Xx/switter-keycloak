package com.antont.switterkeycloak.web.rest


import com.antont.switterkeycloak.service.UserService
import com.antont.switterkeycloak.web.dto.CreateUserDto
import com.antont.switterkeycloak.web.dto.UpdatePasswordDto
import jakarta.validation.Valid
import lombok.AllArgsConstructor
import org.keycloak.representations.AccessToken
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

import java.security.Principal

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
            ResponseEntity.ok(userService.registerUser(dto))
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

//    @PutMapping
//    ResponseEntity<?> updatePassword(UpdatePasswordDto dto, Authentication auth){
//        try {
//            ResponseEntity.ok(userService.updateUser(dto))
//        } catch (Exception e) {
//            ResponseEntity.badRequest().body(e.message)
//        }
//    }
//
//    @DeleteMapping
//    ResponseEntity<?> deleteUserById(String id) {
//        try {
//            ResponseEntity.ok(userService.deleteUser(id))
//        } catch (Exception e) {
//            ResponseEntity.badRequest().body(e.message)
//        }
//    }
}
