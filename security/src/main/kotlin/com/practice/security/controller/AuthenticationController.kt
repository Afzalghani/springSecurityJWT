package com.practice.security.controller

import com.practice.security.authenticate.AuthenticationService
import com.practice.security.http.AuthenticationRequest
import com.practice.security.http.AuthenticationResponse
import com.practice.security.http.RegisterRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController {

    @Autowired
    lateinit var service: AuthenticationService;

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest):ResponseEntity<AuthenticationResponse> {

        return ResponseEntity.ok(service.register(request))
    }

    @PostMapping("/authenticate")
    fun  register(@RequestBody request: AuthenticationRequest):ResponseEntity<AuthenticationResponse>{

        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/hello")
    fun hello():String{
        return "this is my way of doing thing"
    }
}