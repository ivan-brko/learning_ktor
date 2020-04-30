package com.example.domain.user

interface UserDomainService {
    fun getUserByEmail(email: String): User?
    fun insertUser(user: UserWrite): User?
}