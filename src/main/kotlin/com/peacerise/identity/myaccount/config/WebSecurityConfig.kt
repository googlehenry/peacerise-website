package com.peacerise.identity.myaccount.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
class WebSecurityConfig {


    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf()
            .disable()
            .authorizeHttpRequests()
            .requestMatchers("/peacerise/myprofile")
            .hasAnyAuthority(
                "SCOPE_user.data.read",
                "SCOPE_user.data.write",
                "SCOPE_user.profile.read",
                "SCOPE_user.profile.write"
            )
            .anyRequest()
            .permitAll()
        http.oauth2Client()
        http.oauth2ResourceServer().jwt()
        http.formLogin(Customizer.withDefaults())
        http.headers().frameOptions().sameOrigin()

        return http.build()
    }

    @Bean
    fun users(): UserDetailsService {
        return InMemoryUserDetailsManager(
            User.withDefaultPasswordEncoder().username("user1").password("password").roles("USER").build()
        )
    }
}