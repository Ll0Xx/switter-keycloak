package com.antont.switterkeycloak.web.dto


import groovyjarjarantlr4.v4.runtime.misc.NotNull
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import lombok.NoArgsConstructor

@NoArgsConstructor
class CreateUserDto extends PasswordDto {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 15)
    String username
}
