package com.antont.switterkeycloak.service

import com.antont.switterkeycloak.web.dto.CreateUserDto
import com.antont.switterkeycloak.web.dto.UpdatePasswordDto

interface UserService {
    String registerUser(CreateUserDto dto)
    String updateUser(UpdatePasswordDto dto)
    String deleteUser(String id)
}