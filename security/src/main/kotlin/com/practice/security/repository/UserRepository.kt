package com.practice.security.repository

import com.practice.security.model.user
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<user,Int> {

    fun findByEmail(email:String):user;
}