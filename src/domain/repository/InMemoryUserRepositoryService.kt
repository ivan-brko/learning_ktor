package com.example.domain.repository

import com.example.domain.user.User
import com.example.domain.user.UserWrite
import java.time.LocalDateTime

private fun UserWrite.toUser() =
    User(email, firstName, lastName, hashedPassword, role, LocalDateTime.now(), LocalDateTime.now())

class InMemoryUserRepositoryService : UserRepositoryService{
    private val users = mutableListOf<User>()

    override fun getUserByEmail(email: String): User? =
        users.find { it.email == email }

    override fun insertUser(userWrite: UserWrite): User? =
        when (users.find { it.email == userWrite.email }){
            is User -> null
            else -> {
                val user = userWrite.toUser()
                users.add(user)
                user
            }
        }

    override fun deleteUserByEmail(email: String): Boolean =
        users.removeIf{it.email == email}

    override fun updateUser(userWrite: UserWrite): User? =
        if (users.removeIf { it.email == userWrite.email }){
            val user = userWrite.toUser()
            users.add(user)
            user
        }
        else {
            null
        }

    override fun getAllUsers(): List<User> =
        users
}

