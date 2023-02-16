package com.practice.security.config

import com.practice.security.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class ApplicationConfig {

    lateinit var userRepository: UserRepository;

    @Bean
    fun userDeailsService():UserDetailsService{
        return UserDetailsService { username ->  userRepository.findByEmail(username)
        }
    }

    @Bean
    fun authenticationProvider():AuthenticationProvider{
        var authProvider:DaoAuthenticationProvider= DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDeailsService())
        authProvider.setPasswordEncoder(passEncoder())
        return authProvider;
    }
    @Bean
     fun passEncoder(): PasswordEncoder? {
       return BCryptPasswordEncoder();
    }

    @Bean
    fun authenticationManager(config:AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager;
    }




}