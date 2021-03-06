package com.example.domain.user

interface UserDomainService {
    suspend fun getUserByEmail(email: String): User?
    suspend fun insertUser(user: UserWrite): User?
    suspend fun deleteUserByEmail(email: String): Boolean
    suspend fun updateUser(userWrite: UserWrite): User?
    suspend fun getAllUsers(): List<User>
}