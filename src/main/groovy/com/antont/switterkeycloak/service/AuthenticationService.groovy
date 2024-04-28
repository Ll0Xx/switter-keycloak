package com.antont.switterkeycloak.service

import com.antont.switterkeycloak.web.dto.LoginDto

interface AuthenticationService {
    String generateAccessToken(LoginDto dto)
    String logout(String userId)
}
