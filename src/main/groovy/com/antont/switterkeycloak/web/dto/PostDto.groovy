package com.antont.switterkeycloak.web.dto


import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

class PostDto {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 64)
    String title
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 256)
    String content
}
