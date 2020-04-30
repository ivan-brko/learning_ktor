package com.example.domain.user

import com.example.utils.Role
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UserWrite(
    val email: String,
    val firstName: String,
    val lastName: String,
    val hashedPassword: String,
    val role: Role
)

fun UserWrite.toUser() =
    User(email, firstName, lastName, hashedPassword, role, LocalDateTime.now(), LocalDateTime.now())