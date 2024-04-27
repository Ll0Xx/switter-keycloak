package com.antont.switterkeycloak.web.dto

import com.antont.switterkeycloak.validation.PasswordMatches
import groovyjarjarantlr4.v4.runtime.misc.NotNull
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import lombok.NoArgsConstructor

@NoArgsConstructor
@PasswordMatches
class CreateUserDto {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 15)
    String username

    @NotNull
    @NotEmpty
    @Min(3)
    @Size(min = 3, max = 15)
    String password

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 15)
    String passwordConfirmation
}
