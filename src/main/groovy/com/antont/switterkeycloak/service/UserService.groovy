package com.antont.switterkeycloak.service

import com.antont.switterkeycloak.web.dto.CreateUserDto
import com.antont.switterkeycloak.web.dto.PasswordDto

interface UserService {
    Integer registerUser(CreateUserDto dto)
    String updateUser(PasswordDto dto, String userId)
    Integer deleteUser(String userId)
}