package com.bclipse.monolith.infra.security

import com.bclipse.monolith.application.user.util.AccessTokenEncoder
import com.bclipse.monolith.infra.web.ExceptionHandlerFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    private val accessTokenEncoder: AccessTokenEncoder,
    private val userDetailsService: UserDetailsService,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http.httpBasic { it.disable() }
            .csrf { it.disable() }
            .cors {
                it.configurationSource {
                    CorsConfiguration().apply {
                        allowedOrigins = listOf("*")
                        allowedMethods = listOf("*")
                        allowedHeaders = listOf("*")
                        allowCredentials = true
                    }
                }
            }.authorizeHttpRequests {
                it.requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                ).permitAll()
                it.requestMatchers(
                    "/api/v1/user/signup",
                    "/api/v1/user/login",
                    "/api/v1/user/refresh-login"
                ).permitAll()
                it.requestMatchers("/api/v1/**").authenticated()
            }.sessionManagement{
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.addFilterBefore(UserAuthenticationFilter(accessTokenEncoder, userDetailsService), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(ExceptionHandlerFilter(), UserAuthenticationFilter::class.java)
            .build()
}