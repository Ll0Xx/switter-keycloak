package com.antont.switterkeycloak.web.dto

import com.antont.switterkeycloak.validation.PasswordMatches
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import lombok.NoArgsConstructor

@NoArgsConstructor
@PasswordMatches
class UpdatePasswordDto {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 15)
    String newPassword
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 15)
    String newPasswordConfirmation
}
