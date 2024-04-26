package com.antont.switterkeycloak.web.dto

import com.antont.switterkeycloak.validation.PasswordMatches
import groovyjarjarantlr4.v4.runtime.misc.NotNull
import jakarta.validation.constraints.NotEmpty
import lombok.NoArgsConstructor

@NoArgsConstructor
@PasswordMatches
class CreateUserDto {

    @NotNull
    @NotEmpty
    String username

    @NotNull
    @NotEmpty
    String password

    @NotNull
    @NotEmpty
    String passwordConfirmation
}
