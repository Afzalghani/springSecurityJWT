//package com.practice.security.Config
//
//import jakarta.annotation.Nonnull
//import jakarta.servlet.FilterChain
//import jakarta.servlet.http.HttpServletRequest
//import jakarta.servlet.http.HttpServletResponse
//import lombok.RequiredArgsConstructor
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.web.authentication.WebAuthenticationDetails
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
//import org.springframework.stereotype.Component
//import org.springframework.web.filter.OncePerRequestFilter
//import java.util.logging.Logger
//
//@Component
//@RequiredArgsConstructor
//class JwtAuthenticationFilter: OncePerRequestFilter() {
//    override fun doFilterInternal(
//         request: HttpServletRequest,
//        response: HttpServletResponse,
//        filterChain: FilterChain
//    ) {
//        var authHeader:String? =request.getHeader("Authorization")
//
//        var jwt:String?;
//
//
//        lateinit var jwtService:JwtService;
//
//        lateinit var userDetailsService:UserDetailsService
//
//        var userEmail:String?;
//        if(authHeader==null || !authHeader.startsWith("Bearer")){
//            filterChain.doFilter(request,response)
//            return
//        }
//
//        jwt=authHeader.substring(7);
//        userEmail= jwtService.extractUsername(jwt);  // Exatract the userEmail from jwt tokens
//
//        if(userEmail!=null && SecurityContextHolder.getContext().authentication==null){
//           var userDetails: UserDetails = userDetailsService.loadUserByUsername(userEmail)
//
//            if(jwtService.isTokenValid(jwt,userDetails)){
//               var auth:UsernamePasswordAuthenticationToken =
//                   UsernamePasswordAuthenticationToken(userDetails,null,userDetails.authorities)
//                auth.details=WebAuthenticationDetailsSource().buildDetails(request)
//                SecurityContextHolder.getContext().authentication=auth;
//
//            }
//        }
//        filterChain.doFilter(request,response)
//    }
//
//}


package com.practice.security.Config

import com.practice.security.Config.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.NonNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
@RequiredArgsConstructor
class JwtAuthenticationFilter : OncePerRequestFilter() {
   @Autowired
  lateinit var jwtService: JwtService
    @Autowired
     lateinit var userDetailsService: UserDetailsService

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        @NonNull request: HttpServletRequest,
        @NonNull response: HttpServletResponse,
        @NonNull filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        val jwt: String
        val userEmail: String?
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }
        jwt = authHeader.substring(7)
        userEmail = jwtService.extractUsername(jwt)
        if (userEmail != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(userEmail)
            if (jwtService.isTokenValid(jwt, userDetails)) {
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        filterChain.doFilter(request, response)
    }
}