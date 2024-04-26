package com.antont.switterkeycloak.config

import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@RequiredArgsConstructor
@NoArgsConstructor
@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf { it.disable() }
                .authorizeHttpRequests {
                    it
                            .requestMatchers(HttpMethod.POST, "/user").permitAll()
                            .anyRequest().authenticated()
                }
                .oauth2ResourceServer {
                    it.jwt { Customizer.withDefaults() }
                }
                .sessionManagement {
                    it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }
                .build()
    }
}
