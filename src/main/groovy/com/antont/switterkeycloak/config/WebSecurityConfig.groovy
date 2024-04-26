package com.antont.switterkeycloak.config

import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@RequiredArgsConstructor
@NoArgsConstructor
@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    private final MyJwtAuthConverter jwtAuthConverter

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests {
                    it
                            .requestMatchers(HttpMethod.GET, "/test/user").permitAll()
                            .anyRequest().authenticated()
                }
                .oauth2ResourceServer {
                    it.jwt { it.jwtAuthenticationConverter { jwtAuthConverter } }
                }
                .sessionManagement {
                    it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }
                .build()
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().requestMatchers(HttpMethod.POST, "/public/**", "/user")
        }
    }
}
