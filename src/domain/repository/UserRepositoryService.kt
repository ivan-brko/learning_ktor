package com.example.domain.repository

import com.example.domain.user.User
import com.example.domain.user.UserWrite

interface UserRepositoryService{
    fun getUserByEmail(email: String): User?
    fun insertUser(userWrite: UserWrite): User?
    fun deleteUserByEmail(email: String): Boolean
    fun updateUser(userWrite: UserWrite): User?
    fun getAllUsers(): List<User>
}