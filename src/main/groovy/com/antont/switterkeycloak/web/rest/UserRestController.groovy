package com.antont.switterkeycloak.web.rest


import com.antont.switterkeycloak.service.UserService
import com.antont.switterkeycloak.web.dto.CreateUserDto
import jakarta.validation.Valid
import lombok.AllArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
@AllArgsConstructor
class UserRestController {

    private final UserService userService

    UserRestController(UserService userService) {
        this.userService = userService
    }

    @PostMapping
    ResponseEntity<String> registerUser(@Valid @RequestBody CreateUserDto dto) {
        return ResponseEntity.ok(userService.registerUser(dto))
    }

    @GetMapping
    ResponseEntity<String> getUserInfo() {
        return ResponseEntity.ok("yay it secure")
    }
}
