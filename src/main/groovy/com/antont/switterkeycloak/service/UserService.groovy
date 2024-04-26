package com.antont.switterkeycloak.service

import com.antont.switterkeycloak.web.dto.CreateUserDto

interface UserService {
    String registerUser(CreateUserDto dto)
}