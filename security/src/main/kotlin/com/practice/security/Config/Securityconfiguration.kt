package com.practice.security.Config


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class Securityconfiguration {

    @Autowired
    lateinit var authenticationProvider:AuthenticationProvider;
   @Autowired
lateinit var jwtauthFilter: JwtAuthenticationFilter;

    @Bean
    fun securityFilterChain(http: HttpSecurity):SecurityFilterChain {

        http
         .csrf()
         .disable()
            .cors().and()
         .authorizeHttpRequests()
         .requestMatchers("/register","/authenticate")
         .permitAll()
         .anyRequest()
         .authenticated()
         .and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
         .authenticationProvider(authenticationProvider)
         .addFilterBefore(jwtauthFilter,UsernamePasswordAuthenticationFilter::class.java);

        return http.build()

    }



}

