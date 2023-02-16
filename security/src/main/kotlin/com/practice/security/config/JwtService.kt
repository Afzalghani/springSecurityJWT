package com.practice.security.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.function.Function
import kotlin.collections.HashMap

@Service
class JwtService {

    var secretKey:String="3F4528482B4D6251655468576D5A7134743777217A25432A46294A404E635266"

    fun extractUsername(token:String):String?{
        return extractClaims(token,Claims::getSubject);
    }

    fun <T> extractClaims(token:String,claimsResolver: Function<Claims,T>):T{
        var claims:Claims=extractAllClaims(token)
        return claimsResolver.apply(claims)

    }

    private fun  extractAllClaims(token:String): Claims{
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body;


    }

    fun generateToken(extraclaims:Map<String, Any>,userDetails:UserDetails):String{
        return Jwts.builder()
            .setClaims(extraclaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis() +1000*60*24))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    fun isTokenValid(token:String, userDetails:UserDetails):Boolean{
        var userName:String? = extractUsername(token);

        return (userName.equals(userDetails.username) && isTokenExpired(token));

    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date());
    }

    private fun extractExpiration(token: String): Date {
        return extractClaims(token,Claims::getExpiration)

    }

    fun generateToken(userDetails:UserDetails):String{
        return generateToken(HashMap(),userDetails);
    }

    private fun getSignInKey():  Key{
        var keyBytes:ByteArray = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);

    }


}
