package com.example.domain.repository.user

import com.example.domain.user.User
import com.example.domain.user.UserWrite
import com.example.domain.user.toUser

class InMemoryUserRepositoryService : UserRepositoryService {
    private val users = mutableListOf<User>()

    override suspend fun getUserByEmail(email: String): User? =
        users.find { it.email == email }

    override suspend fun insertUser(userWrite: UserWrite): User? =
        when (users.find { it.email == userWrite.email }){
            is User -> null
            else -> {
                val user = userWrite.toUser()
                users.add(user)
                user
            }
        }

    override suspend fun deleteUserByEmail(email: String): Boolean =
        users.removeIf{it.email == email}

    override suspend fun updateUser(userWrite: UserWrite): User? =
        if (users.removeIf { it.email == userWrite.email }){
            val user = userWrite.toUser()
            users.add(user)
            user
        }
        else {
            null
        }

    override suspend fun getAllUsers(): List<User> =
        users
}

