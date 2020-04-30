package com.example.domain.repository.user

import com.example.domain.user.User
import com.example.domain.user.UserWrite

interface UserRepositoryService{
    suspend fun getUserByEmail(email: String): User?
    suspend fun insertUser(userWrite: UserWrite): User?
    suspend fun deleteUserByEmail(email: String): Boolean
    suspend fun updateUser(userWrite: UserWrite): User?
    suspend fun getAllUsers(): List<User>
}