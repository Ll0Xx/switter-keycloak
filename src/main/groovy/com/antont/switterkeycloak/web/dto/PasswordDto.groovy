package com.antont.switterkeycloak.web.dto

import com.antont.switterkeycloak.validation.PasswordMatches
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import lombok.NoArgsConstructor

@NoArgsConstructor
@PasswordMatches
class PasswordDto {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 15)
    String password
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 15)
    String passwordConfirmation
}
