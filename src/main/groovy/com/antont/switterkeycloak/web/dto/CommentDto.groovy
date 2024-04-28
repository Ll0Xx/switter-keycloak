package com.antont.switterkeycloak.web.dto

import groovyjarjarantlr4.v4.runtime.misc.NotNull
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

class CommentDto {
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 256)
    String comment
}
