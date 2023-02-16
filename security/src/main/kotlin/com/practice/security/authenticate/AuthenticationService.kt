package com.practice.security.authenticate

import com.practice.security.config.JwtService
import com.practice.security.http.AuthenticationRequest
import com.practice.security.http.AuthenticationResponse
import com.practice.security.http.RegisterRequest
import com.practice.security.model.Role
import com.practice.security.model.user
import com.practice.security.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService {

    @Autowired
    lateinit var repository: UserRepository;
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder;
    @Autowired
    lateinit var jwtService: JwtService
    @Autowired
    lateinit var authenticationManager:AuthenticationManager

    fun register(request:RegisterRequest):AuthenticationResponse{

        var user=user();
        user.firstName=request.firstName;
        user.lastName=request.lastName
        user.setuserName(request.email)

        user.setpassoword(passwordEncoder.encode(request.password))

        //user.password=passwordEncoder.encode(request.password)
        user.role=Role.USER
        repository.save(user)

        var jwtToken=jwtService.generateToken(user)
            var auth=AuthenticationResponse()
         auth.token=jwtToken;
        return auth;




    }

    fun authenticate(request: AuthenticationRequest):AuthenticationResponse{
    authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.email,request.password))



        var user= repository.findByEmail(request.email!!);

        var jwtToken= jwtService.generateToken(user);
        var auth=AuthenticationResponse()
        auth.token=jwtToken;
        return auth;


    }
}