package com.example.domain.user

interface UserDomainService {
    fun getUserByEmail(email: String): User?
    fun insertUser(user: UserWrite): User?
    fun deleteUserByEmail(email: String): Boolean
    fun updateUser(userWrite: UserWrite): User?
    fun getAllUsers(): List<User>
}