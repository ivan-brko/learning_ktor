package com.example.api.user

interface UserApiService {
    suspend fun insertUser(userWrite: UserWrite): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun getAllUsers(): List<User>
    suspend fun deleteUserByEmail(email: String): Boolean
    suspend fun updateUser(userWrite: UserWrite): User?
}