package com.practice.security.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

@Entity
@Table(name = "user")
class user: UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int?=null

     var firstName:String?=null
     var lastName: String?=null
   private var email:String?=null
    private var password:String?=null

    @Enumerated(EnumType.STRING)
    var role:Role?=null
    override fun getAuthorities(): List<out GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role?.name));
    }

    override fun getPassword(): String? {
        return password;
    }

    fun setpassoword(Password:String?){
        this.password=Password

    }



    override fun getUsername(): String? {
        return email
    }

    fun setuserName(email:String?){
        this.email=email;

    }

    override fun isAccountNonExpired(): Boolean {
        return true;

    }

    override fun isAccountNonLocked(): Boolean {
        return true;
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true

    }

    override fun isEnabled(): Boolean {
        return true

    }
}